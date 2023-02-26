
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
        <table class="news_text_format">
            <tr>
                <td class="space_around_title_text2">${header_logination_login}</td>
                
                <td  class="space_around_view_text2">
                    <input  type="text" name="login"/> <br>
                </td>

            </tr>
            <tr>
                <td class="space_around_title_text2">
                    ${header_logination_password}
                </td>
                <td  class="space_around_view_text2">
                    <input  type="text" name="password"/> <br>
                </td>
            </tr>
            <tr>
                <td class="space_around_title_text2">
                    <label for="roles">${registration_role}</label>
                </td>
                <td class="space_around_view_text2">
                    <select name="roles" id="roles">
                        <option value="user">${registration_role_user}</option>
                        <option value="admin">${registration_role_admin}</option>
                    </select>
                </td>
            </tr>
            <br><br>

            <tr>
                <td class="space_around_title_text2">
                    ${registration_name}
                </td>
                <td class="space_around_view_text2">
                    <input  type="text" name="name"/> <br>
                </td>
            </tr>
            <tr>
                <td class="space_around_title_text2">
                 ${registration_surname}
                </td>
                <td class="space_around_view_text2">
                <input  type="text" name="surname"/> <br>
                </td>
            </tr>
            <tr>
                <td class="space_around_title_text2">
                    ${registration_birthday}
                </td>
                <td class="space_around_view_text2">
                    <input  type="text" name="birthday"/> <br>
                </td>
            </tr>

            <tr>
                <td class="space_around_title_text2">

                </td>
                <td class="space_around_view_text2" >
                    <input type="submit" value="${save}" style="margin-left: 70px"/>
                </td>
            </tr>
            <input type="hidden" name="command" value="do_registration" />

        </table>

        <!--<a href="controller?command=do_change_language"> en </a> &nbsp;&nbsp; -->
    </form>
</div>
</body>
</html>
