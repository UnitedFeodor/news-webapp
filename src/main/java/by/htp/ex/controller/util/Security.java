package by.htp.ex.controller.util;

import by.htp.ex.controller.CommandName;
import by.htp.ex.constants.UserConstants;

import java.util.*;

import static by.htp.ex.controller.CommandName.*;

public class Security {

    // no stuff that ALL users can do
    private final static List<CommandName> adminRequests = List.of
            (DO_ADD_NEWS,DO_DELETE_NEWS,DO_EDIT_NEWS,DO_SIGN_OUT, DO_CHANGE_LANGUAGE,
                    GO_TO_ADD_NEWS,GO_TO_EDIT_NEWS,GO_TO_BASE_PAGE,GO_TO_VIEW_NEWS,
                    GO_TO_NEWS_LIST,GO_TO_ERROR_PAGE);
    private final static List<CommandName> userRequests = List.of
            (DO_SIGN_OUT,GO_TO_VIEW_NEWS,GO_TO_NEWS_LIST,GO_TO_BASE_PAGE, DO_CHANGE_LANGUAGE);

    private final static List<CommandName> guestRequests = List.of
            (DO_SIGN_IN,DO_REGISTRATION, DO_CHANGE_LANGUAGE, GO_TO_REGISTRATION_PAGE,
                GO_TO_VIEW_NEWS,GO_TO_NEWS_LIST,GO_TO_BASE_PAGE);

    private final static Map<String,List<CommandName>> rolePermissions = new HashMap<>();
    static {
        rolePermissions.put(UserConstants.ROLE_ADMIN,adminRequests);
        rolePermissions.put(UserConstants.ROLE_USER,userRequests);
        rolePermissions.put(UserConstants.ROLE_GUEST,guestRequests);
    }
    public static boolean canExecuteThisRequest(String role, CommandName action) {
        if(role == null) {
            role = UserConstants.ROLE_GUEST;
        }
        return rolePermissions.get(role) != null && rolePermissions.get(role).contains(action);
        //return role != null && role.equals("admin");
    }


}
