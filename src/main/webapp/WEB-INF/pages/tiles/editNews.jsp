<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="body-title">
	<a href="controller?command=go_to_news_list">News >> </a> Edit News
</div>

<div class="add-table-margin">
    <form action="controller" method="post">
        <table class="news_text_format">
            <tr>
                <td class="space_around_title_text">News Title</td>

                <td class="space_around_view_text"><div class="word-breaker">
                    <input type="text" name="news_title" value="<c:out value="${requestScope.news.title }"/>"/>

                </div></td>
            </tr>
            <tr>
                <td class="space_around_title_text">News Date</td>

                <td class="space_around_view_text"><div class="word-breaker">
                         <input type="text" name="news_date" value="<c:out value="${requestScope.news.newsDate }"/>"/>

                    </div></td>
            </tr>
            <tr>
                <td class="space_around_title_text">Brief</td>
                <td class="space_around_view_text"><div class="word-breaker">
                    <textarea rows="7" cols="30" name="news_brief" style="resize: none;"><c:out value="${requestScope.news.briefNews }" /></textarea>

                </div></td>
            </tr>
            <tr>
                <td class="space_around_title_text">Content</td>
                <td class="space_around_view_text"><div class="word-breaker">
                    <textarea rows="11" cols="30" name="news_content" style="resize: none;"><c:out value="${requestScope.news.content }" /></textarea>

                    </div></td>
            </tr>
        </table>
</div>


        <c:if test="${sessionScope.role eq 'admin'}">
        <div class="first-view-button">
            <!-- form -->
                <input type="hidden" name="command" value="do_edit_news" /> <input
                    type="hidden" name="id" value="${news.idNews}" /> <input
                    type="submit" value="Save" />

        </div>
    </form>
    <div class="second-view-button">
        <form>
            <input type="hidden" name="command" value="go_to_news_list" /> <input
                type="hidden" name="id" value="${news.idNews}" /> <input
                type="submit" value="Cancel" />
        </form>

    </div>
</c:if>