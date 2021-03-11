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

package it.smc.calendar.caldav.sync.util;

import com.liferay.calendar.exporter.CalendarDataFormat;
import com.liferay.calendar.model.Calendar;
import com.liferay.calendar.model.CalendarBooking;
import com.liferay.calendar.model.CalendarResource;
import com.liferay.calendar.service.CalendarBookingLocalServiceUtil;
import com.liferay.calendar.service.CalendarBookingServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webdav.Resource;
import com.liferay.portal.kernel.webdav.WebDAVRequest;
import com.liferay.portal.kernel.webdav.WebDAVUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Namespace;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;

import it.smc.calendar.caldav.helper.api.UserAgentHelperUtil;
import it.smc.calendar.caldav.util.CalendarUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Fabio Pezzutto
 */
public class CalDAVUtil {

	public static final Namespace NS_APPLE_URI = SAXReaderUtil.createNamespace(
		"A", "http://apple.com/ns/ical/");

	public static final Namespace NS_CALDAV_URI = SAXReaderUtil.createNamespace(
		"C", "urn:ietf:params:xml:ns:caldav");

	public static final Namespace NS_CALENDAR_SERVER_URI =
		SAXReaderUtil.createNamespace("CS", "http://calendarserver.org/ns/");

	public static String buildETag(String primaryKey, Date modifiedDate) {
		return primaryKey + StringPool.UNDERLINE + modifiedDate.getTime();
	}

	public static CalendarBooking getCalendarBookingFromCalendarAndURL(
		Calendar calendar, String URL) {

		String icsName = getICSNameFromURL(URL);

		if (icsName == null) {
			return null;
		}

		long calendarId = calendar.getCalendarId();

		CalendarBooking calendarBooking =
			CalendarBookingLocalServiceUtil.fetchCalendarBooking(
				calendarId, icsName);

		if (calendarBooking != null) {
			return calendarBooking;
		}

		long calendarBookingId = GetterUtil.getLong(icsName);

		calendarBooking = CalendarBookingLocalServiceUtil.fetchCalendarBooking(
			calendarBookingId);

		if ((calendarBooking != null) &&
			(calendarBooking.getCalendarId() == calendarId)) {

			return calendarBooking;
		}

		return null;
	}

	public static CalendarBooking getCalendarBookingFromURL(String URL)
		throws PortalException {

		String icsName = getICSNameFromURL(URL);

		long calendarBookingId = GetterUtil.getLong(icsName);

		return CalendarBookingServiceUtil.getCalendarBooking(calendarBookingId);
	}

	public static String getCalendarBookingURL(CalendarBooking calendarBooking)
		throws PortalException {

		StringBuilder sb = new StringBuilder(11);

		sb.append(PortalUtil.getPathContext());
		sb.append("/webdav/");
		sb.append(
			calendarBooking.getCalendarResource(
			).getCalendarResourceId());
		sb.append(StringPool.SLASH);
		sb.append(WebKeys.CALDAV_TOKEN);
		sb.append(StringPool.SLASH);
		sb.append(
			calendarBooking.getCalendar(
			).getCalendarId());
		sb.append(StringPool.SLASH);
		sb.append(calendarBooking.getCalendarBookingId());
		sb.append(StringPool.PERIOD);
		sb.append(CalendarDataFormat.ICAL.getValue());

		return sb.toString();
	}

	public static String getCalendarColor(Calendar calendar) {
		return StringPool.POUND.concat(
			String.format("%06X", 0xFFFFFF & calendar.getColor()));
	}

	public static String getCalendarResourceURL(
		CalendarResource calendarResource) {

		StringBuilder sb = new StringBuilder(6);

		sb.append(PortalUtil.getPathContext());
		sb.append("/webdav/");
		sb.append(calendarResource.getCalendarResourceId());
		sb.append(StringPool.SLASH);
		sb.append(WebKeys.CALDAV_TOKEN);
		sb.append(StringPool.SLASH);

		return sb.toString();
	}

