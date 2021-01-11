create table CalDAVSync_ScheduleContact (
	uuid_ VARCHAR(75) null,
	scheduleContactId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null,
	commonName VARCHAR(75) null,
	emailAddress VARCHAR(75) null
);