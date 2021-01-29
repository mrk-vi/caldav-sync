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

package it.smc.calendar.caldav.sync;

import com.liferay.calendar.constants.CalendarActionKeys;
import com.liferay.calendar.constants.CalendarPortletKeys;
import com.liferay.calendar.exception.NoSuchCalendarException;
import com.liferay.calendar.exporter.CalendarDataFormat;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarBookingLocalService;
import com.liferay.calendar.service.CalendarBookingService;
import com.liferay.calendar.service.CalendarLocalService;
import com.liferay.calendar.service.CalendarResourceLocalService;
import com.liferay.calendar.service.CalendarService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webdav.BaseWebDAVStorageImpl;
import com.liferay.portal.kernel.webdav.Resource;
import com.liferay.portal.kernel.webdav.WebDAVException;
import com.liferay.portal.kernel.webdav.WebDAVRequest;
import com.liferay.portal.kernel.webdav.WebDAVStorage;
import com.liferay.portal.kernel.webdav.methods.MethodFactory;
import com.liferay.portal.kernel.webdav.methods.MethodFactoryRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import it.smc.calendar.caldav.helper.api.CalendarHelper;
import it.smc.calendar.caldav.schedule.contact.service.ScheduleContactLocalService;
import it.smc.calendar.caldav.sync.ical.util.AttendeeUtil;
import it.smc.calendar.caldav.sync.listener.ICSContentImportExportFactoryUtil;
import it.smc.calendar.caldav.sync.listener.ICSImportExportListener;
import it.smc.calendar.caldav.sync.util.CalDAVHttpMethods;
import it.smc.calendar.caldav.sync.util.CalDAVRequestThreadLocal;
import it.smc.calendar.caldav.sync.util.CalDAVUtil;
import it.smc.calendar.caldav.sync.util.ICalUtil;
import it.smc.calendar.caldav.sync.util.ResourceNotFoundException;
import it.smc.calendar.caldav.util.CalendarUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.property.Attendee;
import net.fortuna.ical4j.model.property.Organizer;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Fabio Pezzutto
 * @author bta
 * @author Luca Comin
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CalendarPortletKeys.CALENDAR,
		"webdav.storage.token=calendar"
	},
	service = WebDAVStorage.class
)
public class LiferayCalDAVStorageImpl extends BaseWebDAVStorageImpl {

	@Override
	public int deleteResource(WebDAVRequest webDAVRequest)
		throws WebDAVException {

		try {
			CalendarBooking calendarBooking = (CalendarBooking)getResource(
				webDAVRequest
			).getModel();

			long currentUserId = CalDAVUtil.getUserId(webDAVRequest);
			User currentUser = _userLocalService.fetchUser(currentUserId);

			CalendarResource calendarResource =
				calendarBooking.getCalendarResource();

			if (!calendarBooking.isMasterBooking() &&
				_calendarHelper.isCalendarResourceUserCalendar(
					calendarResource)) {

				Optional<User> calendarResourceUser =
					_calendarHelper.getCalendarResourceUser(
						calendarResource);

				if (calendarResourceUser.isPresent() &&
					currentUser.equals(calendarResourceUser.get())) {

					ServiceContext serviceContext =
						ServiceContextFactory.getInstance(
							CalendarBooking.class.getName(),
							webDAVRequest.getHttpServletRequest());

					_calendarBookingLocalService.updateStatus(
						currentUserId, calendarBooking,
						WorkflowConstants.STATUS_DENIED, serviceContext);

					return HttpServletResponse.SC_NO_CONTENT;
				}
			}

			_calendarBookingService.deleteCalendarBooking(
				calendarBooking.getCalendarBookingId());

			return HttpServletResponse.SC_NO_CONTENT;
		}
		catch (PrincipalException pe) {
			return HttpServletResponse.SC_FORBIDDEN;
		}
		catch (NoSuchCalendarException nsfe) {
			return HttpServletResponse.SC_CONFLICT;
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe, pe);
			}

			return HttpServletResponse.SC_CONFLICT;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	@Override
	public MethodFactory getMethodFactory() {
		return MethodFactoryRegistryUtil.getMethodFactory(
			CalDAVMethodFactory.class.getName());
	}

