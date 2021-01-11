/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package it.smc.calendar.caldav.schedule.contact.service.impl;

import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarLocalService;
import com.liferay.calendar.service.CalendarResourceLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;

import com.liferay.portal.kernel.exception.EmailAddressException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;
import it.smc.calendar.caldav.schedule.contact.model.ScheduleContact;
import it.smc.calendar.caldav.schedule.contact.service.base.ScheduleContactLocalServiceBaseImpl;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicyOption;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * The implementation of the schedule contact local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>it.smc.calendar.caldav.schedule.contact.service.ScheduleContactLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ScheduleContactLocalServiceBaseImpl
 */
@Component(
	property = "model.class.name=it.smc.calendar.caldav.schedule.contact.model.ScheduleContact",
	service = AopService.class
)
public class ScheduleContactLocalServiceImpl
	extends ScheduleContactLocalServiceBaseImpl {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Use <code>it.smc.calendar.caldav.schedule.contact.service.ScheduleContactLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>it.smc.calendar.caldav.schedule.contact.service.ScheduleContactLocalServiceUtil</code>.
	 */

	@Override
	public ScheduleContact addScheduleContact(
			long companyId, String commonName, String emailAddress, ServiceContext serviceContext)
		throws PortalException {

		if (!Validator.isEmailAddress(emailAddress)) {
			throw new EmailAddressException("Invalid email address: " + emailAddress);
		}

		try {
			return findScheduleContact(companyId, emailAddress);
		}
		catch (PortalException pe) {

			long scheduleContactId = counterLocalService.increment();

			ScheduleContact scheduleContact =
				scheduleContactPersistence.create(scheduleContactId);

			if (Validator.isNull(serviceContext)) {
				serviceContext = new ServiceContext();
			}

			long userId = serviceContext.getUserId();

			if (Validator.isNull(userId)) {
				Role role = roleLocalService.getRole(
					companyId,
					RoleConstants.ADMINISTRATOR);

				userId = userLocalService.getRoleUserIds(role.getRoleId())[0];
			}

			Group group = null;

			if (Validator.isNull(serviceContext.getScopeGroupId())) {
				group = groupLocalService.getCompanyGroup(companyId);
			}
			else {
				group =
					groupLocalService.getGroup(
						serviceContext.getScopeGroupId());
			}

			if (Validator.isNull(commonName) || commonName.isEmpty()) {
				commonName = emailAddress;
			}

			User user = userLocalService.getUser(userId);

			scheduleContact.setCompanyId(companyId);
			scheduleContact.setCommonName(commonName);
			scheduleContact.setCreateDate(
				serviceContext.getCreateDate(new Date()));
			scheduleContact.setEmailAddress(emailAddress);
			scheduleContact.setGroupId(group.getGroupId());
			scheduleContact.setModifiedDate(
				serviceContext.getModifiedDate(new Date()));
			scheduleContact.setUserId(userId);
			scheduleContact.setUserName(user.getFullName());

			scheduleContactPersistence.update(scheduleContact);

			long classNameId =
				classNameLocalService.getClassNameId(ScheduleContact.class);

			Map<Locale, String> nameMap = new HashMap<>();

			for (Locale locale : LanguageUtil.getAvailableLocales()) {
				nameMap.put(locale, commonName);
			}

			calendarResourceLocalService.addCalendarResource(
				userId, group.getGroupId(), classNameId, scheduleContactId,
				StringPool.BLANK, StringPool.BLANK, nameMap,
				Collections.emptyMap(), true, serviceContext);

			return scheduleContact;
		}
	}

	@Override
	public Calendar fetchDefaultCalendar(long companyId, String emailAddress) {

		try {
			ScheduleContact scheduleContact =
				findScheduleContact(companyId, emailAddress);

			long classNameId =
				classNameLocalService.getClassNameId(ScheduleContact.class);

			CalendarResource calendarResource =
				calendarResourceLocalService.fetchCalendarResource(
					classNameId, scheduleContact.getScheduleContactId());

			return calendarResource.getDefaultCalendar();
		}
		catch (PortalException pe) {
			return null;
		}
	}

	@Override
	public ScheduleContact findScheduleContact(
			long companyId, String emailAddress)
		throws PortalException {

		return scheduleContactPersistence.findByC_E(companyId, emailAddress);
	}

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	protected CalendarResourceLocalService calendarResourceLocalService;

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	protected GroupLocalService groupLocalService;

	@Reference(policyOption = ReferencePolicyOption.GREEDY)
	protected RoleLocalService roleLocalService;
}