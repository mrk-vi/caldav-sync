create unique index IX_B4286E5E on CalDAVSync_ScheduleContact (companyId, emailAddress[$COLUMN_LENGTH:75$]);
create index IX_A43B35AA on CalDAVSync_ScheduleContact (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_9B891EAC on CalDAVSync_ScheduleContact (uuid_[$COLUMN_LENGTH:75$], groupId);