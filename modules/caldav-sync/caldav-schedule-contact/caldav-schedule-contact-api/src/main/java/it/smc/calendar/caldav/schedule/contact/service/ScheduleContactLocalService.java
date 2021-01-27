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

import com.liferay.calendar.model.Calendar;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import it.smc.calendar.caldav.schedule.contact.model.ScheduleContact;

import java.io.Serializable;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the local service interface for ScheduleContact. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see ScheduleContactLocalServiceUtil
 * @generated
 */
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface ScheduleContactLocalService
	extends BaseLocalService, PersistedModelLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ScheduleContactLocalServiceUtil} to access the schedule contact local service. Add custom service methods to <code>it.smc.calendar.caldav.schedule.contact.service.impl.ScheduleContactLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public ScheduleContact addScheduleContact(
			long companyId, String commonName, String emailAddress,
			ServiceContext serviceContext)
		throws PortalException;

	/**
	 * Adds the schedule contact to the database. Also notifies the appropriate model listeners.
	 *
	 * @param scheduleContact the schedule contact
	 * @return the schedule contact that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	public ScheduleContact addScheduleContact(ScheduleContact scheduleContact);

	/**
	 * Creates a new schedule contact with the primary key. Does not add the schedule contact to the database.
	 *
	 * @param scheduleContactId the primary key for the new schedule contact
	 * @return the new schedule contact
	 */
	@Transactional(enabled = false)
	public ScheduleContact createScheduleContact(long scheduleContactId);

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	/**
	 * Deletes the schedule contact with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param scheduleContactId the primary key of the schedule contact
	 * @return the schedule contact that was removed
	 * @throws PortalException if a schedule contact with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	public ScheduleContact deleteScheduleContact(long scheduleContactId)
		throws PortalException;

	/**
	 * Deletes the schedule contact from the database. Also notifies the appropriate model listeners.
	 *
	 * @param scheduleContact the schedule contact
	 * @return the schedule contact that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	public ScheduleContact deleteScheduleContact(
		ScheduleContact scheduleContact);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DynamicQuery dynamicQuery();

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end);

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator);

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Calendar fetchDefaultCalendar(long companyId, String emailAddress);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ScheduleContact fetchScheduleContact(long scheduleContactId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ScheduleContact fetchScheduleContact(
		long companyId, String emailAddress);

	/**
	 * Returns the schedule contact matching the UUID and group.
	 *
	 * @param uuid the schedule contact's UUID
	 * @param groupId the primary key of the group
	 * @return the matching schedule contact, or <code>null</code> if a matching schedule contact could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ScheduleContact fetchScheduleContactByUuidAndGroupId(
		String uuid, long groupId);

	public ScheduleContact findScheduleContact(
			long companyId, String emailAddress)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Calendar getDefaultCalendar(
			long companyId, String commonName, String emailAddress,
			ServiceContext serviceContext)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	/**
	 * @throws PortalException
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	/**
	 * Returns the schedule contact with the primary key.
	 *
	 * @param scheduleContactId the primary key of the schedule contact
	 * @return the schedule contact
	 * @throws PortalException if a schedule contact with the primary key could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ScheduleContact getScheduleContact(long scheduleContactId)
		throws PortalException;

	/**
	 * Returns the schedule contact matching the UUID and group.
	 *
	 * @param uuid the schedule contact's UUID
	 * @param groupId the primary key of the group
	 * @return the matching schedule contact
	 * @throws PortalException if a matching schedule contact could not be found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ScheduleContact getScheduleContactByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException;

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ScheduleContact> getScheduleContacts(int start, int end);

	/**
	 * Returns all the schedule contacts matching the UUID and company.
	 *
	 * @param uuid the UUID of the schedule contacts
	 * @param companyId the primary key of the company
	 * @return the matching schedule contacts, or an empty list if no matches were found
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ScheduleContact> getScheduleContactsByUuidAndCompanyId(
		String uuid, long companyId);

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ScheduleContact> getScheduleContactsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ScheduleContact> orderByComparator);

	/**
	 * Returns the number of schedule contacts.
	 *
	 * @return the number of schedule contacts
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getScheduleContactsCount();

	/**
	 * Updates the schedule contact in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param scheduleContact the schedule contact
	 * @return the schedule contact that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	public ScheduleContact updateScheduleContact(
		ScheduleContact scheduleContact);

}