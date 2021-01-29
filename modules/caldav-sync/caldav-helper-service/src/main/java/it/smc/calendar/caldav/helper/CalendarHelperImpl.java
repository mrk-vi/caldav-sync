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

package it.smc.calendar.caldav.helper;

import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.UserLocalService;

import it.smc.calendar.caldav.helper.api.CalendarHelper;

import java.util.Optional;

import it.smc.calendar.caldav.schedule.contact.model.ScheduleContact;
import it.smc.calendar.caldav.schedule.contact.service.ScheduleContactLocalService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Fabio Pezzutto
 */
@Component(immediate = true, service = CalendarHelper.class)
public class CalendarHelperImpl implements CalendarHelper {

	@Override
	public Optional<ScheduleContact> getCalendarResourceScheduleContact(
		CalendarResource calendarResource) {

		if (!isCalendarResourceScheduleContactCalendar(calendarResource)) {
			return Optional.empty();
		}

		ScheduleContact scheduleContact =
			_scheduleContactLocalService.fetchScheduleContact(
				calendarResource.getClassPK());

		return Optional.ofNullable(scheduleContact);
	}

	public Optional<User> getCalendarResourceUser(
		CalendarResource calendarResource) {

		if (!isCalendarResourceUserCalendar(calendarResource)) {
			return Optional.empty();
		}

		User user = _userLocalService.fetchUser(
			calendarResource.getClassPK());

		return Optional.ofNullable(user);
	}

	@Override
	public boolean isCalendarResourceScheduleContactCalendar(
		CalendarResource calendarResource) {

		long classNameId =
			_classNameLocalService.getClassNameId(ScheduleContact.class);

		return calendarResource.getClassNameId() == classNameId;
	}

	@Override
	public boolean isCalendarScheduleContactCalendar(Calendar calendar) {
		try {
			return isCalendarResourceScheduleContactCalendar(
				calendar.getCalendarResource());
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		return Boolean.FALSE;
	}

	public boolean isCalendarResourceUserCalendar(
		CalendarResource calendarResource) {

		long classNameId =
			_classNameLocalService.getClassNameId(User.class);

		return calendarResource.getClassNameId() == classNameId;
	}

	public boolean isCalendarUserCalendar(Calendar calendar) {
		try {
			return isCalendarResourceUserCalendar(
				calendar.getCalendarResource());
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		return Boolean.FALSE;
	}

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	private ClassNameLocalService _classNameLocalService;

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	private ScheduleContactLocalService _scheduleContactLocalService;

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	private UserLocalService _userLocalService;

	private static Log _log = LogFactoryUtil.getLog(CalendarHelperImpl.class);

}