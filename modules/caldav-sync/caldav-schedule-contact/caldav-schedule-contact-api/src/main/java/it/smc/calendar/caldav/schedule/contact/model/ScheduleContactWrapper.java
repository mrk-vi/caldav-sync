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

package it.smc.calendar.caldav.schedule.contact.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ScheduleContact}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ScheduleContact
 * @generated
 */
public class ScheduleContactWrapper
	extends BaseModelWrapper<ScheduleContact>
	implements ModelWrapper<ScheduleContact>, ScheduleContact {

	public ScheduleContactWrapper(ScheduleContact scheduleContact) {
		super(scheduleContact);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("scheduleContactId", getScheduleContactId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commonName", getCommonName());
		attributes.put("emailAddress", getEmailAddress());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long scheduleContactId = (Long)attributes.get("scheduleContactId");

		if (scheduleContactId != null) {
			setScheduleContactId(scheduleContactId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String commonName = (String)attributes.get("commonName");

		if (commonName != null) {
			setCommonName(commonName);
		}

		String emailAddress = (String)attributes.get("emailAddress");

		if (emailAddress != null) {
			setEmailAddress(emailAddress);
		}
	}

	/**
	 * Returns the common name of this schedule contact.
	 *
	 * @return the common name of this schedule contact
	 */
	@Override
	public String getCommonName() {
		return model.getCommonName();
	}

	/**
	 * Returns the company ID of this schedule contact.
	 *
	 * @return the company ID of this schedule contact
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this schedule contact.
	 *
	 * @return the create date of this schedule contact
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the email address of this schedule contact.
	 *
	 * @return the email address of this schedule contact
	 */
	@Override
	public String getEmailAddress() {
		return model.getEmailAddress();
	}

	/**
	 * Returns the group ID of this schedule contact.
	 *
	 * @return the group ID of this schedule contact
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this schedule contact.
	 *
	 * @return the modified date of this schedule contact
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the primary key of this schedule contact.
	 *
	 * @return the primary key of this schedule contact
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the schedule contact ID of this schedule contact.
	 *
	 * @return the schedule contact ID of this schedule contact
	 */
	@Override
	public long getScheduleContactId() {
		return model.getScheduleContactId();
	}

	/**
	 * Returns the user ID of this schedule contact.
	 *
	 * @return the user ID of this schedule contact
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this schedule contact.
	 *
	 * @return the user name of this schedule contact
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this schedule contact.
	 *
	 * @return the user uuid of this schedule contact
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this schedule contact.
	 *
	 * @return the uuid of this schedule contact
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the common name of this schedule contact.
	 *
	 * @param commonName the common name of this schedule contact
	 */
	@Override
	public void setCommonName(String commonName) {
		model.setCommonName(commonName);
	}

	/**
	 * Sets the company ID of this schedule contact.
	 *
	 * @param companyId the company ID of this schedule contact
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this schedule contact.
	 *
	 * @param createDate the create date of this schedule contact
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the email address of this schedule contact.
	 *
	 * @param emailAddress the email address of this schedule contact
	 */
	@Override
	public void setEmailAddress(String emailAddress) {
		model.setEmailAddress(emailAddress);
	}

	/**
	 * Sets the group ID of this schedule contact.
	 *
	 * @param groupId the group ID of this schedule contact
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this schedule contact.
	 *
	 * @param modifiedDate the modified date of this schedule contact
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the primary key of this schedule contact.
	 *
	 * @param primaryKey the primary key of this schedule contact
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the schedule contact ID of this schedule contact.
	 *
	 * @param scheduleContactId the schedule contact ID of this schedule contact
	 */
	@Override
	public void setScheduleContactId(long scheduleContactId) {
		model.setScheduleContactId(scheduleContactId);
	}

	/**
	 * Sets the user ID of this schedule contact.
	 *
	 * @param userId the user ID of this schedule contact
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this schedule contact.
	 *
	 * @param userName the user name of this schedule contact
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this schedule contact.
	 *
	 * @param userUuid the user uuid of this schedule contact
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this schedule contact.
	 *
	 * @param uuid the uuid of this schedule contact
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected ScheduleContactWrapper wrap(ScheduleContact scheduleContact) {
		return new ScheduleContactWrapper(scheduleContact);
	}

}