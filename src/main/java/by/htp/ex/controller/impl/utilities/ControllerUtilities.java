package by.htp.ex.controller.impl.utilities;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import static by.htp.ex.bean.attributes.UserAttributes.USER_ROLE;

public class ControllerUtilities {

    public static boolean isRoleAdmin(HttpSession session) {
        //HttpSession session = request.getSession(false);
        if(session == null) {
            return false;
        }

        String role = (String) session.getAttribute(USER_ROLE);
        return role != null && role.equals("admin");
    }


}
