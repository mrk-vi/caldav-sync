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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ScheduleContactSoap implements Serializable {

	public static ScheduleContactSoap toSoapModel(ScheduleContact model) {
		ScheduleContactSoap soapModel = new ScheduleContactSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setScheduleContactId(model.getScheduleContactId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCommonName(model.getCommonName());
		soapModel.setEmailAddress(model.getEmailAddress());

		return soapModel;
	}

	public static ScheduleContactSoap[] toSoapModels(ScheduleContact[] models) {
		ScheduleContactSoap[] soapModels =
			new ScheduleContactSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ScheduleContactSoap[][] toSoapModels(
		ScheduleContact[][] models) {

		ScheduleContactSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new ScheduleContactSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ScheduleContactSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ScheduleContactSoap[] toSoapModels(
		List<ScheduleContact> models) {

		List<ScheduleContactSoap> soapModels =
			new ArrayList<ScheduleContactSoap>(models.size());

		for (ScheduleContact model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ScheduleContactSoap[soapModels.size()]);
	}

	public ScheduleContactSoap() {
	}

	public long getPrimaryKey() {
		return _scheduleContactId;
	}

	public void setPrimaryKey(long pk) {
		setScheduleContactId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getScheduleContactId() {
		return _scheduleContactId;
	}

	public void setScheduleContactId(long scheduleContactId) {
		_scheduleContactId = scheduleContactId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public String getCommonName() {
		return _commonName;
	}

	public void setCommonName(String commonName) {
		_commonName = commonName;
	}

	public String getEmailAddress() {
		return _emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		_emailAddress = emailAddress;
	}

	private String _uuid;
	private long _scheduleContactId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _commonName;
	private String _emailAddress;

}