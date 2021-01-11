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

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import it.smc.calendar.caldav.schedule.contact.exception.NoSuchScheduleContactException;
import it.smc.calendar.caldav.schedule.contact.model.ScheduleContact;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the schedule contact service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ScheduleContactUtil
 * @generated
 */
@ProviderType
public interface ScheduleContactPersistence
	extends BasePersistence<ScheduleContact> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ScheduleContactUtil} to access the schedule contact persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the schedule contacts where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching schedule contacts
	 */
	public java.util.List<ScheduleContact> findByUuid(String uuid);

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
	public java.util.List<ScheduleContact> findByUuid(
		String uuid, int start, int end);

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
	public java.util.List<ScheduleContact> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ScheduleContact>
			orderByComparator);

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
	public java.util.List<ScheduleContact> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ScheduleContact>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first schedule contact in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching schedule contact
	 * @throws NoSuchScheduleContactException if a matching schedule contact could not be found
	 */
	public ScheduleContact findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ScheduleContact>
				orderByComparator)
		throws NoSuchScheduleContactException;

	/**
	 * Returns the first schedule contact in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching schedule contact, or <code>null</code> if a matching schedule contact could not be found
	 */
	public ScheduleContact fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ScheduleContact>
			orderByComparator);

	/**
	 * Returns the last schedule contact in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching schedule contact
	 * @throws NoSuchScheduleContactException if a matching schedule contact could not be found
	 */
	public ScheduleContact findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ScheduleContact>
				orderByComparator)
		throws NoSuchScheduleContactException;

	/**
	 * Returns the last schedule contact in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching schedule contact, or <code>null</code> if a matching schedule contact could not be found
	 */
	public ScheduleContact fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<ScheduleContact>
			orderByComparator);

	/**
	 * Returns the schedule contacts before and after the current schedule contact in the ordered set where uuid = &#63;.
	 *
	 * @param scheduleContactId the primary key of the current schedule contact
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next schedule contact
	 * @throws NoSuchScheduleContactException if a schedule contact with the primary key could not be found
	 */
	public ScheduleContact[] findByUuid_PrevAndNext(
			long scheduleContactId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<ScheduleContact>
				orderByComparator)
		throws NoSuchScheduleContactException;

	/**
	 * Removes all the schedule contacts where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of schedule contacts where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching schedule contacts
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the schedule contact where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchScheduleContactException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching schedule contact
	 * @throws NoSuchScheduleContactException if a matching schedule contact could not be found
	 */
	public ScheduleContact findByUUID_G(String uuid, long groupId)
		throws NoSuchScheduleContactException;

	/**
	 * Returns the schedule contact where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching schedule contact, or <code>null</code> if a matching schedule contact could not be found
	 */
	public ScheduleContact fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the schedule contact where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching schedule contact, or <code>null</code> if a matching schedule contact could not be found
	 */
	public ScheduleContact fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the schedule contact where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the schedule contact that was removed
	 */
	public ScheduleContact removeByUUID_G(String uuid, long groupId)
		throws NoSuchScheduleContactException;

	/**
	 * Returns the number of schedule contacts where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching schedule contacts
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the schedule contacts where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching schedule contacts
	 */
	public java.util.List<ScheduleContact> findByUuid_C(
		String uuid, long companyId);

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
	public java.util.List<ScheduleContact> findByUuid_C(
		String uuid, long companyId, int start, int end);

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
	public java.util.List<ScheduleContact> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ScheduleContact>
			orderByComparator);

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
	public java.util.List<ScheduleContact> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ScheduleContact>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first schedule contact in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching schedule contact
	 * @throws NoSuchScheduleContactException if a matching schedule contact could not be found
	 */
	public ScheduleContact findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ScheduleContact>
				orderByComparator)
		throws NoSuchScheduleContactException;

	/**
	 * Returns the first schedule contact in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching schedule contact, or <code>null</code> if a matching schedule contact could not be found
	 */
	public ScheduleContact fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ScheduleContact>
			orderByComparator);

	/**
	 * Returns the last schedule contact in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching schedule contact
	 * @throws NoSuchScheduleContactException if a matching schedule contact could not be found
	 */
	public ScheduleContact findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ScheduleContact>
				orderByComparator)
		throws NoSuchScheduleContactException;

	/**
	 * Returns the last schedule contact in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching schedule contact, or <code>null</code> if a matching schedule contact could not be found
	 */
	public ScheduleContact fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<ScheduleContact>
			orderByComparator);

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
	public ScheduleContact[] findByUuid_C_PrevAndNext(
			long scheduleContactId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<ScheduleContact>
				orderByComparator)
		throws NoSuchScheduleContactException;

	/**
	 * Removes all the schedule contacts where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of schedule contacts where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching schedule contacts
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns the schedule contact where companyId = &#63; and emailAddress = &#63; or throws a <code>NoSuchScheduleContactException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param emailAddress the email address
	 * @return the matching schedule contact
	 * @throws NoSuchScheduleContactException if a matching schedule contact could not be found
	 */
	public ScheduleContact findByC_E(long companyId, String emailAddress)
		throws NoSuchScheduleContactException;

	/**
	 * Returns the schedule contact where companyId = &#63; and emailAddress = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param emailAddress the email address
	 * @return the matching schedule contact, or <code>null</code> if a matching schedule contact could not be found
	 */
	public ScheduleContact fetchByC_E(long companyId, String emailAddress);

	/**
	 * Returns the schedule contact where companyId = &#63; and emailAddress = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param emailAddress the email address
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching schedule contact, or <code>null</code> if a matching schedule contact could not be found
	 */
	public ScheduleContact fetchByC_E(
		long companyId, String emailAddress, boolean useFinderCache);

	/**
	 * Removes the schedule contact where companyId = &#63; and emailAddress = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param emailAddress the email address
	 * @return the schedule contact that was removed
	 */
	public ScheduleContact removeByC_E(long companyId, String emailAddress)
		throws NoSuchScheduleContactException;

	/**
	 * Returns the number of schedule contacts where companyId = &#63; and emailAddress = &#63;.
	 *
	 * @param companyId the company ID
	 * @param emailAddress the email address
	 * @return the number of matching schedule contacts
	 */
	public int countByC_E(long companyId, String emailAddress);

	/**
	 * Caches the schedule contact in the entity cache if it is enabled.
	 *
	 * @param scheduleContact the schedule contact
	 */
	public void cacheResult(ScheduleContact scheduleContact);

	/**
	 * Caches the schedule contacts in the entity cache if it is enabled.
	 *
	 * @param scheduleContacts the schedule contacts
	 */
	public void cacheResult(java.util.List<ScheduleContact> scheduleContacts);

	/**
	 * Creates a new schedule contact with the primary key. Does not add the schedule contact to the database.
	 *
	 * @param scheduleContactId the primary key for the new schedule contact
	 * @return the new schedule contact
	 */
	public ScheduleContact create(long scheduleContactId);

	/**
	 * Removes the schedule contact with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param scheduleContactId the primary key of the schedule contact
	 * @return the schedule contact that was removed
	 * @throws NoSuchScheduleContactException if a schedule contact with the primary key could not be found
	 */
	public ScheduleContact remove(long scheduleContactId)
		throws NoSuchScheduleContactException;

	public ScheduleContact updateImpl(ScheduleContact scheduleContact);

	/**
	 * Returns the schedule contact with the primary key or throws a <code>NoSuchScheduleContactException</code> if it could not be found.
	 *
	 * @param scheduleContactId the primary key of the schedule contact
	 * @return the schedule contact
	 * @throws NoSuchScheduleContactException if a schedule contact with the primary key could not be found
	 */
	public ScheduleContact findByPrimaryKey(long scheduleContactId)
		throws NoSuchScheduleContactException;

	/**
	 * Returns the schedule contact with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param scheduleContactId the primary key of the schedule contact
	 * @return the schedule contact, or <code>null</code> if a schedule contact with the primary key could not be found
	 */
	public ScheduleContact fetchByPrimaryKey(long scheduleContactId);

	/**
	 * Returns all the schedule contacts.
	 *
	 * @return the schedule contacts
	 */
	public java.util.List<ScheduleContact> findAll();

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
	public java.util.List<ScheduleContact> findAll(int start, int end);

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
	public java.util.List<ScheduleContact> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ScheduleContact>
			orderByComparator);

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
	public java.util.List<ScheduleContact> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ScheduleContact>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the schedule contacts from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of schedule contacts.
	 *
	 * @return the number of schedule contacts
	 */
	public int countAll();

}