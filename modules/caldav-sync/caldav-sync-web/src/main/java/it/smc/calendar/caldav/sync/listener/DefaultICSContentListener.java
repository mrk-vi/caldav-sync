/**
 * Copyright (c) 2013 SMC Treviso Srl. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package it.smc.calendar.caldav.sync.listener;

import com.liferay.calendar.constants.CalendarActionKeys;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.model.CalendarBookingConstants;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarBookingLocalService;
import com.liferay.calendar.util.JCalendarUtil;
import com.liferay.calendar.workflow.CalendarBookingWorkflowConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.sanitizer.SanitizerException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import it.smc.calendar.caldav.helper.api.CalendarHelper;
import it.smc.calendar.caldav.helper.api.CalendarHelperUtil;
import it.smc.calendar.caldav.schedule.contact.model.ScheduleContact;
import it.smc.calendar.caldav.schedule.contact.service.ScheduleContactLocalService;
import it.smc.calendar.caldav.sync.ical.util.AttendeeUtil;
import it.smc.calendar.caldav.sync.util.CalDAVUtil;

import java.io.IOException;

import java.net.URI;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.ParameterList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.component.VAlarm;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.parameter.Cn;
import net.fortuna.ical4j.model.parameter.CuType;
import net.fortuna.ical4j.model.parameter.PartStat;
import net.fortuna.ical4j.model.parameter.Rsvp;
import net.fortuna.ical4j.model.parameter.Value;
import net.fortuna.ical4j.model.parameter.XParameter;
import net.fortuna.ical4j.model.property.Action;
import net.fortuna.ical4j.model.property.Attendee;
import net.fortuna.ical4j.model.property.Created;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.LastModified;
import net.fortuna.ical4j.model.property.Method;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.model.property.Status;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.model.property.Transp;
import net.fortuna.ical4j.model.property.Trigger;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.XProperty;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Fabio Pezzutto
 */
@Component(immediate = true, service = ICSImportExportListener.class)
public class DefaultICSContentListener implements ICSImportExportListener {

	public CalendarBooking afterContentExported(
			String ics, CalendarBooking calendarBooking)
		throws SanitizerException {

		return calendarBooking;
	}

	public CalendarBooking afterContentImported(
			String ics, CalendarBooking calendarBooking)
		throws SanitizerException {

		try {
			net.fortuna.ical4j.model.Calendar iCalCalendar = getICalendar(ics);

			VEvent vEvent = (VEvent)iCalCalendar.getComponent(
				net.fortuna.ical4j.model.Component.VEVENT);

			if (calendarBooking != null) {
				calendarBooking =
					updateTitleAndDescription(calendarBooking, vEvent);

				calendarBooking =
					updateBookingAttendees(calendarBooking, vEvent);
			}

			return calendarBooking;
		}
		catch (Exception e) {
			throw new SanitizerException(e);
		}
	}

	public void afterContentImported(String ics, Calendar calendar)
		throws SanitizerException {

		try {
			net.fortuna.ical4j.model.Calendar iCalCalendar = getICalendar(ics);

			ComponentList components = iCalCalendar.getComponents(
				net.fortuna.ical4j.model.Component.VEVENT);

			for (Object component : components) {
				if (component instanceof VEvent) {
					VEvent vEvent = (VEvent)component;

					String bookingUuid = vEvent.getUid(
					).getValue();

					CalendarBooking calendarBooking =
						_calendarBookingLocalService.fetchCalendarBooking(
							calendar.getCalendarId(), bookingUuid);

					if (calendarBooking != null) {
						updateBookingAttendees(calendarBooking, vEvent);
						updateTitleAndDescription(calendarBooking, vEvent);
					}
				}
			}
		}
		catch (Exception e) {
			throw new SanitizerException(e);
		}
	}

