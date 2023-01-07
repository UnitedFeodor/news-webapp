<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h1>An error has occured. Sorry.</h1>


<div class="body-title">
    <h2>
        <c:out value="${requestScope.error_msg }" />
    </h2>
	<a href="controller?command=go_to_news_list">Back to News </a>
</div>