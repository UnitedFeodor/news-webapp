<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<%-- <%@include jsp directive here for localization vars import in a different file --%>
<%@ include file="/WEB-INF/pages/tiles/localization/localizationBase.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="script/validation.js"></script>
<title> news management

</title>

<link rel="stylesheet" type="text/css" href="styles/newsStyle.css">


</head>
<body>
	<div class="page">
		<div class="header">
			<%--
			<c:url value = "/WEB-INF/pages/tiles/header.jsp" var = "headerURL">
				<c:param  name="header_name" value="${header_name}"/>
			</c:url>
			<c:import url = "${headerURL}"/> --%>
			<%--
			<c:out value="${loc}"/><br>
			<c:out value="${sessionScope.local}"/>

			<c:out value="${wrapper_welcome}" />
			--%>

			<c:import url="/WEB-INF/pages/tiles/header.jsp">

				<c:param  name="header_name" value="${header_name}"/>
			</c:import>
		</div>

		<div class="base-layout-wrapper">
			<div class="menu">

				<c:if test="${not (sessionScope.user eq 'active')}">
					<c:out value="${wrapper_welcome}"/>


					<%-- <c:import url=""></c:import> --%>
				</c:if>
				<c:if test="${sessionScope.user eq 'active'}">
					<c:import url="/WEB-INF/pages/tiles/menu.jsp" />
				</c:if>
		</div>

		<div class="content">

				<c:if test="${not (sessionScope.user eq 'active')}">
					<c:import url="/WEB-INF/pages/tiles/guestInfo.jsp" />
				</c:if>
				<c:if test="${sessionScope.user eq 'active'}">
					<c:import url="/WEB-INF/pages/tiles/body.jsp" />
				</c:if>


			</div>
		</div>

		<div class="footer">

			<c:import url="/WEB-INF/pages/tiles/footer.jsp" />
		</div>
	</div>
</body>
</html>