	public String beforeContentExported(
			String ics, CalendarBooking calendarBooking)
		throws SanitizerException {

		try {
			long userId = PrincipalThreadLocal.getUserId();

			if (userId == 0) {
				userId = PermissionThreadLocal.getPermissionChecker(
				).getUserId();
			}

			User currentUser = _userLocalService.getUser(userId);

			//TODO: find a better way to do it
			ics = ics.replaceAll("TRIGGER:PT", "TRIGGER:PT-");

			net.fortuna.ical4j.model.Calendar iCalCalendar = getICalendar(ics);

			ComponentList components = iCalCalendar.getComponents(
				net.fortuna.ical4j.model.Component.VEVENT);

			for (Object component : components) {
				if (component instanceof VEvent) {
					VEvent vEvent = (VEvent)component;

					if (vEvent.getAlarms().size() > 0) {
						updateAlarmActions(vEvent, userId);
					}

					updateICSExternalAttendees(vEvent, calendarBooking);

//					updateDowloadedInvitations(
//						currentUser, iCalCalendar, vEvent, calendarBooking);

					updateAllDayDate(vEvent, calendarBooking, currentUser);

					DateTime modifiedDate = new DateTime(
						calendarBooking.getModifiedDate(
						).getTime());

					LastModified lastModified = new LastModified(modifiedDate);

					lastModified.setUtc(true);
					vEvent.getProperties(
					).add(
						lastModified
					);

					DateTime createdDate = new DateTime(
						calendarBooking.getCreateDate(
						).getTime());

					Created created = new Created(createdDate);

					created.setUtc(true);
					vEvent.getProperties(
					).add(
						created
					);
				}
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Calendar exported: " + iCalCalendar.toString());
			}

			return iCalCalendar.toString();
		}
		catch (Exception e) {
			throw new SanitizerException(e);
		}
	}

	public String beforeContentImported(String ics, Calendar calendar)
		throws SanitizerException {

		try {
			net.fortuna.ical4j.model.Calendar iCalCalendar = getICalendar(ics);

			ComponentList components = iCalCalendar.getComponents(
				net.fortuna.ical4j.model.Component.VEVENT);

			for (Object component : components) {
				if (component instanceof VEvent) {
					VEvent vEvent = (VEvent)component;

					if (vEvent.getAlarms().size() > 0) {
						updateAlarmAttendeers(vEvent, calendar);
						removeIgnorableTriggers(vEvent);
					}

					Uid vEventUid = vEvent.getUid();

					if (vEventUid != null) {
						String vEventUidValue = vEventUid.getValue();

						CalendarBooking calendarBooking =
							_calendarBookingLocalService.fetchCalendarBooking(
								calendar.getCalendarId(), vEventUidValue);

						updateAltDescription(calendarBooking, vEvent);
					}
				}
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Calendar imported: " + iCalCalendar.toString());
			}

			return iCalCalendar.toString();
		}
		catch (Exception e) {
			throw new SanitizerException(e);
		}
	}

	protected User getCalendarBookingUser(CalendarBooking calendarBooking)
		throws PortalException {

		CalendarResource calendarResource =
			calendarBooking.getCalendarResource();

		Optional<User> user = CalendarHelperUtil.getCalendarResourceUser(
			calendarResource);

		if (!user.isPresent()) {
			return null;
		}

		return user.get();
	}

	protected net.fortuna.ical4j.model.Calendar getICalendar(String ics)
		throws IOException, ParserException {

		CalendarBuilder calendarBuilder = new CalendarBuilder();

		UnsyncStringReader unsyncStringReader = new UnsyncStringReader(ics);

		net.fortuna.ical4j.model.Calendar iCalCalendar = calendarBuilder.build(
			unsyncStringReader);

		return iCalCalendar;
	}

	protected void removeIgnorableTriggers(VEvent vEvent) {
		ArrayList<VAlarm> alarms = (ArrayList<VAlarm>)vEvent.getAlarms();

		ParameterList parameterList = new ParameterList();

		parameterList.add(new Value("DATE-TIME"));

		Trigger ignorableTrigger = new Trigger(
			parameterList, "19760401T005545Z");

		alarms.removeIf(
			a -> {
				Trigger trigger = a.getTrigger();

				if (trigger != null) {
					return trigger.equals(ignorableTrigger);
				}

				return true;
			});
	}