	public static String getCalendarURL(Calendar calendar) {
		StringBuilder sb = new StringBuilder(3);

		String baseURL = StringPool.BLANK;

		try {
			baseURL = getCalendarResourceURL(calendar.getCalendarResource());
		}
		catch (Exception e) {
			_log.error(e);
		}

		sb.append(baseURL);
		sb.append(calendar.getCalendarId());
		sb.append(StringPool.SLASH);

		return sb.toString();
	}

	public static String getICSNameFromURL(String URL) {
		if (!URL.endsWith(
			StringPool.PERIOD + CalendarDataFormat.ICAL.getValue())) {

			return null;
		}

		String calendarBookingICSStr = StringUtil.extractLast(
			URL, StringPool.SLASH);

		calendarBookingICSStr = calendarBookingICSStr.substring(
			0 , calendarBookingICSStr.lastIndexOf(StringPool.PERIOD));

		String decoded =  calendarBookingICSStr;

		try {
			decoded = URLDecoder.decode(calendarBookingICSStr,
				StandardCharsets.UTF_8.displayName());

			while (!decoded.equals(calendarBookingICSStr)) {
				calendarBookingICSStr = decoded;
				decoded = URLDecoder.decode(calendarBookingICSStr,
					StandardCharsets.UTF_8.displayName());
			}
		}
		catch (UnsupportedEncodingException uee) {
			_log.warn("Can not decode the url: " + decoded);
		}

		return calendarBookingICSStr;
	}

	public static String getPrincipalURL(long userId) {
		StringBuilder sb = new StringBuilder(6);

		sb.append(PortalUtil.getPathContext());
		sb.append("/webdav/user/");
		sb.append(WebKeys.CALDAV_TOKEN);
		sb.append(StringPool.SLASH);
		sb.append(userId);
		sb.append(StringPool.SLASH);

		return sb.toString();
	}

	public static Element getReportDateFilter() throws InvalidRequestException {
		try {
			Document document = CalDAVRequestThreadLocal.getRequestDocument();

			String xPathExpression = "//*[local-name()='time-range']";

			XPath xPathSelector = SAXReaderUtil.createXPath(xPathExpression);

			Node node = xPathSelector.selectSingleNode(document);

			if (node == null) {
				return null;
			}

			Element timeRangeElement = (Element)node.asXPathResult(
				node.getParent());

			return timeRangeElement;
		}
		catch (Exception e) {
			throw new InvalidRequestException(e);
		}
	}

	public static Set<QName> getRequestDAVProps(WebDAVRequest webDAVRequest)
		throws InvalidRequestException {

		try {
			Set<QName> props = new HashSet<>();

			Resource resource = webDAVRequest.getWebDAVStorage(
			).getResource(
				webDAVRequest
			);

			Document document = CalDAVRequestThreadLocal.getRequestDocument();

			if (document == null) {
				if (resource.isCollection()) {
					return CalDAVProps.getAllCollectionProps();
				}

				return CalDAVProps.getAllResourceProps();
			}

			Element rootElement = document.getRootElement();

			if (rootElement.element("allprop") != null) {
				if (resource.isCollection()) {
					return CalDAVProps.getAllCollectionProps();
				}

				return CalDAVProps.getAllResourceProps();
			}

			Element propElement = rootElement.element("prop");

			List<Element> elements = propElement.elements();

			for (Element element : elements) {
				String prefix = element.getNamespacePrefix();
				String uri = element.getNamespaceURI();

				Namespace namespace = WebDAVUtil.createNamespace(prefix, uri);

				props.add(
					SAXReaderUtil.createQName(element.getName(), namespace));
			}

			return props;
		}
		catch (Exception e) {
			throw new InvalidRequestException(e);
		}
	}

