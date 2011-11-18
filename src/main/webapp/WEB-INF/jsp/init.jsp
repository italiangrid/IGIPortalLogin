<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html" isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>


<%@ page import="portal.login.domain.Idp" %>




<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %> 
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %> 
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Calendar" %> 
<%@ page import="java.util.Collections" %> 
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %> 
<%@ page import="com.liferay.portal.kernel.util.CalendarFactoryUtil" %> 
<%@ page import="com.liferay.portal.kernel.dao.search.ResultRow" %> 
<%@ page import="com.liferay.portal.kernel.dao.search.SearchEntry" %> 
<%@ page import="com.liferay.portal.kernel.dao.search.ResultRow" %> 
<%@ page import="com.liferay.portal.kernel.exception.SystemException" %> 
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %> 
<%@ page import="com.liferay.portal.security.permission.ActionKeys" %> 
<%@ page import="com.liferay.portal.kernel.util.ListUtil" %> 
<%@ page import="com.liferay.portal.service.permission.PortalPermissionUtil" %>
<%@ page import="com.liferay.portal.service.permission.PortletPermissionUtil"%> 
<%@ page import="com.liferay.portal.model.User"%>
<%@ page import="javax.portlet.PortletURL" %>

<portlet:defineObjects />	
<liferay-theme:defineObjects />