	@Reference(
		target = "(model.class.name=com.liferay.calendar.model.Calendar)",
		unbind = "-"
	)
	protected void setModelPermissionChecker(
		ModelResourcePermission<Calendar> modelResourcePermission) {

		_calendarModelResourcePermission = modelResourcePermission;
	}

	protected void updateAlarmActions(VEvent vEvent, long userId)
		throws Exception {

		User user = _userLocalService.getUser(userId);

		String currentUserEmail = StringUtil.toLowerCase(
			user.getEmailAddress());

		ComponentList vAlarms = (ComponentList)vEvent.getAlarms();

		VAlarm vAlarm;
		PropertyList propertyList;

		for (int i = 0; i < vAlarms.size(); i++) {
			vAlarm = (VAlarm)vAlarms.get(i);
			propertyList = vAlarm.getProperties();

			PropertyList alarmAttendees = propertyList.getProperties(
				Property.ATTENDEE);

			boolean currentUserIdAttendee = false;

			Iterator<Attendee> alarmAttendeesIterator =
				alarmAttendees.iterator();

			while (alarmAttendeesIterator.hasNext()) {
				Attendee attendee = alarmAttendeesIterator.next();

				String attendeeData = StringUtil.toLowerCase(
					attendee.getValue());

				if (attendeeData.contains(currentUserEmail)) {
					currentUserIdAttendee = true;

					break;
				}
			}

			HttpServletRequest request =
				ServiceContextThreadLocal.getServiceContext(
				).getRequest();

			if (!currentUserIdAttendee) {
				propertyList.remove(propertyList.getProperty(Action.ACTION));
			}
			else if (vAlarm.getAction().equals(Action.EMAIL) &&
					 !CalDAVUtil.isThunderbird(request)) {

				propertyList.remove(propertyList.getProperty(Action.ACTION));
				propertyList.add(Action.DISPLAY);
			}
		}
	}

	protected void updateAlarmAttendeers(VEvent vEvent, Calendar calendar)
		throws Exception {

		ComponentList vAlarms = (ComponentList)vEvent.getAlarms();

		VAlarm vAlarm;
		PropertyList propertyList;

		for (int i = 0; i < vAlarms.size(); i++) {
			vAlarm = (VAlarm)vAlarms.get(i);
			propertyList = vAlarm.getProperties();

			if (!vAlarm.getAction().equals(Action.EMAIL)) {
				propertyList.remove(propertyList.getProperty(Action.ACTION));
				propertyList.add(Action.EMAIL);

				if ((vAlarm.getSummary() == null) &&
					(vAlarm.getDescription() != null)) {

					String description = vAlarm.getDescription(
					).getValue();

					propertyList.add(new Summary(description));
				}

				List<String> recipients = _getNotificationRecipients(calendar);

				URI uri;
				Attendee attendee;

				for (String recipient : recipients) {
					uri = URI.create("mailto:".concat(recipient));
					attendee = new Attendee(uri);
					propertyList.add(attendee);
				}
			}
		}
	}

