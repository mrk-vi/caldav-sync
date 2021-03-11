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

package it.smc.calendar.caldav.schedule.contact.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ScheduleContactLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ScheduleContactLocalService
 * @generated
 */
public class ScheduleContactLocalServiceWrapper
	implements ScheduleContactLocalService,
			   ServiceWrapper<ScheduleContactLocalService> {

	public ScheduleContactLocalServiceWrapper(
		ScheduleContactLocalService scheduleContactLocalService) {

		_scheduleContactLocalService = scheduleContactLocalService;
	}

	@Override
	public it.smc.calendar.caldav.schedule.contact.model.ScheduleContact
			addScheduleContact(
				long companyId, String commonName, String emailAddress,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _scheduleContactLocalService.addScheduleContact(
			companyId, commonName, emailAddress, serviceContext);
	}

	/**
	 * Adds the schedule contact to the database. Also notifies the appropriate model listeners.
	 *
	 * @param scheduleContact the schedule contact
	 * @return the schedule contact that was added
	 */
	@Override
	public it.smc.calendar.caldav.schedule.contact.model.ScheduleContact
		addScheduleContact(
			it.smc.calendar.caldav.schedule.contact.model.ScheduleContact
				scheduleContact) {

		return _scheduleContactLocalService.addScheduleContact(scheduleContact);
	}

	/**
	 * Creates a new schedule contact with the primary key. Does not add the schedule contact to the database.
	 *
	 * @param scheduleContactId the primary key for the new schedule contact
	 * @return the new schedule contact
	 */
	@Override
	public it.smc.calendar.caldav.schedule.contact.model.ScheduleContact
		createScheduleContact(long scheduleContactId) {

		return _scheduleContactLocalService.createScheduleContact(
			scheduleContactId);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _scheduleContactLocalService.deletePersistedModel(
			persistedModel);
	}

	/**
	 * Deletes the schedule contact with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param scheduleContactId the primary key of the schedule contact
	 * @return the schedule contact that was removed
	 * @throws PortalException if a schedule contact with the primary key could not be found
	 */
	@Override
	public it.smc.calendar.caldav.schedule.contact.model.ScheduleContact
			deleteScheduleContact(long scheduleContactId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _scheduleContactLocalService.deleteScheduleContact(
			scheduleContactId);
	}

	/**
	 * Deletes the schedule contact from the database. Also notifies the appropriate model listeners.
	 *
	 * @param scheduleContact the schedule contact
	 * @return the schedule contact that was removed
	 */
	@Override
	public it.smc.calendar.caldav.schedule.contact.model.ScheduleContact
		deleteScheduleContact(
			it.smc.calendar.caldav.schedule.contact.model.ScheduleContact
				scheduleContact) {

		return _scheduleContactLocalService.deleteScheduleContact(
			scheduleContact);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _scheduleContactLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _scheduleContactLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>it.smc.calendar.caldav.schedule.contact.model.impl.ScheduleContactModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _scheduleContactLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>it.smc.calendar.caldav.schedule.contact.model.impl.ScheduleContactModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _scheduleContactLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _scheduleContactLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _scheduleContactLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	@Override
	public com.liferay.calendar.model.Calendar fetchDefaultCalendar(
		long companyId, String emailAddress) {

		return _scheduleContactLocalService.fetchDefaultCalendar(
			companyId, emailAddress);
	}

	@Override
	public it.smc.calendar.caldav.schedule.contact.model.ScheduleContact
		fetchScheduleContact(long scheduleContactId) {

		return _scheduleContactLocalService.fetchScheduleContact(
			scheduleContactId);
	}

	@Override
	public it.smc.calendar.caldav.schedule.contact.model.ScheduleContact
		fetchScheduleContact(long companyId, String emailAddress) {

		return _scheduleContactLocalService.fetchScheduleContact(
			companyId, emailAddress);
	}

	/**
	 * Returns the schedule contact matching the UUID and group.
	 *
	 * @param uuid the schedule contact's UUID
	 * @param groupId the primary key of the group
	 * @return the matching schedule contact, or <code>null</code> if a matching schedule contact could not be found
	 */
	@Override
	public it.smc.calendar.caldav.schedule.contact.model.ScheduleContact
		fetchScheduleContactByUuidAndGroupId(String uuid, long groupId) {

		return _scheduleContactLocalService.
			fetchScheduleContactByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public it.smc.calendar.caldav.schedule.contact.model.ScheduleContact
			findScheduleContact(long companyId, String emailAddress)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _scheduleContactLocalService.findScheduleContact(
			companyId, emailAddress);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _scheduleContactLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.calendar.model.Calendar getDefaultCalendar(
			it.smc.calendar.caldav.schedule.contact.model.ScheduleContact
				scheduleContact,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _scheduleContactLocalService.getDefaultCalendar(
			scheduleContact, serviceContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery
		getExportActionableDynamicQuery(
			com.liferay.exportimport.kernel.lar.PortletDataContext
				portletDataContext) {

		return _scheduleContactLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _scheduleContactLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _scheduleContactLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _scheduleContactLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the schedule contact with the primary key.
	 *
	 * @param scheduleContactId the primary key of the schedule contact
	 * @return the schedule contact
	 * @throws PortalException if a schedule contact with the primary key could not be found
	 */
	@Override
	public it.smc.calendar.caldav.schedule.contact.model.ScheduleContact
			getScheduleContact(long scheduleContactId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _scheduleContactLocalService.getScheduleContact(
			scheduleContactId);
	}

	/**
	 * Returns the schedule contact matching the UUID and group.
	 *
	 * @param uuid the schedule contact's UUID
	 * @param groupId the primary key of the group
	 * @return the matching schedule contact
	 * @throws PortalException if a matching schedule contact could not be found
	 */
	@Override
	public it.smc.calendar.caldav.schedule.contact.model.ScheduleContact
			getScheduleContactByUuidAndGroupId(String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _scheduleContactLocalService.getScheduleContactByUuidAndGroupId(
			uuid, groupId);
	}

	/**
	 * Returns a range of all the schedule contacts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>it.smc.calendar.caldav.schedule.contact.model.impl.ScheduleContactModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of schedule contacts
	 * @param end the upper bound of the range of schedule contacts (not inclusive)
	 * @return the range of schedule contacts
	 */
	@Override
	public java.util.List
		<it.smc.calendar.caldav.schedule.contact.model.ScheduleContact>
			getScheduleContacts(int start, int end) {

		return _scheduleContactLocalService.getScheduleContacts(start, end);
	}

	/**
	 * Returns all the schedule contacts matching the UUID and company.
	 *
	 * @param uuid the UUID of the schedule contacts
	 * @param companyId the primary key of the company
	 * @return the matching schedule contacts, or an empty list if no matches were found
	 */
	@Override
	public java.util.List
		<it.smc.calendar.caldav.schedule.contact.model.ScheduleContact>
			getScheduleContactsByUuidAndCompanyId(String uuid, long companyId) {

		return _scheduleContactLocalService.
			getScheduleContactsByUuidAndCompanyId(uuid, companyId);
	}

	/**
	 * Returns a range of schedule contacts matching the UUID and company.
	 *
	 * @param uuid the UUID of the schedule contacts
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of schedule contacts
	 * @param end the upper bound of the range of schedule contacts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching schedule contacts, or an empty list if no matches were found
	 */
	@Override
	public java.util.List
		<it.smc.calendar.caldav.schedule.contact.model.ScheduleContact>
			getScheduleContactsByUuidAndCompanyId(
				String uuid, long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<it.smc.calendar.caldav.schedule.contact.model.
						ScheduleContact> orderByComparator) {

		return _scheduleContactLocalService.
			getScheduleContactsByUuidAndCompanyId(
				uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of schedule contacts.
	 *
	 * @return the number of schedule contacts
	 */
	@Override
	public int getScheduleContactsCount() {
		return _scheduleContactLocalService.getScheduleContactsCount();
	}

	/**
	 * Updates the schedule contact in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param scheduleContact the schedule contact
	 * @return the schedule contact that was updated
	 */
	@Override
	public it.smc.calendar.caldav.schedule.contact.model.ScheduleContact
		updateScheduleContact(
			it.smc.calendar.caldav.schedule.contact.model.ScheduleContact
				scheduleContact) {

		return _scheduleContactLocalService.updateScheduleContact(
			scheduleContact);
	}

	@Override
	public ScheduleContactLocalService getWrappedService() {
		return _scheduleContactLocalService;
	}

	@Override
	public void setWrappedService(
		ScheduleContactLocalService scheduleContactLocalService) {

		_scheduleContactLocalService = scheduleContactLocalService;
	}

	private ScheduleContactLocalService _scheduleContactLocalService;

}