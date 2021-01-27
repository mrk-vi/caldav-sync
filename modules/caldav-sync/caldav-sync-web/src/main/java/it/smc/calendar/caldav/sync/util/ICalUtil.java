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

package it.smc.calendar.caldav.sync.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.ReleaseInfo;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.component.VTimeZone;
import net.fortuna.ical4j.model.property.Attendee;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Fabio Pezzutto
 */
public class ICalUtil {

	public static Attendee fetchAttendee(String data, String email)
		throws Exception {

		PropertyList attendees = _getAttendees(data);

		for (Object attendeeO : attendees) {
			Attendee attendee = (Attendee) attendeeO;
			String attendeeEmail =
				attendee.getCalAddress().getSchemeSpecificPart();
			if (attendeeEmail.equals(email)) {
				return attendee;
			}
		}

		return null;
	}
	public static String getVEventUid(String data) throws Exception {
		List<String> emailAddresses = new ArrayList<>();

		CalendarBuilder calendarBuilder = new CalendarBuilder();

		UnsyncStringReader ics = new UnsyncStringReader(data);

		Calendar calendar = calendarBuilder.build(ics);

		VEvent vEvent = (VEvent)calendar.getComponent(Component.VEVENT);

		return vEvent.getUid().getValue();
	}

	public static Calendar getVTimeZoneCalendar() {
		TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance(
		).createRegistry();

		TimeZone timeZone = registry.getTimeZone("GMT");

		VTimeZone vTimeZone = timeZone.getVTimeZone();

		Calendar iCalCalendar = new Calendar();

		PropertyList propertiesList = iCalCalendar.getProperties();

		ProdId prodId = new ProdId(
			"-//Liferay Inc//Liferay Portal " + ReleaseInfo.getVersion() +
				"//EN");

		propertiesList.add(prodId);
		propertiesList.add(Version.VERSION_2_0);

		iCalCalendar.getComponents(
		).add(
			vTimeZone
		);

		return iCalCalendar;
	}

	public static List<String> getAttendeeEmailAddresses(String data)
		throws Exception {

		List<String> emailAddresses = new ArrayList<>();

		PropertyList attendees = _getAttendees(data);

		for (Object attendee : attendees) {
			emailAddresses.add(
				_getEmailAddress(((Attendee)attendee).getValue()));
		}

		return emailAddresses;
	}

	public static List<String> getEmailAddresses(String data)
		throws Exception {

		List<String> emailAddresses = new ArrayList<>();

		emailAddresses.add(getOrganizerEmailAddress(data));

		emailAddresses.addAll(getAttendeeEmailAddresses(data));

		return emailAddresses;
	}

	public static Organizer getOrganizer(String data) throws Exception {
		CalendarBuilder calendarBuilder = new CalendarBuilder();

		UnsyncStringReader ics = new UnsyncStringReader(data);

		Calendar calendar = calendarBuilder.build(ics);

		VEvent vEvent = (VEvent)calendar.getComponent(Component.VEVENT);

		if (Validator.isNull(vEvent)) {
			return null;
		}

		return vEvent.getOrganizer();
	}

	public static String getOrganizerEmailAddress(String data)
		throws Exception {

		Organizer organizer = getOrganizer(data);

		if (Validator.isNull(organizer)) {
			return null;
		}

		return _getEmailAddress(organizer.getValue());
	}

	private static PropertyList _getAttendees(String data)
		throws IOException, ParserException {
		CalendarBuilder calendarBuilder = new CalendarBuilder();

		UnsyncStringReader ics = new UnsyncStringReader(data);

		Calendar calendar = calendarBuilder.build(ics);

		VEvent vEvent = (VEvent)calendar.getComponent(Component.VEVENT);

		return vEvent.getProperties(Property.ATTENDEE);
	}

	private static String _getEmailAddress(String attendee) {
		return StringUtil.replace(
			attendee.toLowerCase(Locale.ROOT),
			"mailto:", StringPool.BLANK);
	}
	
}