	protected void updateAllDayDate(
		VEvent vEvent, CalendarBooking calendarBooking, User user) {

		if (calendarBooking.isAllDay()) {
			PropertyList propertyList = vEvent.getProperties();

			TimeZone userTimeZone = user.getTimeZone();

			TimeZone utcTimeZone = TimeZone.getTimeZone("UTC");

			java.util.Calendar startJCalendar = JCalendarUtil.getJCalendar(
				calendarBooking.getStartTime(), utcTimeZone);

			boolean normalize = true;

			if (startJCalendar.get(java.util.Calendar.HOUR) == 0) {
				normalize = false;
			}

			java.util.Calendar endJCalendar = JCalendarUtil.getJCalendar(
				calendarBooking.getEndTime(), utcTimeZone);

			endJCalendar.add(java.util.Calendar.DAY_OF_MONTH, 1);

			long utcStart = startJCalendar.getTimeInMillis();

			long utcEnd = endJCalendar.getTimeInMillis();

			if (normalize) {
				utcStart += userTimeZone.getRawOffset();
				utcEnd += userTimeZone.getRawOffset();
			}

			DtStart dtStart = new DtStart(
				new net.fortuna.ical4j.model.Date(utcStart), true);

			DtEnd dtEnd = new DtEnd(
				new net.fortuna.ical4j.model.Date(utcEnd), true);

			propertyList.remove(vEvent.getStartDate());

			propertyList.remove(vEvent.getEndDate());

			propertyList.add(dtStart);

			propertyList.add(dtEnd);
		}
	}

	protected void updateAltDescription(
			CalendarBooking calendarBooking, VEvent vEvent)
		throws PortalException {

		XProperty vEventXAltDesc = (XProperty)vEvent.getProperty("X-ALT-DESC");

		if (vEventXAltDesc == null) {
			return;
		}

		if (calendarBooking != null) {
			User calendarBookingUser = getCalendarBookingUser(calendarBooking);

			Locale locale = calendarBookingUser.getLocale();

			String calendarBookingDescription = calendarBooking.getDescription(
				locale);

			XProperty calendarBookingXAltDesc = new XProperty(
				"X-ALT-DESC", calendarBookingDescription);

			ParameterList parameters = calendarBookingXAltDesc.getParameters();

			parameters.add(new XParameter("FMTTYPE", "text/html"));

			if (calendarBookingXAltDesc.equals(vEventXAltDesc)) {
				return;
			}
		}

		_replaceDescription(vEvent, vEventXAltDesc);
	}

	protected CalendarBooking updateBookingAttendees(
			CalendarBooking calendarBooking, VEvent vEvent)
		throws PortalException {

		PropertyList attendeeList = vEvent.getProperties(Property.ATTENDEE);

		Iterator iterator = attendeeList.iterator();

		while (iterator.hasNext()) {
			Attendee attendee = (Attendee)iterator.next();

			if (Validator.isNull(attendee.getValue())) {
				continue;
			}

			String attendeeEmail = StringUtil.replace(
				StringUtil.toLowerCase(attendee.getValue()), "mailto:",
				StringPool.BLANK);

			if (!Validator.isEmailAddress(attendeeEmail)) {
				continue;
			}

			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			User user = _userLocalService.fetchUserByEmailAddress(
				calendarBooking.getCompanyId(), attendeeEmail);

			Map<Long, CalendarBooking> userBookings = new HashMap<>();

			for (CalendarBooking cb :
				calendarBooking.getChildCalendarBookings()) {

				userBookings.put(cb.getCalendar().getUserId(), cb);
			}

			if (user == null) {
				Parameter cnParameter = attendee.getParameter(Parameter.CN);

				String commonName = attendeeEmail;

				if (Validator.isNotNull(cnParameter)) {
					commonName = cnParameter.getValue();
				}

				ScheduleContact scheduleContact =
					_scheduleContactLocalService.addScheduleContact(
						calendarBooking.getCompanyId(), commonName,
						attendeeEmail, serviceContext);

				Calendar externalCalendar =
					_scheduleContactLocalService.getDefaultCalendar(
						scheduleContact,
						serviceContext);

				CalendarBooking externalBooking;

				try {
					externalBooking =
						_calendarBookingLocalService.getCalendarBooking(
							externalCalendar.getCalendarId(),
							calendarBooking.getParentCalendarBookingId());
				}
				catch (PortalException pe) {
					externalBooking = null;
				}

				if (Validator.isNull(externalBooking)) {
					externalBooking =
						_calendarBookingLocalService.addCalendarBooking(
							externalCalendar.getUserId(),
							externalCalendar.getCalendarId(), new long[]{},
							calendarBooking.getParentCalendarBookingId(),
							CalendarBookingConstants.RECURRING_CALENDAR_BOOKING_ID_DEFAULT,
							calendarBooking.getTitleMap(),
							calendarBooking.getDescriptionMap(),
							calendarBooking.getLocation(),
							calendarBooking.getStartTime(),
							calendarBooking.getEndTime(),
							calendarBooking.getAllDay(),
							calendarBooking.getRecurrence(),
							calendarBooking.getFirstReminder(),
							calendarBooking.getFirstReminderType(),
							calendarBooking.getSecondReminder(),
							calendarBooking.getSecondReminderType(),
							serviceContext);
				}

				_calendarBookingLocalService.updateStatus(
					externalBooking.getUserId(),
					externalBooking.getCalendarBookingId(),
					AttendeeUtil.getStatus(
						attendee, WorkflowConstants.STATUS_PENDING),
					ServiceContextThreadLocal.getServiceContext());
			}
			else if (user.getUserId() == PrincipalThreadLocal.getUserId()
					 && userBookings.containsKey(user.getUserId())) {
				CalendarBooking internalBooking =
					userBookings.get(user.getUserId());

				int status = AttendeeUtil.getStatus(
					attendee, internalBooking.getStatus());

				if (status != internalBooking.getStatus()) {
					internalBooking =
						_calendarBookingLocalService.updateStatus(
							user.getUserId(), internalBooking, status,
							serviceContext);

					LastModified iCalLastModified =
						(LastModified)vEvent.getProperty(
							LastModified.LAST_MODIFIED);

					Date modifiedDate = new Date();

					if (iCalLastModified != null) {
						modifiedDate = iCalLastModified.getDate();
					}

					_updateBookingModifiedDate(
						internalBooking, modifiedDate);
				}
			}
		}

		return _calendarBookingLocalService.getCalendarBooking(
			calendarBooking.getCalendarBookingId());
	}

