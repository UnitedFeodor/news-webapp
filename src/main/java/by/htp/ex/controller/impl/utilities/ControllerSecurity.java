package by.htp.ex.controller.impl.utilities;

import jakarta.servlet.http.HttpSession;

import static by.htp.ex.controller.constants.UserAttributes.USER_ROLE;

public class ControllerSecurity {

    public static boolean canExecuteThisRequest(HttpSession session) {
        //HttpSession session = request.getSession(false);

        // admin role check
        if(session == null) {
            return false;
        }

        String role = (String) session.getAttribute(USER_ROLE);
        return role != null && role.equals("admin");
    }


}
