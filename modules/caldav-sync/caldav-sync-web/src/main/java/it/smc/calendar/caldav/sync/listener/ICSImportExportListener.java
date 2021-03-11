/**
 * Copyright (c) 2013 SMC Treviso Srl. All rights reserved.
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

package it.smc.calendar.caldav.sync.listener;

import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.portal.kernel.sanitizer.SanitizerException;

/**
 * @author Fabio Pezzutto
 */
public interface ICSImportExportListener {

	public CalendarBooking afterContentExported(
			String ics, CalendarBooking calendarBooking)
		throws SanitizerException;

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #afterContentImported(String,
		CalendarBooking)}
	 */
	@Deprecated
	public void afterContentImported(String ics, Calendar calendar)
		throws SanitizerException;

	public CalendarBooking afterContentImported(String ics, CalendarBooking calendar)
		throws SanitizerException;

	public String beforeContentExported(
			String ics, CalendarBooking calendarBooking)
		throws SanitizerException;

	public String beforeContentImported(String ics, Calendar calendar)
		throws SanitizerException;
}