	@Override
	public Resource getResource(WebDAVRequest webDAVRequest)
		throws WebDAVException {

		try {
			String[] pathArray = webDAVRequest.getPathArray();
			String method = webDAVRequest.getHttpServletRequest(
			).getMethod();

			if (CalDAVUtil.isPrincipalRequest(webDAVRequest)) {
				long userid = GetterUtil.getLong(pathArray[2]);
				User user = null;

				try {
					user = _userService.getUserById(userid);
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn(e);
					}
				}

				if (user == null) {
					throw new WebDAVException(
						"No user were found with id " + pathArray[2]);
				}

				return toResource(webDAVRequest, user);
			}

			// calendar resource collection request

			String calendarResourceId = pathArray[0];
			CalendarResource calendarResource = null;

			if (calendarResourceId.length() < 16) {
				calendarResource =
					_calendarResourceLocalService.getCalendarResource(
						GetterUtil.getLong(calendarResourceId));
			}
			else {
				calendarResource =
					_calendarResourceLocalService.
						fetchCalendarResourceByUuidAndGroupId(
							calendarResourceId, webDAVRequest.getGroupId());
			}

			if (calendarResource == null) {
				throw new ResourceNotFoundException(
					"No calendar resource were found for GUID/ID " +
						calendarResourceId);
			}

			if (CalDAVUtil.isCalendarBookingRequest(webDAVRequest) &&
				!method.equals(CalDAVHttpMethods.PUT)) {

				// calendar booking resource

				String resourceName = pathArray[pathArray.length - 1];

				String resourceExtension = StringPool.PERIOD.concat(
					FileUtil.getExtension(resourceName));

				if (resourceName.endsWith(resourceExtension)) {
					String resourceShortName = StringUtil.replace(
						resourceName, resourceExtension, StringPool.BLANK);

					long calendarBookingId = GetterUtil.getLong(
						resourceShortName);

					CalendarBooking calendarBooking = null;

					if (calendarBookingId > 0) {
						calendarBooking =
							_calendarBookingService.fetchCalendarBooking(
								calendarBookingId);
					}
					else {
						calendarBooking =
							_calendarBookingLocalService.
								fetchCalendarBooking(
									GetterUtil.getLong(pathArray[2]),
									resourceShortName);
					}

					if (calendarBooking == null) {
						throw new ResourceNotFoundException(
							"Calendar booking not found with id: " +
								calendarBookingId);
					}

					_calendarModelResourcePermission.check(
						webDAVRequest.getPermissionChecker(),
						calendarBooking.getCalendar(), ActionKeys.VIEW);

					return toResource(webDAVRequest, calendarBooking);
				}
			}

			if (CalDAVUtil.isCalendarRequest(webDAVRequest)) {

				// calendar request

				String calendarId = pathArray[2];
				Calendar calendar = null;

				if (calendarId.length() < 16) {
					calendar = _calendarLocalService.getCalendar(
						GetterUtil.getLong(calendarId));
				}
				else {
					calendar =
						_calendarLocalService.fetchCalendarByUuidAndGroupId(
							calendarId, webDAVRequest.getGroupId());
				}

				if (calendar == null) {
					throw new ResourceNotFoundException(
						"No calendar were found for GUID/ID " + calendarId);
				}

				_calendarModelResourcePermission.check(
					webDAVRequest.getPermissionChecker(), calendar,
					ActionKeys.VIEW);

				return toResource(webDAVRequest, calendar);
			}

			return toResource(webDAVRequest, calendarResource);
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	@Override
	public List<Resource> getResources(WebDAVRequest webDAVRequest)
		throws WebDAVException {

		try {
			String[] pathArray = webDAVRequest.getPathArray();

			// calendar resource collection request

			CalendarResource calendarResource;

			if (CalDAVUtil.isPrincipalRequest(webDAVRequest)) {
				long userid = GetterUtil.getLong(pathArray[2]);
				User user = null;

				try {
					user = _userService.getUserById(userid);
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn(e);
					}
				}

				if (user == null) {
					throw new WebDAVException(
						"No user were found with id " + pathArray[2]);
				}

				calendarResource =
					_calendarResourceLocalService.fetchCalendarResource(
						PortalUtil.getClassNameId(User.class),
						user.getPrimaryKey());
			}
			else {
				String calendarResourceId = pathArray[0];

				if (calendarResourceId.length() < 16) {
					calendarResource =
						_calendarResourceLocalService.getCalendarResource(
							GetterUtil.getLong(calendarResourceId));
				}
				else {
					calendarResource =
						_calendarResourceLocalService.
							fetchCalendarResourceByUuidAndGroupId(
								calendarResourceId, webDAVRequest.getGroupId());
				}
			}

			if (calendarResource == null) {
				throw new WebDAVException("No calendar resource were found");
			}

			if (CalDAVUtil.isCalendarRequest(webDAVRequest)) {
				return toCalendarBookingResources(webDAVRequest);
			}

			return toCalendarResources(webDAVRequest, calendarResource);
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	@Override
	public boolean isSupportsClassTwo() {
		return false;
	}

	@Override
	public int moveSimpleResource(
			WebDAVRequest webDAVRequest, Resource resource, String destination,
			boolean overwrite)
		throws WebDAVException {

		try {
			CalendarBooking calendarBooking =
				(CalendarBooking)resource.getModel();

			String[] parts = destination.split(StringPool.SLASH);

			long targetCalendarId = Long.parseLong(parts[6]);

			Calendar targetCalendar = _calendarService.fetchCalendar(
				targetCalendarId);

			CalendarBooking bookingCopy =
				_calendarBookingLocalService.fetchCalendarBooking(
					targetCalendarId, calendarBooking.getVEventUid());

			if (bookingCopy != null) {
				_calendarBookingService.deleteCalendarBooking(
					calendarBooking.getCalendarBookingId());
			}
			else {
				_calendarModelResourcePermission.check(
					webDAVRequest.getPermissionChecker(),
					calendarBooking.getCalendar(), ActionKeys.UPDATE);
				_calendarModelResourcePermission.check(
					webDAVRequest.getPermissionChecker(), targetCalendar,
					ActionKeys.UPDATE);

				calendarBooking.setCalendarId(targetCalendarId);

				_calendarBookingLocalService.updateCalendarBooking(
					calendarBooking);
			}

			return HttpServletResponse.SC_OK;
		}
		catch (PrincipalException pe) {
			return HttpServletResponse.SC_FORBIDDEN;
		}
		catch (NoSuchCalendarException nsfe) {
			return HttpServletResponse.SC_CONFLICT;
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe, pe);
			}

			return HttpServletResponse.SC_CONFLICT;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	@Override
	public int putResource(WebDAVRequest webDAVRequest) throws WebDAVException {
		try {
			String data = CalDAVRequestThreadLocal.getRequestContent();

			Calendar calendar = (Calendar)getResource(
				webDAVRequest
			).getModel();

			Calendar targetCalendar = calendar;

			Organizer organizer = ICalUtil.getOrganizer(data);

			User calendarUser =
				_userLocalService.getUser(calendar.getUserId());

			String userEmailAddress = calendarUser.getEmailAddress();

			if (Validator.isNotNull(organizer)) {
				String organizerEmailAddress =
					organizer.getCalAddress().getSchemeSpecificPart();

				User organizerUser = _userLocalService.fetchUserByEmailAddress(
					calendar.getCompanyId(), organizerEmailAddress);

				if (Validator.isNull(organizerUser)) {
					List<String> attendeeEmailAddresses =
						ICalUtil.getAttendeeEmailAddresses(data);

					if (!attendeeEmailAddresses.contains(userEmailAddress)) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"No user email address found in request data");
						}

						return HttpServletResponse.SC_BAD_REQUEST;
					}

					Parameter commonNameParam =
						organizer.getParameter(Parameter.CN);

					String commonName = organizerEmailAddress;

					if (Validator.isNotNull(commonNameParam)) {
						commonName = commonNameParam.getValue();
					}

					targetCalendar =
						_scheduleContactLocalService.getDefaultCalendar(
							calendar.getCompanyId(), commonName,
							organizerEmailAddress, new ServiceContext());
				}
			}

			_calendarModelResourcePermission.check(
				webDAVRequest.getPermissionChecker(),
				calendar.getCalendarId(),
				CalendarActionKeys.MANAGE_BOOKINGS);

			String currentPrincipal = PrincipalThreadLocal.getName();

			PermissionChecker currentPermissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			ICSImportExportListener icsContentListener =
				ICSContentImportExportFactoryUtil.newInstance();

			data = icsContentListener.beforeContentImported(
				data, targetCalendar);

			try {
				long userId = targetCalendar.getUserId();

				PrincipalThreadLocal.setName(userId);

				PermissionChecker permissionChecker =
					PermissionCheckerFactoryUtil.create(
						_userLocalService.getUser(userId));

				PermissionThreadLocal.setPermissionChecker(
					permissionChecker);

				_calendarLocalService.importCalendar(
					targetCalendar.getCalendarId(), data,
					CalendarDataFormat.ICAL.getValue());

			}
			finally {
				PrincipalThreadLocal.setName(currentPrincipal);

				PermissionThreadLocal.setPermissionChecker(
					currentPermissionChecker);
			}

			icsContentListener.afterContentImported(data, targetCalendar);

			String vEventUid = ICalUtil.getVEventUid(data);

			Attendee attendee = ICalUtil.fetchAttendee(data, userEmailAddress);

			CalendarBooking calendarBooking =
				_calendarBookingLocalService.fetchCalendarBooking(
					calendar.getCalendarId(), vEventUid);

			if (Validator.isNotNull(attendee) &&
				Validator.isNotNull(calendarBooking)) {

				int status = AttendeeUtil.getStatus(attendee,
					calendarBooking.getStatus());

				_calendarBookingLocalService.updateStatus(
					calendarUser.getUserId(), calendarBooking, status,
					new ServiceContext());
			}

			return HttpServletResponse.SC_CREATED;
		}
		catch (PrincipalException pe) {
			return HttpServletResponse.SC_FORBIDDEN;
		}
		catch (NoSuchCalendarException nsfe) {
			return HttpServletResponse.SC_CONFLICT;
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe, pe);
			}

			return HttpServletResponse.SC_CONFLICT;
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	@Reference(
		target = "(model.class.name=com.liferay.calendar.model.Calendar)",
		unbind = "-"
	)
	protected void setModelPermissionChecker(
		ModelResourcePermission<Calendar> modelResourcePermission) {

		_calendarModelResourcePermission = modelResourcePermission;
	}

	protected List<Resource> toCalendarBookingResources(
			WebDAVRequest webDAVRequest)
		throws PortalException {

		Calendar calendar = (Calendar)getResource(
			webDAVRequest
		).getModel();

		List<CalendarBooking> calendarBookings =
			CalendarUtil.getCalendarBookings(
				webDAVRequest.getPermissionChecker(), calendar, null, null);

		List<Resource> resources = new ArrayList<>();

		for (CalendarBooking calendarBooking : calendarBookings) {
			resources.add(toResource(webDAVRequest, calendarBooking));
		}

		return resources;
	}

	protected List<Resource> toCalendarResources(
			WebDAVRequest webDAVRequest, CalendarResource calendarResource)
		throws PortalException {

		List<Calendar> calendars;

		if (CalDAVUtil.isIOS(webDAVRequest) ||
			CalDAVUtil.isMacOSX(webDAVRequest) ||
			CalDAVUtil.isOpenSync(webDAVRequest) ||
			CalDAVUtil.isICal(webDAVRequest)) {

			calendars = CalendarUtil.getAllCalendars(
				webDAVRequest.getPermissionChecker());
		}
		else {
			calendars = CalendarUtil.getCalendarResourceCalendars(
				calendarResource);
		}

		List<Resource> resources = new ArrayList<>();

		for (Calendar calendar : calendars) {
			resources.add(toResource(webDAVRequest, calendar));
		}

		return resources;
	}

	protected Resource toResource(
		WebDAVRequest webDAVRequest, Calendar calendar) {

		String parentPath = getRootPath() + webDAVRequest.getPath();

		Locale locale = PortalUtil.getLocale(
			webDAVRequest.getHttpServletRequest());

		return new CalendarResourceImpl(calendar, parentPath, locale);
	}

	protected Resource toResource(
		WebDAVRequest webDAVRequest, CalendarBooking calendarBooking) {

		String parentPath = getRootPath() + webDAVRequest.getPath();

		Locale locale = PortalUtil.getLocale(
			webDAVRequest.getHttpServletRequest());

		return new CalendarBookingResourceImpl(
			calendarBooking, parentPath, locale);
	}

	protected Resource toResource(
		WebDAVRequest webDAVRequest, CalendarResource calendarResource) {

		String parentPath = getRootPath() + webDAVRequest.getPath();

		Locale locale = PortalUtil.getLocale(
			webDAVRequest.getHttpServletRequest());

		return new CalendarResourceResourceImpl(
			calendarResource, parentPath, locale);
	}

	protected Resource toResource(WebDAVRequest webDAVRequest, User user) {
		String parentPath = getRootPath() + webDAVRequest.getPath();

		Locale locale = PortalUtil.getLocale(
			webDAVRequest.getHttpServletRequest());

		return new UserResourceImpl(user, parentPath, locale);
	}

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	protected CalendarLocalService _calendarLocalService;

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	protected CalendarService _calendarService;

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	protected CalendarBookingLocalService _calendarBookingLocalService;

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	protected CalendarBookingService _calendarBookingService;

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	protected CalendarResourceLocalService _calendarResourceLocalService;

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	protected CalendarHelper _calendarHelper;

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	protected UserLocalService _userLocalService;

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	protected UserService _userService;

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	protected ScheduleContactLocalService _scheduleContactLocalService;

	private static Log _log = LogFactoryUtil.getLog(
		LiferayCalDAVStorageImpl.class);

	private ModelResourcePermission<Calendar> _calendarModelResourcePermission;


}