	public static String getResourceETag(Resource resource) {
		StagedModel model = (StagedModel)resource.getModel();

		Date modifiedDate = model.getModifiedDate();

		if (model instanceof Calendar) {
			Calendar calendar = (Calendar)model;

			try {
				modifiedDate = CalendarUtil.getLastCalendarModifiedDate(
					calendar.getCalendarId());
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		return buildETag(
			String.valueOf(resource.getPrimaryKey()), modifiedDate);
	}

	public static long getUserId(WebDAVRequest webDAVRequest) {
		long userId = webDAVRequest.getUserId();

		if (userId == 0) {
			userId = PrincipalThreadLocal.getUserId();
		}

		if (userId == 0) {
			userId = PermissionThreadLocal.getPermissionChecker(
			).getUserId();
		}

		return userId;
	}

	public static boolean isAndroid(WebDAVRequest webDAVRequest) {
		return _isUserAgent(webDAVRequest, UserAgentHelperUtil::isAndroid);
	}

	public static boolean isAndroidCalDAVSyncAdapter(
		WebDAVRequest webDAVRequest) {

		return _isUserAgent(
			webDAVRequest, UserAgentHelperUtil::isAndroidCalDAVSyncAdapter);
	}

	public static boolean isCalendarBookingRequest(
		WebDAVRequest webDAVRequest) {

		if (webDAVRequest.getPathArray().length <= 3) {
			return false;
		}

		String path = webDAVRequest.getPath();

		return path.endsWith(
			StringPool.PERIOD + CalendarDataFormat.ICAL.getValue());
	}

	public static boolean isCalendarRequest(WebDAVRequest webDAVRequest) {
		if (!isPrincipalRequest(webDAVRequest) &&
			(webDAVRequest.getPathArray().length > 2)) {

			return true;
		}

		return false;
	}

	public static boolean isICal(WebDAVRequest webDAVRequest) {
		return _isUserAgent(webDAVRequest, UserAgentHelperUtil::isICal);
	}

	public static boolean isIOS(WebDAVRequest webDAVRequest) {
		return _isUserAgent(webDAVRequest, UserAgentHelperUtil::isIOS);
	}

	public static boolean isMacOSX(WebDAVRequest webDAVRequest) {
		return _isUserAgent(webDAVRequest, UserAgentHelperUtil::isMacOSX);
	}

	public static boolean isOpenSync(WebDAVRequest webDAVRequest) {
		return _isUserAgent(webDAVRequest, UserAgentHelperUtil::isOpenSync);
	}

	public static boolean isPrincipalRequest(WebDAVRequest webDAVRequest) {
		String[] path = webDAVRequest.getPathArray();

		if ((path.length == 3) && path[0].equals("user")) {
			return true;
		}

		return false;
	}

	public static boolean isRequestContentXML(HttpServletRequest request) {
		String method = StringUtil.toUpperCase(request.getMethod());

		if (method.equals("PROPFIND") || method.equals("PROPPATCH") ||
			method.equals("REPORT")) {

			return true;
		}

		return false;
	}

	public static boolean isThunderbird(HttpServletRequest request) {
		return _isUserAgent(request, UserAgentHelperUtil::isThunderbird);
	}

	public static boolean isThunderbird(WebDAVRequest webDAVRequest) {
		return _isUserAgent(webDAVRequest, UserAgentHelperUtil::isThunderbird);
	}

	private static String _getUserAgent(HttpServletRequest request) {
		String userAgent = request.getHeader(HttpHeaders.USER_AGENT);

		if (_log.isDebugEnabled()) {
			_log.debug("webDAV userAgent:" + userAgent);
		}

		return userAgent;
	}

	private static String _getUserAgent(WebDAVRequest webDAVRequest) {
		return _getUserAgent(webDAVRequest.getHttpServletRequest());
	}

	private static boolean _isUserAgent(
		HttpServletRequest request, Predicate<String> predicate) {

		String userAgent = _getUserAgent(request);

		if (Validator.isNull(userAgent)) {
			return false;
		}

		return predicate.test(userAgent);
	}

	private static boolean _isUserAgent(
		WebDAVRequest request, Predicate<String> predicate) {

		String userAgent = _getUserAgent(request);

		if (Validator.isNull(userAgent)) {
			return false;
		}

		return predicate.test(userAgent);
	}

	private static Log _log = LogFactoryUtil.getLog(CalDAVUtil.class);

}
