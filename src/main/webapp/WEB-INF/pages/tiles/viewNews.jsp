<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/pages/tiles/localization/localizationBase.jsp" %>

<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<div class="body-title">
	<a href="controller?command=go_to_news_list">${goback_news}</a> ${viewnews_goback_current}
</div>

<div class="add-table-margin">
	<table class="news_text_format">
		<tr>
			<td class="space_around_title_text">${title}</td>

			<td class="space_around_view_text"><div class="word-breaker">
					<c:out value="${requestScope.news.title }" />
				</div></td>
		</tr>
		<tr>
			<td class="space_around_title_text">${date}</td>

			<td class="space_around_view_text"><div class="word-breaker">
				<tags:localDate date="${news.newsDate}"/>
				</div></td>
		</tr>
		<tr>
			<td class="space_around_title_text">${brief}</td>
			<td class="space_around_view_text"><div class="word-breaker">
					<c:out value="${requestScope.news.brief }" />
				</div></td>
		</tr>
		<tr>
			<td class="space_around_title_text">${content}</td>
			<td class="space_around_view_text"><div class="word-breaker">
					<c:out value="${requestScope.news.content }" />
				</div></td>
		</tr>
	</table>
</div>


<c:if test="${sessionScope.role eq 'admin'}">
<div class="first-view-button">
	<form action="controller" method="post">
		<input type="hidden" name="command" value="go_to_edit_news" /> <input
			type="hidden" name="idNews" value="${news.idNews}" /> <input
			type="submit" value="${edit}" />
	</form>
</div>

<div class="second-view-button">
	<form action="controller" method="post">
		<input type="hidden" name="command" value="do_delete_news" /> <input
			type="hidden" name="idNews" value="${news.idNews}" /> <input
			type="submit" value="${delete}" />
	</form>
</div>
</c:if>