	protected void updateDowloadedInvitations(
			User user, net.fortuna.ical4j.model.Calendar iCalCalendar,
			VEvent vEvent, CalendarBooking calendarBooking)
		throws PortalException {

		CalendarBooking parentBooking =
			calendarBooking.getParentCalendarBooking();

		// set organizer

		User userOrganizer = getCalendarBookingUser(parentBooking);

		if (userOrganizer != null) {
			URI uri = URI.create(
				"mailto:".concat(userOrganizer.getEmailAddress()));

			Organizer organizer = new Organizer(uri);

			organizer.getParameters(
			).add(
				new Cn(userOrganizer.getFullName())
			);

			vEvent.getProperties(
			).add(
				organizer
			);

			Attendee organizerAttendee = AttendeeUtil.create(
				userOrganizer.getEmailAddress(), userOrganizer.getFullName(),
				false, WorkflowConstants.STATUS_APPROVED);

			vEvent.getProperties(
			).add(
				organizerAttendee
			);
		}

		vEvent.getProperties(
		).add(
			Transp.OPAQUE
		);

		boolean hasUpdatePermissions =
			_calendarModelResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(),
				calendarBooking.getCalendarId(),
				CalendarActionKeys.MANAGE_BOOKINGS);

		User bookingUser = getCalendarBookingUser(calendarBooking);

		// set event status

		boolean bookingPending = false;

		if (calendarBooking.getStatus() == WorkflowConstants.STATUS_PENDING) {
			bookingPending = true;
		}

		if (_log.isDebugEnabled()) {
			if (bookingUser != null) {
				_log.debug(
					"Booking user for booking " +
						calendarBooking.getCalendarBookingId() + " is " +
							bookingUser.getScreenName());
			}
			else {
				_log.debug(
					"No booking user for booking " +
						calendarBooking.getCalendarBookingId());
			}

			if (userOrganizer != null) {
				_log.debug(
					"User organizer is " + userOrganizer.getScreenName());
			}
		}

		Property eventStatus = vEvent.getProperty(Status.STATUS);

		if (eventStatus != null) {
			vEvent.getProperties(
			).remove(
				eventStatus
			);
		}

