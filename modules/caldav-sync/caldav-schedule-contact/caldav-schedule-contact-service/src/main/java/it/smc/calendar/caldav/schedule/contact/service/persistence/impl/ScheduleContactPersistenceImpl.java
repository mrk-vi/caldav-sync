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

package it.smc.calendar.caldav.schedule.contact.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import it.smc.calendar.caldav.schedule.contact.exception.NoSuchScheduleContactException;
import it.smc.calendar.caldav.schedule.contact.model.ScheduleContact;
import it.smc.calendar.caldav.schedule.contact.model.impl.ScheduleContactImpl;
import it.smc.calendar.caldav.schedule.contact.model.impl.ScheduleContactModelImpl;
import it.smc.calendar.caldav.schedule.contact.service.persistence.ScheduleContactPersistence;
import it.smc.calendar.caldav.schedule.contact.service.persistence.impl.constants.CalDAVSyncPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the schedule contact service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = ScheduleContactPersistence.class)
public class ScheduleContactPersistenceImpl
	extends BasePersistenceImpl<ScheduleContact>
	implements ScheduleContactPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ScheduleContactUtil</code> to access the schedule contact persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ScheduleContactImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;

	/**
	 * Returns all the schedule contacts where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching schedule contacts
	 */
	@Override
	public List<ScheduleContact> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<ScheduleContact> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
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
	@Override
	public List<ScheduleContact> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ScheduleContact> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
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
	@Override
	public List<ScheduleContact> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ScheduleContact> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<ScheduleContact> list = null;

		if (useFinderCache) {
			list = (List<ScheduleContact>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ScheduleContact scheduleContact : list) {
					if (!uuid.equals(scheduleContact.getUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SCHEDULECONTACT_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(ScheduleContactModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				list = (List<ScheduleContact>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first schedule contact in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching schedule contact
	 * @throws NoSuchScheduleContactException if a matching schedule contact could not be found
	 */
	@Override
	public ScheduleContact findByUuid_First(
			String uuid, OrderByComparator<ScheduleContact> orderByComparator)
		throws NoSuchScheduleContactException {

		ScheduleContact scheduleContact = fetchByUuid_First(
			uuid, orderByComparator);

		if (scheduleContact != null) {
			return scheduleContact;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchScheduleContactException(msg.toString());
	}

	/**
	 * Returns the first schedule contact in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching schedule contact, or <code>null</code> if a matching schedule contact could not be found
	 */
	@Override
	public ScheduleContact fetchByUuid_First(
		String uuid, OrderByComparator<ScheduleContact> orderByComparator) {

		List<ScheduleContact> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last schedule contact in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching schedule contact
	 * @throws NoSuchScheduleContactException if a matching schedule contact could not be found
	 */
	@Override
	public ScheduleContact findByUuid_Last(
			String uuid, OrderByComparator<ScheduleContact> orderByComparator)
		throws NoSuchScheduleContactException {

		ScheduleContact scheduleContact = fetchByUuid_Last(
			uuid, orderByComparator);

		if (scheduleContact != null) {
			return scheduleContact;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchScheduleContactException(msg.toString());
	}

	/**
	 * Returns the last schedule contact in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching schedule contact, or <code>null</code> if a matching schedule contact could not be found
	 */
	@Override
	public ScheduleContact fetchByUuid_Last(
		String uuid, OrderByComparator<ScheduleContact> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<ScheduleContact> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public ScheduleContact[] findByUuid_PrevAndNext(
			long scheduleContactId, String uuid,
			OrderByComparator<ScheduleContact> orderByComparator)
		throws NoSuchScheduleContactException {

		uuid = Objects.toString(uuid, "");

		ScheduleContact scheduleContact = findByPrimaryKey(scheduleContactId);

		Session session = null;

		try {
			session = openSession();

			ScheduleContact[] array = new ScheduleContactImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, scheduleContact, uuid, orderByComparator, true);

			array[1] = scheduleContact;

			array[2] = getByUuid_PrevAndNext(
				session, scheduleContact, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ScheduleContact getByUuid_PrevAndNext(
		Session session, ScheduleContact scheduleContact, String uuid,
		OrderByComparator<ScheduleContact> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SCHEDULECONTACT_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(ScheduleContactModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						scheduleContact)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<ScheduleContact> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the schedule contacts where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (ScheduleContact scheduleContact :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(scheduleContact);
		}
	}

	/**
	 * Returns the number of schedule contacts where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching schedule contacts
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SCHEDULECONTACT_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"scheduleContact.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(scheduleContact.uuid IS NULL OR scheduleContact.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the schedule contact where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchScheduleContactException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching schedule contact
	 * @throws NoSuchScheduleContactException if a matching schedule contact could not be found
	 */
	@Override
	public ScheduleContact findByUUID_G(String uuid, long groupId)
		throws NoSuchScheduleContactException {

		ScheduleContact scheduleContact = fetchByUUID_G(uuid, groupId);

		if (scheduleContact == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchScheduleContactException(msg.toString());
		}

		return scheduleContact;
	}

	/**
	 * Returns the schedule contact where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching schedule contact, or <code>null</code> if a matching schedule contact could not be found
	 */
	@Override
	public ScheduleContact fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the schedule contact where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching schedule contact, or <code>null</code> if a matching schedule contact could not be found
	 */
	@Override
	public ScheduleContact fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs, this);
		}

		if (result instanceof ScheduleContact) {
			ScheduleContact scheduleContact = (ScheduleContact)result;

			if (!Objects.equals(uuid, scheduleContact.getUuid()) ||
				(groupId != scheduleContact.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_SCHEDULECONTACT_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<ScheduleContact> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					ScheduleContact scheduleContact = list.get(0);

					result = scheduleContact;

					cacheResult(scheduleContact);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByUUID_G, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (ScheduleContact)result;
		}
	}

	/**
	 * Removes the schedule contact where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the schedule contact that was removed
	 */
	@Override
	public ScheduleContact removeByUUID_G(String uuid, long groupId)
		throws NoSuchScheduleContactException {

		ScheduleContact scheduleContact = findByUUID_G(uuid, groupId);

		return remove(scheduleContact);
	}

	/**
	 * Returns the number of schedule contacts where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching schedule contacts
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SCHEDULECONTACT_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 =
		"scheduleContact.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(scheduleContact.uuid IS NULL OR scheduleContact.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"scheduleContact.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the schedule contacts where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching schedule contacts
	 */
	@Override
	public List<ScheduleContact> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<ScheduleContact> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
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
	@Override
	public List<ScheduleContact> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ScheduleContact> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
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
	@Override
	public List<ScheduleContact> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ScheduleContact> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<ScheduleContact> list = null;

		if (useFinderCache) {
			list = (List<ScheduleContact>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ScheduleContact scheduleContact : list) {
					if (!uuid.equals(scheduleContact.getUuid()) ||
						(companyId != scheduleContact.getCompanyId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_SCHEDULECONTACT_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(ScheduleContactModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				list = (List<ScheduleContact>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
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
	@Override
	public ScheduleContact findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ScheduleContact> orderByComparator)
		throws NoSuchScheduleContactException {

		ScheduleContact scheduleContact = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (scheduleContact != null) {
			return scheduleContact;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchScheduleContactException(msg.toString());
	}

	/**
	 * Returns the first schedule contact in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching schedule contact, or <code>null</code> if a matching schedule contact could not be found
	 */
	@Override
	public ScheduleContact fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ScheduleContact> orderByComparator) {

		List<ScheduleContact> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public ScheduleContact findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ScheduleContact> orderByComparator)
		throws NoSuchScheduleContactException {

		ScheduleContact scheduleContact = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (scheduleContact != null) {
			return scheduleContact;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchScheduleContactException(msg.toString());
	}

	/**
	 * Returns the last schedule contact in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching schedule contact, or <code>null</code> if a matching schedule contact could not be found
	 */
	@Override
	public ScheduleContact fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ScheduleContact> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<ScheduleContact> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public ScheduleContact[] findByUuid_C_PrevAndNext(
			long scheduleContactId, String uuid, long companyId,
			OrderByComparator<ScheduleContact> orderByComparator)
		throws NoSuchScheduleContactException {

		uuid = Objects.toString(uuid, "");

		ScheduleContact scheduleContact = findByPrimaryKey(scheduleContactId);

		Session session = null;

		try {
			session = openSession();

			ScheduleContact[] array = new ScheduleContactImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, scheduleContact, uuid, companyId, orderByComparator,
				true);

			array[1] = scheduleContact;

			array[2] = getByUuid_C_PrevAndNext(
				session, scheduleContact, uuid, companyId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ScheduleContact getByUuid_C_PrevAndNext(
		Session session, ScheduleContact scheduleContact, String uuid,
		long companyId, OrderByComparator<ScheduleContact> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_SCHEDULECONTACT_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(ScheduleContactModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						scheduleContact)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<ScheduleContact> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the schedule contacts where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (ScheduleContact scheduleContact :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(scheduleContact);
		}
	}

	/**
	 * Returns the number of schedule contacts where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching schedule contacts
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SCHEDULECONTACT_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"scheduleContact.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(scheduleContact.uuid IS NULL OR scheduleContact.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"scheduleContact.companyId = ?";

	private FinderPath _finderPathFetchByC_E;
	private FinderPath _finderPathCountByC_E;

	/**
	 * Returns the schedule contact where companyId = &#63; and emailAddress = &#63; or throws a <code>NoSuchScheduleContactException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param emailAddress the email address
	 * @return the matching schedule contact
	 * @throws NoSuchScheduleContactException if a matching schedule contact could not be found
	 */
	@Override
	public ScheduleContact findByC_E(long companyId, String emailAddress)
		throws NoSuchScheduleContactException {

		ScheduleContact scheduleContact = fetchByC_E(companyId, emailAddress);

		if (scheduleContact == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", emailAddress=");
			msg.append(emailAddress);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchScheduleContactException(msg.toString());
		}

		return scheduleContact;
	}

	/**
	 * Returns the schedule contact where companyId = &#63; and emailAddress = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param emailAddress the email address
	 * @return the matching schedule contact, or <code>null</code> if a matching schedule contact could not be found
	 */
	@Override
	public ScheduleContact fetchByC_E(long companyId, String emailAddress) {
		return fetchByC_E(companyId, emailAddress, true);
	}

	/**
	 * Returns the schedule contact where companyId = &#63; and emailAddress = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param emailAddress the email address
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching schedule contact, or <code>null</code> if a matching schedule contact could not be found
	 */
	@Override
	public ScheduleContact fetchByC_E(
		long companyId, String emailAddress, boolean useFinderCache) {

		emailAddress = Objects.toString(emailAddress, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, emailAddress};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_E, finderArgs, this);
		}

		if (result instanceof ScheduleContact) {
			ScheduleContact scheduleContact = (ScheduleContact)result;

			if ((companyId != scheduleContact.getCompanyId()) ||
				!Objects.equals(
					emailAddress, scheduleContact.getEmailAddress())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_SCHEDULECONTACT_WHERE);

			query.append(_FINDER_COLUMN_C_E_COMPANYID_2);

			boolean bindEmailAddress = false;

			if (emailAddress.isEmpty()) {
				query.append(_FINDER_COLUMN_C_E_EMAILADDRESS_3);
			}
			else {
				bindEmailAddress = true;

				query.append(_FINDER_COLUMN_C_E_EMAILADDRESS_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindEmailAddress) {
					qPos.add(StringUtil.toLowerCase(emailAddress));
				}

				List<ScheduleContact> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_E, finderArgs, list);
					}
				}
				else {
					ScheduleContact scheduleContact = list.get(0);

					result = scheduleContact;

					cacheResult(scheduleContact);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(_finderPathFetchByC_E, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (ScheduleContact)result;
		}
	}

	/**
	 * Removes the schedule contact where companyId = &#63; and emailAddress = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param emailAddress the email address
	 * @return the schedule contact that was removed
	 */
	@Override
	public ScheduleContact removeByC_E(long companyId, String emailAddress)
		throws NoSuchScheduleContactException {

		ScheduleContact scheduleContact = findByC_E(companyId, emailAddress);

		return remove(scheduleContact);
	}

	/**
	 * Returns the number of schedule contacts where companyId = &#63; and emailAddress = &#63;.
	 *
	 * @param companyId the company ID
	 * @param emailAddress the email address
	 * @return the number of matching schedule contacts
	 */
	@Override
	public int countByC_E(long companyId, String emailAddress) {
		emailAddress = Objects.toString(emailAddress, "");

		FinderPath finderPath = _finderPathCountByC_E;

		Object[] finderArgs = new Object[] {companyId, emailAddress};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SCHEDULECONTACT_WHERE);

			query.append(_FINDER_COLUMN_C_E_COMPANYID_2);

			boolean bindEmailAddress = false;

			if (emailAddress.isEmpty()) {
				query.append(_FINDER_COLUMN_C_E_EMAILADDRESS_3);
			}
			else {
				bindEmailAddress = true;

				query.append(_FINDER_COLUMN_C_E_EMAILADDRESS_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindEmailAddress) {
					qPos.add(StringUtil.toLowerCase(emailAddress));
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_E_COMPANYID_2 =
		"scheduleContact.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_E_EMAILADDRESS_2 =
		"lower(scheduleContact.emailAddress) = ?";

	private static final String _FINDER_COLUMN_C_E_EMAILADDRESS_3 =
		"(scheduleContact.emailAddress IS NULL OR scheduleContact.emailAddress = '')";

	public ScheduleContactPersistenceImpl() {
		setModelClass(ScheduleContact.class);

		setModelImplClass(ScheduleContactImpl.class);
		setModelPKClass(long.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the schedule contact in the entity cache if it is enabled.
	 *
	 * @param scheduleContact the schedule contact
	 */
	@Override
	public void cacheResult(ScheduleContact scheduleContact) {
		entityCache.putResult(
			entityCacheEnabled, ScheduleContactImpl.class,
			scheduleContact.getPrimaryKey(), scheduleContact);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				scheduleContact.getUuid(), scheduleContact.getGroupId()
			},
			scheduleContact);

		finderCache.putResult(
			_finderPathFetchByC_E,
			new Object[] {
				scheduleContact.getCompanyId(),
				scheduleContact.getEmailAddress()
			},
			scheduleContact);

		scheduleContact.resetOriginalValues();
	}

	/**
	 * Caches the schedule contacts in the entity cache if it is enabled.
	 *
	 * @param scheduleContacts the schedule contacts
	 */
	@Override
	public void cacheResult(List<ScheduleContact> scheduleContacts) {
		for (ScheduleContact scheduleContact : scheduleContacts) {
			if (entityCache.getResult(
					entityCacheEnabled, ScheduleContactImpl.class,
					scheduleContact.getPrimaryKey()) == null) {

				cacheResult(scheduleContact);
			}
			else {
				scheduleContact.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all schedule contacts.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ScheduleContactImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the schedule contact.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ScheduleContact scheduleContact) {
		entityCache.removeResult(
			entityCacheEnabled, ScheduleContactImpl.class,
			scheduleContact.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(ScheduleContactModelImpl)scheduleContact, true);
	}

	@Override
	public void clearCache(List<ScheduleContact> scheduleContacts) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ScheduleContact scheduleContact : scheduleContacts) {
			entityCache.removeResult(
				entityCacheEnabled, ScheduleContactImpl.class,
				scheduleContact.getPrimaryKey());

			clearUniqueFindersCache(
				(ScheduleContactModelImpl)scheduleContact, true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				entityCacheEnabled, ScheduleContactImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		ScheduleContactModelImpl scheduleContactModelImpl) {

		Object[] args = new Object[] {
			scheduleContactModelImpl.getUuid(),
			scheduleContactModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, scheduleContactModelImpl, false);

		args = new Object[] {
			scheduleContactModelImpl.getCompanyId(),
			scheduleContactModelImpl.getEmailAddress()
		};

		finderCache.putResult(
			_finderPathCountByC_E, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByC_E, args, scheduleContactModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		ScheduleContactModelImpl scheduleContactModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				scheduleContactModelImpl.getUuid(),
				scheduleContactModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((scheduleContactModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				scheduleContactModelImpl.getOriginalUuid(),
				scheduleContactModelImpl.getOriginalGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				scheduleContactModelImpl.getCompanyId(),
				scheduleContactModelImpl.getEmailAddress()
			};

			finderCache.removeResult(_finderPathCountByC_E, args);
			finderCache.removeResult(_finderPathFetchByC_E, args);
		}

		if ((scheduleContactModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_E.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				scheduleContactModelImpl.getOriginalCompanyId(),
				scheduleContactModelImpl.getOriginalEmailAddress()
			};

			finderCache.removeResult(_finderPathCountByC_E, args);
			finderCache.removeResult(_finderPathFetchByC_E, args);
		}
	}

	/**
	 * Creates a new schedule contact with the primary key. Does not add the schedule contact to the database.
	 *
	 * @param scheduleContactId the primary key for the new schedule contact
	 * @return the new schedule contact
	 */
	@Override
	public ScheduleContact create(long scheduleContactId) {
		ScheduleContact scheduleContact = new ScheduleContactImpl();

		scheduleContact.setNew(true);
		scheduleContact.setPrimaryKey(scheduleContactId);

		String uuid = PortalUUIDUtil.generate();

		scheduleContact.setUuid(uuid);

		scheduleContact.setCompanyId(CompanyThreadLocal.getCompanyId());

		return scheduleContact;
	}

	/**
	 * Removes the schedule contact with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param scheduleContactId the primary key of the schedule contact
	 * @return the schedule contact that was removed
	 * @throws NoSuchScheduleContactException if a schedule contact with the primary key could not be found
	 */
	@Override
	public ScheduleContact remove(long scheduleContactId)
		throws NoSuchScheduleContactException {

		return remove((Serializable)scheduleContactId);
	}

	/**
	 * Removes the schedule contact with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the schedule contact
	 * @return the schedule contact that was removed
	 * @throws NoSuchScheduleContactException if a schedule contact with the primary key could not be found
	 */
	@Override
	public ScheduleContact remove(Serializable primaryKey)
		throws NoSuchScheduleContactException {

		Session session = null;

		try {
			session = openSession();

			ScheduleContact scheduleContact = (ScheduleContact)session.get(
				ScheduleContactImpl.class, primaryKey);

			if (scheduleContact == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchScheduleContactException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(scheduleContact);
		}
		catch (NoSuchScheduleContactException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected ScheduleContact removeImpl(ScheduleContact scheduleContact) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(scheduleContact)) {
				scheduleContact = (ScheduleContact)session.get(
					ScheduleContactImpl.class,
					scheduleContact.getPrimaryKeyObj());
			}

			if (scheduleContact != null) {
				session.delete(scheduleContact);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (scheduleContact != null) {
			clearCache(scheduleContact);
		}

		return scheduleContact;
	}

	@Override
	public ScheduleContact updateImpl(ScheduleContact scheduleContact) {
		boolean isNew = scheduleContact.isNew();

		if (!(scheduleContact instanceof ScheduleContactModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(scheduleContact.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					scheduleContact);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in scheduleContact proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ScheduleContact implementation " +
					scheduleContact.getClass());
		}

		ScheduleContactModelImpl scheduleContactModelImpl =
			(ScheduleContactModelImpl)scheduleContact;

		if (Validator.isNull(scheduleContact.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			scheduleContact.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (scheduleContact.getCreateDate() == null)) {
			if (serviceContext == null) {
				scheduleContact.setCreateDate(now);
			}
			else {
				scheduleContact.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!scheduleContactModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				scheduleContact.setModifiedDate(now);
			}
			else {
				scheduleContact.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (scheduleContact.isNew()) {
				session.save(scheduleContact);

				scheduleContact.setNew(false);
			}
			else {
				scheduleContact = (ScheduleContact)session.merge(
					scheduleContact);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!_columnBitmaskEnabled) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {scheduleContactModelImpl.getUuid()};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				scheduleContactModelImpl.getUuid(),
				scheduleContactModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((scheduleContactModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					scheduleContactModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {scheduleContactModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((scheduleContactModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					scheduleContactModelImpl.getOriginalUuid(),
					scheduleContactModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					scheduleContactModelImpl.getUuid(),
					scheduleContactModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, ScheduleContactImpl.class,
			scheduleContact.getPrimaryKey(), scheduleContact, false);

		clearUniqueFindersCache(scheduleContactModelImpl, false);
		cacheUniqueFindersCache(scheduleContactModelImpl);

		scheduleContact.resetOriginalValues();

		return scheduleContact;
	}

	/**
	 * Returns the schedule contact with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the schedule contact
	 * @return the schedule contact
	 * @throws NoSuchScheduleContactException if a schedule contact with the primary key could not be found
	 */
	@Override
	public ScheduleContact findByPrimaryKey(Serializable primaryKey)
		throws NoSuchScheduleContactException {

		ScheduleContact scheduleContact = fetchByPrimaryKey(primaryKey);

		if (scheduleContact == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchScheduleContactException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return scheduleContact;
	}

	/**
	 * Returns the schedule contact with the primary key or throws a <code>NoSuchScheduleContactException</code> if it could not be found.
	 *
	 * @param scheduleContactId the primary key of the schedule contact
	 * @return the schedule contact
	 * @throws NoSuchScheduleContactException if a schedule contact with the primary key could not be found
	 */
	@Override
	public ScheduleContact findByPrimaryKey(long scheduleContactId)
		throws NoSuchScheduleContactException {

		return findByPrimaryKey((Serializable)scheduleContactId);
	}

	/**
	 * Returns the schedule contact with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param scheduleContactId the primary key of the schedule contact
	 * @return the schedule contact, or <code>null</code> if a schedule contact with the primary key could not be found
	 */
	@Override
	public ScheduleContact fetchByPrimaryKey(long scheduleContactId) {
		return fetchByPrimaryKey((Serializable)scheduleContactId);
	}

	/**
	 * Returns all the schedule contacts.
	 *
	 * @return the schedule contacts
	 */
	@Override
	public List<ScheduleContact> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<ScheduleContact> findAll(int start, int end) {
		return findAll(start, end, null);
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
	@Override
	public List<ScheduleContact> findAll(
		int start, int end,
		OrderByComparator<ScheduleContact> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
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
	@Override
	public List<ScheduleContact> findAll(
		int start, int end,
		OrderByComparator<ScheduleContact> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<ScheduleContact> list = null;

		if (useFinderCache) {
			list = (List<ScheduleContact>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_SCHEDULECONTACT);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SCHEDULECONTACT;

				sql = sql.concat(ScheduleContactModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<ScheduleContact>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the schedule contacts from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ScheduleContact scheduleContact : findAll()) {
			remove(scheduleContact);
		}
	}

	/**
	 * Returns the number of schedule contacts.
	 *
	 * @return the number of schedule contacts
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SCHEDULECONTACT);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "scheduleContactId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_SCHEDULECONTACT;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ScheduleContactModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the schedule contact persistence.
	 */
	@Activate
	public void activate() {
		ScheduleContactModelImpl.setEntityCacheEnabled(entityCacheEnabled);
		ScheduleContactModelImpl.setFinderCacheEnabled(finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, ScheduleContactImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, ScheduleContactImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, ScheduleContactImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, ScheduleContactImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			ScheduleContactModelImpl.UUID_COLUMN_BITMASK |
			ScheduleContactModelImpl.COMMONNAME_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, ScheduleContactImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			ScheduleContactModelImpl.UUID_COLUMN_BITMASK |
			ScheduleContactModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, ScheduleContactImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, ScheduleContactImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			ScheduleContactModelImpl.UUID_COLUMN_BITMASK |
			ScheduleContactModelImpl.COMPANYID_COLUMN_BITMASK |
			ScheduleContactModelImpl.COMMONNAME_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathFetchByC_E = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, ScheduleContactImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_E",
			new String[] {Long.class.getName(), String.class.getName()},
			ScheduleContactModelImpl.COMPANYID_COLUMN_BITMASK |
			ScheduleContactModelImpl.EMAILADDRESS_COLUMN_BITMASK);

		_finderPathCountByC_E = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_E",
			new String[] {Long.class.getName(), String.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(ScheduleContactImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = CalDAVSyncPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.it.smc.calendar.caldav.schedule.contact.model.ScheduleContact"),
			true);
	}

	@Override
	@Reference(
		target = CalDAVSyncPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = CalDAVSyncPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	private boolean _columnBitmaskEnabled;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_SCHEDULECONTACT =
		"SELECT scheduleContact FROM ScheduleContact scheduleContact";

	private static final String _SQL_SELECT_SCHEDULECONTACT_WHERE =
		"SELECT scheduleContact FROM ScheduleContact scheduleContact WHERE ";

	private static final String _SQL_COUNT_SCHEDULECONTACT =
		"SELECT COUNT(scheduleContact) FROM ScheduleContact scheduleContact";

	private static final String _SQL_COUNT_SCHEDULECONTACT_WHERE =
		"SELECT COUNT(scheduleContact) FROM ScheduleContact scheduleContact WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "scheduleContact.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ScheduleContact exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ScheduleContact exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ScheduleContactPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	static {
		try {
			Class.forName(CalDAVSyncPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}