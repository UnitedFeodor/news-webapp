<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/pages/tiles/localization/localizationBase.jsp" %>

<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<div class="body-title">
	<a href="">${goback_news} </a> ${newslist_goback_current}
</div>

<form action="controller" method="post">
	<c:forEach var="news" items="${requestScope.news}">
		<div class="single-news-wrapper">
			<div class="single-news-header-wrapper">
				<div class="news-title">
					<c:out value="${news.title}" />
				</div>
				<div class="news-date">
					<tags:localDate date="${news.newsDate}"/>
					<%-- <c:out value="${news.newsDate}" /> --%>
				</div>

				<div class="news-content">
					<c:out value="${news.brief}" />
				</div>
				<div class="news-link-to-wrapper">
					<div class="link-position">
						<c:if test="${sessionScope.role eq 'admin'}">
						      <a href="controller?command=go_to_edit_news&idNews=${news.idNews}">${edit} </a>
						</c:if>
						<span>&nbsp&nbsp</span>
						<a href="controller?command=go_to_view_news&idNews=${news.idNews}">${newslist_view} </a>
   					    
   					    <c:if test="${sessionScope.role eq 'admin'}">
   					         <input type="checkbox" name="idNews" value="${news.idNews }" />
   					    </c:if>
					</div>
				</div>

			</div>
		</div>

	</c:forEach>

    <c:if test="${(sessionScope.role eq 'admin') and (not (requestScope.news eq null))}">
        <div class="delete-button-position">

            <input type="hidden" name="command" value="do_delete_news" />
            <input type="submit" value="${delete}" />

        </div>
    </c:if>


	<%--
	 <!-- <logic:notEmpty name="newsForm" property="newsList">
		<div class="delete-button-position">
			<html:submit>
				<bean:message key="locale.newslink.deletebutton" />
			</html:submit>
		</div>
	</logic:notEmpty>
    -->--%>

	<div class="no-news">
		<c:if test="${requestScope.news eq null}">
        ${nonews}
	</c:if>
	</div>
</form>

<form name="amountForm" action="controller" method="get" style="margin: 20px; font-size: larger; min-font-size: 14">
	<input type="hidden" name="command" value="go_to_news_list" />
	<input type="hidden" name="page" value="${requestScope.page}">
	<div class="news-amount">
		<select name="count" id="count" onchange="javascript:document.amountForm.submit();">

			<option value="5" ${sessionScope.count.equals("5") ? 'selected' : ''}>5</option>
			<option value="10" ${sessionScope.count.equals("10") ? 'selected' : ''}>10</option>
			<option value="25" ${sessionScope.count.equals("25") ? 'selected' : ''}>25</option>
		</select>
	</div>
</form>
<div class="pagination">
	<c:if test="${not (requestScope.page eq 1) }">
		<a href="controller?command=go_to_news_list&page=${requestScope.page-1}"> &laquo;</a>
	</c:if>

	<c:if test="${not (requestScope.page eq 1) }" >
		<a href="controller?command=go_to_news_list&page=1" > 1 </a>
	</c:if>

	<a id="selected-page"> ${requestScope.page} </a>

	<c:if test="${not (requestScope.page eq requestScope.final_page_number)}">
		<a href="controller?command=go_to_news_list&page=${requestScope.final_page_number}"> ${requestScope.final_page_number}</a>
	</c:if>


	<c:if test="${not (requestScope.page eq requestScope.final_page_number) }">
		<a href="controller?command=go_to_news_list&page=${requestScope.page+1}"> &raquo;</a>
	</c:if>

</div>