		if (bookingPending) {
			vEvent.getProperties(
			).add(
				Status.VEVENT_TENTATIVE
			);
		}
		else {
			vEvent.getProperties(
			).add(
				Status.VEVENT_CONFIRMED
			);
		}

		// update attendees status

		List<CalendarBooking> childCalendarBookings =
			calendarBooking.getParentCalendarBooking(
			).getChildCalendarBookings();

		List<Attendee> attendees = new ArrayList<>();
		List<String> attendeesEmails = new ArrayList<>();
		Iterator<Attendee> attendeesIterator = vEvent.getProperties(
			Attendee.ATTENDEE
		).iterator();

		while (attendeesIterator.hasNext()) {
			attendees.add(attendeesIterator.next());
		}

		for (CalendarBooking childCalendarBooking : childCalendarBookings) {
			boolean attendeeFound = false;

			Optional<User> childBookingUser =
				CalendarHelperUtil.getCalendarResourceUser(
					childCalendarBooking.getCalendarResource());

			if (_log.isDebugEnabled()) {
				if (childBookingUser.isPresent()) {
					_log.debug(
						"User for calendar booking " +
							childCalendarBooking.getCalendarBookingId() +
								" is " +
									childBookingUser.get(
									).getScreenName());
				}
				else {
					_log.debug(
						"No User found for calendar booking " +
							childCalendarBooking.getCalendarBookingId());
				}
			}

			if (!childBookingUser.isPresent()) {
				continue;
			}

			for (Attendee attendee : attendees) {
				String emailAddress = StringUtil.replace(
					attendee.getValue(), "mailto:", StringPool.BLANK);

				if (!Validator.isEmailAddress(emailAddress)) {
					continue;
				}

				if (attendeesEmails.contains(emailAddress)) {
					continue;
				}

				if (emailAddress.equals(
						childBookingUser.get().getEmailAddress())) {

					attendeeFound = true;
					attendeesEmails.add(emailAddress);

					Parameter partStatParameter = attendee.getParameter(
						PartStat.PARTSTAT);

					if (partStatParameter == null) {
						continue;
					}

					attendee.getParameters(
					).remove(
						partStatParameter
					);

					switch (childCalendarBooking.getStatus()) {
						case WorkflowConstants.STATUS_DENIED:
							attendee.getParameters(
							).add(
								PartStat.DECLINED
							);

							break;
						case WorkflowConstants.STATUS_APPROVED:
							attendee.getParameters(
							).add(
								PartStat.ACCEPTED
							);
							attendee.getParameters(
							).removeAll(
								Rsvp.RSVP
							);

							break;
						case CalendarBookingWorkflowConstants.STATUS_MAYBE:
							attendee.getParameters(
							).add(
								PartStat.TENTATIVE
							);

							break;
						default:
							attendee.getParameters(
							).add(
								PartStat.NEEDS_ACTION
							);

							break;
					}

					break;
				}
			}

			String emailAddress = childBookingUser.get(
			).getEmailAddress();

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Child booking user email address is " + emailAddress);
			}

			if ((userOrganizer != null) &&
				childBookingUser.get().equals(userOrganizer)) {

				continue;
			}

