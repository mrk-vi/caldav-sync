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

package it.smc.calendar.caldav.schedule.contact.service.persistence;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import it.smc.calendar.caldav.schedule.contact.model.ScheduleContact;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the schedule contact service. This utility wraps <code>it.smc.calendar.caldav.schedule.contact.service.persistence.impl.ScheduleContactPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ScheduleContactPersistence
 * @generated
 */
public class ScheduleContactUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(ScheduleContact scheduleContact) {
		getPersistence().clearCache(scheduleContact);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, ScheduleContact> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ScheduleContact> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ScheduleContact> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ScheduleContact> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ScheduleContact> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ScheduleContact update(ScheduleContact scheduleContact) {
		return getPersistence().update(scheduleContact);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ScheduleContact update(
		ScheduleContact scheduleContact, ServiceContext serviceContext) {

		return getPersistence().update(scheduleContact, serviceContext);
	}

	/**
	 * Returns all the schedule contacts where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching schedule contacts
	 */
	public static List<ScheduleContact> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	 * Returns a range of all the schedule contacts where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ScheduleContactModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of schedule contacts
	 * @param end the upper bound of the range of schedule contacts (not inclusive)
	 * @return the range of matching schedule contacts
	 */
	public static List<ScheduleContact> findByUuid(
		String uuid, int start, int end) {

		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	 * Returns an ordered range of all the schedule contacts where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ScheduleContactModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of schedule contacts
	 * @param end the upper bound of the range of schedule contacts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching schedule contacts
	 */
	public static List<ScheduleContact> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ScheduleContact> orderByComparator) {

		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the schedule contacts where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ScheduleContactModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of schedule contacts
	 * @param end the upper bound of the range of schedule contacts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching schedule contacts
	 */
	public static List<ScheduleContact> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ScheduleContact> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid(
			uuid, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first schedule contact in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching schedule contact
	 * @throws NoSuchScheduleContactException if a matching schedule contact could not be found
	 */
	public static ScheduleContact findByUuid_First(
			String uuid, OrderByComparator<ScheduleContact> orderByComparator)
		throws it.smc.calendar.caldav.schedule.contact.exception.
			NoSuchScheduleContactException {

		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the first schedule contact in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching schedule contact, or <code>null</code> if a matching schedule contact could not be found
	 */
	public static ScheduleContact fetchByUuid_First(
		String uuid, OrderByComparator<ScheduleContact> orderByComparator) {

		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	 * Returns the last schedule contact in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching schedule contact
	 * @throws NoSuchScheduleContactException if a matching schedule contact could not be found
	 */
	public static ScheduleContact findByUuid_Last(
			String uuid, OrderByComparator<ScheduleContact> orderByComparator)
		throws it.smc.calendar.caldav.schedule.contact.exception.
			NoSuchScheduleContactException {

		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the last schedule contact in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching schedule contact, or <code>null</code> if a matching schedule contact could not be found
	 */
	public static ScheduleContact fetchByUuid_Last(
		String uuid, OrderByComparator<ScheduleContact> orderByComparator) {

		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	 * Returns the schedule contacts before and after the current schedule contact in the ordered set where uuid = &#63;.
	 *
	 * @param scheduleContactId the primary key of the current schedule contact
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next schedule contact
	 * @throws NoSuchScheduleContactException if a schedule contact with the primary key could not be found
	 */
	public static ScheduleContact[] findByUuid_PrevAndNext(
			long scheduleContactId, String uuid,
			OrderByComparator<ScheduleContact> orderByComparator)
		throws it.smc.calendar.caldav.schedule.contact.exception.
			NoSuchScheduleContactException {

		return getPersistence().findByUuid_PrevAndNext(
			scheduleContactId, uuid, orderByComparator);
	}

	/**
	 * Removes all the schedule contacts where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	 * Returns the number of schedule contacts where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching schedule contacts
	 */
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	 * Returns the schedule contact where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchScheduleContactException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching schedule contact
	 * @throws NoSuchScheduleContactException if a matching schedule contact could not be found
	 */
	public static ScheduleContact findByUUID_G(String uuid, long groupId)
		throws it.smc.calendar.caldav.schedule.contact.exception.
			NoSuchScheduleContactException {

		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the schedule contact where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching schedule contact, or <code>null</code> if a matching schedule contact could not be found
	 */
	public static ScheduleContact fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the schedule contact where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching schedule contact, or <code>null</code> if a matching schedule contact could not be found
	 */
	public static ScheduleContact fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		return getPersistence().fetchByUUID_G(uuid, groupId, useFinderCache);
	}

	/**
	 * Removes the schedule contact where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the schedule contact that was removed
	 */
	public static ScheduleContact removeByUUID_G(String uuid, long groupId)
		throws it.smc.calendar.caldav.schedule.contact.exception.
			NoSuchScheduleContactException {

		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the number of schedule contacts where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching schedule contacts
	 */
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	 * Returns all the schedule contacts where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching schedule contacts
	 */
	public static List<ScheduleContact> findByUuid_C(
		String uuid, long companyId) {

		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of all the schedule contacts where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ScheduleContactModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of schedule contacts
	 * @param end the upper bound of the range of schedule contacts (not inclusive)
	 * @return the range of matching schedule contacts
	 */
	public static List<ScheduleContact> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	 * Returns an ordered range of all the schedule contacts where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ScheduleContactModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of schedule contacts
	 * @param end the upper bound of the range of schedule contacts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching schedule contacts
	 */
	public static List<ScheduleContact> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ScheduleContact> orderByComparator) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the schedule contacts where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ScheduleContactModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of schedule contacts
	 * @param end the upper bound of the range of schedule contacts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching schedule contacts
	 */
	public static List<ScheduleContact> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ScheduleContact> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findByUuid_C(
			uuid, companyId, start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Returns the first schedule contact in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching schedule contact
	 * @throws NoSuchScheduleContactException if a matching schedule contact could not be found
	 */
	public static ScheduleContact findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ScheduleContact> orderByComparator)
		throws it.smc.calendar.caldav.schedule.contact.exception.
			NoSuchScheduleContactException {

		return getPersistence().findByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the first schedule contact in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching schedule contact, or <code>null</code> if a matching schedule contact could not be found
	 */
	public static ScheduleContact fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ScheduleContact> orderByComparator) {

		return getPersistence().fetchByUuid_C_First(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last schedule contact in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching schedule contact
	 * @throws NoSuchScheduleContactException if a matching schedule contact could not be found
	 */
	public static ScheduleContact findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ScheduleContact> orderByComparator)
		throws it.smc.calendar.caldav.schedule.contact.exception.
			NoSuchScheduleContactException {

		return getPersistence().findByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the last schedule contact in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching schedule contact, or <code>null</code> if a matching schedule contact could not be found
	 */
	public static ScheduleContact fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ScheduleContact> orderByComparator) {

		return getPersistence().fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);
	}

	/**
	 * Returns the schedule contacts before and after the current schedule contact in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param scheduleContactId the primary key of the current schedule contact
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next schedule contact
	 * @throws NoSuchScheduleContactException if a schedule contact with the primary key could not be found
	 */
	public static ScheduleContact[] findByUuid_C_PrevAndNext(
			long scheduleContactId, String uuid, long companyId,
			OrderByComparator<ScheduleContact> orderByComparator)
		throws it.smc.calendar.caldav.schedule.contact.exception.
			NoSuchScheduleContactException {

		return getPersistence().findByUuid_C_PrevAndNext(
			scheduleContactId, uuid, companyId, orderByComparator);
	}

	/**
	 * Removes all the schedule contacts where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the number of schedule contacts where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching schedule contacts
	 */
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	 * Returns the schedule contact where companyId = &#63; and emailAddress = &#63; or throws a <code>NoSuchScheduleContactException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param emailAddress the email address
	 * @return the matching schedule contact
	 * @throws NoSuchScheduleContactException if a matching schedule contact could not be found
	 */
	public static ScheduleContact findByC_E(long companyId, String emailAddress)
		throws it.smc.calendar.caldav.schedule.contact.exception.
			NoSuchScheduleContactException {

		return getPersistence().findByC_E(companyId, emailAddress);
	}

	/**
	 * Returns the schedule contact where companyId = &#63; and emailAddress = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param emailAddress the email address
	 * @return the matching schedule contact, or <code>null</code> if a matching schedule contact could not be found
	 */
	public static ScheduleContact fetchByC_E(
		long companyId, String emailAddress) {

		return getPersistence().fetchByC_E(companyId, emailAddress);
	}

	/**
	 * Returns the schedule contact where companyId = &#63; and emailAddress = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param emailAddress the email address
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching schedule contact, or <code>null</code> if a matching schedule contact could not be found
	 */
	public static ScheduleContact fetchByC_E(
		long companyId, String emailAddress, boolean useFinderCache) {

		return getPersistence().fetchByC_E(
			companyId, emailAddress, useFinderCache);
	}

	/**
	 * Removes the schedule contact where companyId = &#63; and emailAddress = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param emailAddress the email address
	 * @return the schedule contact that was removed
	 */
	public static ScheduleContact removeByC_E(
			long companyId, String emailAddress)
		throws it.smc.calendar.caldav.schedule.contact.exception.
			NoSuchScheduleContactException {

		return getPersistence().removeByC_E(companyId, emailAddress);
	}

	/**
	 * Returns the number of schedule contacts where companyId = &#63; and emailAddress = &#63;.
	 *
	 * @param companyId the company ID
	 * @param emailAddress the email address
	 * @return the number of matching schedule contacts
	 */
	public static int countByC_E(long companyId, String emailAddress) {
		return getPersistence().countByC_E(companyId, emailAddress);
	}

	/**
	 * Caches the schedule contact in the entity cache if it is enabled.
	 *
	 * @param scheduleContact the schedule contact
	 */
	public static void cacheResult(ScheduleContact scheduleContact) {
		getPersistence().cacheResult(scheduleContact);
	}

	/**
	 * Caches the schedule contacts in the entity cache if it is enabled.
	 *
	 * @param scheduleContacts the schedule contacts
	 */
	public static void cacheResult(List<ScheduleContact> scheduleContacts) {
		getPersistence().cacheResult(scheduleContacts);
	}

	/**
	 * Creates a new schedule contact with the primary key. Does not add the schedule contact to the database.
	 *
	 * @param scheduleContactId the primary key for the new schedule contact
	 * @return the new schedule contact
	 */
	public static ScheduleContact create(long scheduleContactId) {
		return getPersistence().create(scheduleContactId);
	}

	/**
	 * Removes the schedule contact with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param scheduleContactId the primary key of the schedule contact
	 * @return the schedule contact that was removed
	 * @throws NoSuchScheduleContactException if a schedule contact with the primary key could not be found
	 */
	public static ScheduleContact remove(long scheduleContactId)
		throws it.smc.calendar.caldav.schedule.contact.exception.
			NoSuchScheduleContactException {

		return getPersistence().remove(scheduleContactId);
	}

	public static ScheduleContact updateImpl(ScheduleContact scheduleContact) {
		return getPersistence().updateImpl(scheduleContact);
	}

	/**
	 * Returns the schedule contact with the primary key or throws a <code>NoSuchScheduleContactException</code> if it could not be found.
	 *
	 * @param scheduleContactId the primary key of the schedule contact
	 * @return the schedule contact
	 * @throws NoSuchScheduleContactException if a schedule contact with the primary key could not be found
	 */
	public static ScheduleContact findByPrimaryKey(long scheduleContactId)
		throws it.smc.calendar.caldav.schedule.contact.exception.
			NoSuchScheduleContactException {

		return getPersistence().findByPrimaryKey(scheduleContactId);
	}

	/**
	 * Returns the schedule contact with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param scheduleContactId the primary key of the schedule contact
	 * @return the schedule contact, or <code>null</code> if a schedule contact with the primary key could not be found
	 */
	public static ScheduleContact fetchByPrimaryKey(long scheduleContactId) {
		return getPersistence().fetchByPrimaryKey(scheduleContactId);
	}

	/**
	 * Returns all the schedule contacts.
	 *
	 * @return the schedule contacts
	 */
	public static List<ScheduleContact> findAll() {
		return getPersistence().findAll();
	}

	/**
	 * Returns a range of all the schedule contacts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ScheduleContactModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of schedule contacts
	 * @param end the upper bound of the range of schedule contacts (not inclusive)
	 * @return the range of schedule contacts
	 */
	public static List<ScheduleContact> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	 * Returns an ordered range of all the schedule contacts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ScheduleContactModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of schedule contacts
	 * @param end the upper bound of the range of schedule contacts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of schedule contacts
	 */
	public static List<ScheduleContact> findAll(
		int start, int end,
		OrderByComparator<ScheduleContact> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the schedule contacts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ScheduleContactModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of schedule contacts
	 * @param end the upper bound of the range of schedule contacts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of schedule contacts
	 */
	public static List<ScheduleContact> findAll(
		int start, int end,
		OrderByComparator<ScheduleContact> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the schedule contacts from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of schedule contacts.
	 *
	 * @return the number of schedule contacts
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ScheduleContactPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<ScheduleContactPersistence, ScheduleContactPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			ScheduleContactPersistence.class);

		ServiceTracker<ScheduleContactPersistence, ScheduleContactPersistence>
			serviceTracker =
				new ServiceTracker
					<ScheduleContactPersistence, ScheduleContactPersistence>(
						bundle.getBundleContext(),
						ScheduleContactPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}