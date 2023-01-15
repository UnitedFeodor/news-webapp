<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/pages/tiles/localization/localizationBase.jsp" %>
<h1>${error_message}</h1>


<div class="body-title">
    <h2>
        <c:out value="${sessionScope.error_msg }" />
        <c:remove var="error_msg"/>
    </h2>
	<a href="controller?command=go_to_news_list">${error_goback}</a>
</div>