			if (!attendeeFound && !attendeesEmails.contains(emailAddress) &&
				!attendeesEmails.contains(
					childBookingUser.get().getEmailAddress())) {

				Attendee attendee = AttendeeUtil.create(
					childBookingUser.get(
					).getEmailAddress(),
					childBookingUser.get(
					).getFullName(),
					true, childCalendarBooking.getStatus());

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Add attendee " + attendee.toString() + " to booking " +
							calendarBooking.getCalendarBookingId());
				}

				vEvent.getProperties(
				).add(
					attendee
				);
				attendeesEmails.add(emailAddress);
			}
		}

		// add current user as attendee

		if (bookingPending && hasUpdatePermissions && (bookingUser != null) &&
			bookingUser.equals(user) && !bookingUser.equals(userOrganizer) &&
			!attendeesEmails.contains(user.getEmailAddress())) {

			Property methodProperty = iCalCalendar.getProperty(Method.METHOD);

			iCalCalendar.getProperties(
			).remove(
				methodProperty
			);
			iCalCalendar.getProperties(
			).add(
				Method.REQUEST
			);

			Attendee attendee = AttendeeUtil.create(
				user.getEmailAddress(), user.getFullName(), true,
				WorkflowConstants.STATUS_PENDING);

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Adding current user (" + user.getEmailAddress() +
						") as attendee of booking" +
							calendarBooking.getCalendarBookingId());
			}

			vEvent.getProperties(
			).add(
				attendee
			);
		}
	}

	protected void updateICSExternalAttendees(
			VEvent vEvent, CalendarBooking calendarBooking)
		throws PortalException {

		PropertyList propertyList = vEvent.getProperties();

		List<CalendarBooking> childCalendarBookings =
			calendarBooking.getChildCalendarBookings();

		for (CalendarBooking childBooking : childCalendarBookings) {
			CalendarResource calendarResource = childBooking.getCalendarResource();

			_calendarHelper.getCalendarResourceScheduleContact(
					calendarResource)
				.ifPresent( scheduleContact -> propertyList.add(
					_toICalAttendee(
						scheduleContact.getCommonName(),
						scheduleContact.getEmailAddress(),
						childBooking.getStatus())
					)
				);
		}
	}

	protected CalendarBooking updateTitleAndDescription(
			CalendarBooking calendarBooking, VEvent vEvent)
		throws PortalException {

		Locale locale;

		try {
			Calendar calendar = calendarBooking.getCalendar();

			long userId = calendar.getUserId();
			User user = _userLocalService.getUser(userId);

			locale = user.getLocale();
		}
		catch (PortalException e) {
			locale = Locale.getDefault();
		}

		String title = calendarBooking.getTitle(locale);
		String description = calendarBooking.getDescription(locale);

		Map<Locale, String> titleMap = new HashMap<>();
		Map<Locale, String> descriptionMap = new HashMap<>();

		for (Locale l : LanguageUtil.getAvailableLocales()) {
			titleMap.put(l, title);
			descriptionMap.put(l, description);
		}

		calendarBooking.setTitleMap(titleMap);
		calendarBooking.setDescriptionMap(descriptionMap);

		return _calendarBookingLocalService.updateCalendarBooking(
			calendarBooking);
	}

	private List<String> _getNotificationRecipients(Calendar calendar)
		throws Exception {

		CalendarResource calendarResource = calendar.getCalendarResource();

		List<Role> roles = _roleLocalService.getResourceRoles(
			calendar.getCompanyId(), Calendar.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(calendar.getPrimaryKey()), "MANAGE_BOOKINGS");

		/*	getResourceBlockRoles(
			calendar.getResourceBlockId(), Calendar.class.getName(),
			"MANAGE_BOOKINGS");*/

		List<String> notificationRecipients = new ArrayList<>();

		for (Role role : roles) {
			String name = role.getName();

			if (name.equals(RoleConstants.OWNER)) {
				User calendarResourceUser = _userLocalService.fetchUser(
					calendarResource.getUserId());

				if (calendarResourceUser != null) {
					notificationRecipients.add(
						calendarResourceUser.getEmailAddress());
				}

				User calendarUser = _userLocalService.fetchUser(
					calendar.getUserId());

				if ((calendarUser != null) && (calendarResourceUser != null) &&
					(calendarResourceUser.getUserId() !=
						calendarUser.getUserId())) {

					notificationRecipients.add(calendarUser.getEmailAddress());
				}
			}
			else {
				List<User> roleUsers = _userLocalService.getRoleUsers(
					role.getRoleId());

				for (User roleUser : roleUsers) {
					PermissionChecker permissionChecker =
						PermissionCheckerFactoryUtil.create(roleUser);

					if (!_calendarModelResourcePermission.contains(
							permissionChecker, calendar, "MANAGE_BOOKINGS")) {

						continue;
					}

					notificationRecipients.add(roleUser.getEmailAddress());
				}
			}
		}

		return notificationRecipients;
	}

	private void _replaceDescription(VEvent vEvent, XProperty vEventXAltDesc) {
		String vEventAltDescValue = vEventXAltDesc.getValue();

		Property vEventDescription = vEvent.getProperty(Property.DESCRIPTION);

		PropertyList vEventProperties = vEvent.getProperties();

		vEventProperties.remove(vEventDescription);

		Description description = new Description(vEventAltDescValue);

		vEventProperties.add(description);
	}

	private Attendee _toICalAttendee(String attendeeString)
		throws SanitizerException {

		StringBuilder sb = new StringBuilder(9);

		sb.append("BEGIN:VCALENDAR");
		sb.append(StringPool.NEW_LINE);
		sb.append("BEGIN:VEVENT");
		sb.append(StringPool.NEW_LINE);
		sb.append(attendeeString);
		sb.append(StringPool.NEW_LINE);
		sb.append("END:VEVENT");
		sb.append(StringPool.NEW_LINE);
		sb.append("END:VCALENDAR");

		CalendarBuilder calendarBuilder = new CalendarBuilder();

		UnsyncStringReader unsyncStringReader = new UnsyncStringReader(
			sb.toString());

		Attendee attendee = new Attendee();

		try {
			net.fortuna.ical4j.model.Calendar iCalCalendar =
				calendarBuilder.build(unsyncStringReader);

			net.fortuna.ical4j.model.Component vEvent =
				iCalCalendar.getComponent(VEvent.VEVENT);

			Property attendeeProperty = vEvent.getProperty(Attendee.ATTENDEE);

			attendee.setValue(attendeeProperty.getValue());

			Iterator<Parameter> iterator = attendeeProperty.getParameters(
			).iterator();

			while (iterator.hasNext()) {
				Parameter parameter = iterator.next();

				attendee.getParameters(
				).add(
					parameter
				);
			}
		}
		catch (Exception e) {
			_log.error("Error parsing attendee " + attendeeString, e);

			throw new SanitizerException(e);
		}

		return attendee;
	}

	private Attendee _toICalAttendee(
		String fullName, String emailAddress, int status) {

		Attendee attendee = new Attendee();

		URI uri = URI.create("mailto:".concat(emailAddress));

		attendee.setCalAddress(uri);

		Cn cn = new Cn(fullName);

		ParameterList parameters = attendee.getParameters();

		parameters.add(cn);
		parameters.add(CuType.INDIVIDUAL);
		parameters.add(net.fortuna.ical4j.model.parameter.Role.REQ_PARTICIPANT);
		parameters.add(Rsvp.TRUE);

		if (status == WorkflowConstants.STATUS_APPROVED) {
			parameters.add(PartStat.ACCEPTED);
		}
		else {
			parameters.add(PartStat.NEEDS_ACTION);
		}

		return attendee;
	}

	private void _updateBookingModifiedDate(
			CalendarBooking calendarBooking, Date date)
		throws PortalException {

		CalendarBooking parentCalendarBooking =
			calendarBooking.getParentCalendarBooking();

		if (calendarBooking.getModifiedDate().getTime() < date.getTime()) {
			parentCalendarBooking.setModifiedDate(date);

			parentCalendarBooking =
				_calendarBookingLocalService.updateCalendarBooking(
					parentCalendarBooking);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DefaultICSContentListener.class);

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	private CalendarBookingLocalService _calendarBookingLocalService;

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	private CalendarHelper _calendarHelper;

	private ModelResourcePermission<Calendar> _calendarModelResourcePermission;

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	private ClassNameLocalService _classNameLocalService;

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	private RoleLocalService _roleLocalService;

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	private ScheduleContactLocalService _scheduleContactLocalService;

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	private UserLocalService _userLocalService;

}