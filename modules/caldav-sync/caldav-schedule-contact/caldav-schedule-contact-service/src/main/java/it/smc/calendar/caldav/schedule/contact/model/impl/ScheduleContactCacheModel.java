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

package it.smc.calendar.caldav.schedule.contact.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import it.smc.calendar.caldav.schedule.contact.model.ScheduleContact;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing ScheduleContact in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ScheduleContactCacheModel
	implements CacheModel<ScheduleContact>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ScheduleContactCacheModel)) {
			return false;
		}

		ScheduleContactCacheModel scheduleContactCacheModel =
			(ScheduleContactCacheModel)obj;

		if (scheduleContactId == scheduleContactCacheModel.scheduleContactId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, scheduleContactId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", scheduleContactId=");
		sb.append(scheduleContactId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", commonName=");
		sb.append(commonName);
		sb.append(", emailAddress=");
		sb.append(emailAddress);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ScheduleContact toEntityModel() {
		ScheduleContactImpl scheduleContactImpl = new ScheduleContactImpl();

		if (uuid == null) {
			scheduleContactImpl.setUuid("");
		}
		else {
			scheduleContactImpl.setUuid(uuid);
		}

		scheduleContactImpl.setScheduleContactId(scheduleContactId);
		scheduleContactImpl.setGroupId(groupId);
		scheduleContactImpl.setCompanyId(companyId);
		scheduleContactImpl.setUserId(userId);

		if (userName == null) {
			scheduleContactImpl.setUserName("");
		}
		else {
			scheduleContactImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			scheduleContactImpl.setCreateDate(null);
		}
		else {
			scheduleContactImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			scheduleContactImpl.setModifiedDate(null);
		}
		else {
			scheduleContactImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (commonName == null) {
			scheduleContactImpl.setCommonName("");
		}
		else {
			scheduleContactImpl.setCommonName(commonName);
		}

		if (emailAddress == null) {
			scheduleContactImpl.setEmailAddress("");
		}
		else {
			scheduleContactImpl.setEmailAddress(emailAddress);
		}

		scheduleContactImpl.resetOriginalValues();

		return scheduleContactImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		scheduleContactId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		commonName = objectInput.readUTF();
		emailAddress = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(scheduleContactId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (commonName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(commonName);
		}

		if (emailAddress == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(emailAddress);
		}
	}

	public String uuid;
	public long scheduleContactId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String commonName;
	public String emailAddress;

}