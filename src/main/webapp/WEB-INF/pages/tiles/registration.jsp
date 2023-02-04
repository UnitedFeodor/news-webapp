
<%@ include file="/WEB-INF/pages/tiles/localization/localizationBase.jsp" %>
<html>
<head>
    <title></title>
</head>
<body>
<div class="body-title">
    <a href="controller?command=go_to_news_list">${goback_news} </a> ${header_registration_link}
</div>
<div class="add-table-margin">
    <form action="controller" method="post">
            ${header_logination_login}
        <input  type="text" name="login"/> <br>
            ${header_logination_password}
        <input  type="text" name="password"/> <br>


            ${registration_name}
        <input class="space_around_title_text" type="text" name="name"/> <br>
            ${registration_surname}
        <input class="space_around_title_text" type="text" name="surname"/> <br>
            ${registration_birthday}
        <input class="space_around_title_text" type="text" name="birthday"/> <br>

        <input type="hidden" name="command" value="do_registration" />
        <input type="submit" value="${save}"/>
        <!--<a href="controller?command=do_change_language"> en </a> &nbsp;&nbsp; -->
    </form>
</div>
</body>
</html>
