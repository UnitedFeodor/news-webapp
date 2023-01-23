<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="/WEB-INF/pages/tiles/localization/localizationBase.jsp" %>
<%--
<fmt:setLocale value="sessionScope.local"/>
<fmt:setBundle basename="localization" var="loc1"/>

<fmt:message bundle="${loc1}" key="header.name" var="header_name"/>
<fmt:message bundle="${loc1}" key="button.en" var="button_en"/>
<fmt:message bundle="${loc1}" key="button.ru" var="button_ru"/>
--%>
<div class="wrapper">
	<div class="newstitle"> ${header_name} </div> <!-- News management not yet wroking-->


	<div class="local-link">

		<div align="right">
			<!-- <%--
			<form class="form-inline" style ='display:inline-block;' action="controller" method="get">
				<input type="hidden" name="command" value="do_change_language" />
				<input type="hidden" name="local" value="en" />
				<input type="submit" name="en" value="${header_en}" />

			</form>
			<form class="form-inline" style ='display:inline-block;' action="controller" method="get">
				<input type="hidden" name="command" value="do_change_language" />
				<input type="hidden" name="local" value="ru" />
				<input type="submit" name="ru" value="${header_ru}" />

			</form> --%> -->
			<a href="controller?command=do_change_language&local=en"> ${header_en} </a> &nbsp;&nbsp;
			<a href="controller?command=do_change_language&local=ru"> ${header_ru} </a> <br /> <br />
		</div>

		<c:if test="${not (sessionScope.userActivity eq 'active')}">

			<div align="right">
				<form action="controller" method="post">
					<input type="hidden" name="command" value="do_sign_in"/>
					${header_logination_login} <input type="text" name="login" value="" /><br />
					${header_logination_password} <input type="password" name="password" value="" /><br />

					<c:if test="${not (sessionScope.auth_error eq null)}">
						<font color="red"> 
							<c:out value="${auth_error_message}" />
							<c:remove var="auth_error"/>
						</font> 
					</c:if>

					<c:if test="${not (sessionScope.register_success eq null)}">
						<font color="green">
							<c:out value="${register_success_message}" />
							<c:remove var="register_success"/>
						</font>
					</c:if>

					<c:if test="${not (sessionScope.register_error eq null)}">
						<font color="red">
							<c:out value="${register_error_message}" />
							<c:remove var="register_error"/>
						</font>
					</c:if>

					<a href="controller?command=go_to_registration_page">${header_registration_link}</a> <input type="submit" value="${header_signin}" /><br />
				</form>
			</div>

		</c:if>
		
		<c:if test="${sessionScope.userActivity eq 'active'}">

			<div align="right">
				<form action="controller" method="post">
					<input type="hidden" name="command" value="do_sign_out" /> 
					<input type="submit" value="${header_signout}" /><br />
				</form>

				<c:if test="${not (sessionScope.save_success eq null)}">
					<font color="green">
						<c:out value="${save_success_message}" />
						<c:remove var="save_success"/>
					</font>
				</c:if>
				<c:if test="${not (sessionScope.delete_success eq null)}">
					<font color="green">
						<c:out value="${delete_success_message}" />
						<c:remove var="delete_success"/>
					</font>
				</c:if>

			</div>



		</c:if>
	</div>

</div>
