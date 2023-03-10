<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/pages/tiles/localization/localizationBase.jsp" %>

<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<div class="body-title">
	<a href="controller?command=go_to_news_list">${goback_news} </a> ${addnews_goback_current}
</div>

<div class="add-table-margin">
    <form action="controller" method="post">
        <table class="news_text_format">
            <tr>
                <td class="space_around_title_text">${title}</td>

                <td class="space_around_view_text"><div class="word-breaker">
                    <input type="text" name="news_title" value="<c:out value="${requestScope.news.title }"/>"/>

                </div></td>
            </tr>
            <tr>
                <td class="space_around_title_text">${date}</td>

                <td class="space_around_view_text"><div class="word-breaker">
                         <input type="text" name="news_date" value="<tags:localDate date="${news.newsDate}"/>"/>

                    </div></td>
            </tr>
            <tr>
                <td class="space_around_title_text">${brief}</td>
                <td class="space_around_view_text"><div class="word-breaker">
                    <textarea rows="7" cols="30" name="news_brief" style="resize: none;"><c:out value="${requestScope.news.brief }" /></textarea>

                </div></td>
            </tr>
            <tr>
                <td class="space_around_title_text">${content}</td>
                <td class="space_around_view_text"><div class="word-breaker">
                    <textarea rows="11" cols="30" name="news_content" style="resize: none;"><c:out value="${requestScope.news.content }" /></textarea>

                    </div></td>
            </tr>
        </table>



        <%-- <c:if test="${sessionScope.role eq 'admin'}"> --%>
        <div style="margin-left: 120px; margin-bottom: 10px">
            <!-- form -->
            <input type="hidden" name="command" value="do_add_news" /> <input
               <%-- type="hidden" name="idNews" value="${news.idNews}" /> --%> <input
                type="submit" value="${save}" />
        </div>
    </form>

    <div style="margin-left: 120px" >
        <form action="controller">
            <input type="hidden" name="command" value="go_to_news_list" /> <input
             <%--   type="hidden" name="idNews" value="${news.idNews}" /> --%><input
                type="submit" value="${cancel}" />
        </form>
    </div>
</div>
<%-- </c:if> --%>
