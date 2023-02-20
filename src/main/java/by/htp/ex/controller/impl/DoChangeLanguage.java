package by.htp.ex.controller.impl;

import by.htp.ex.controller.Command;
import by.htp.ex.constants.JSPConstants;
import by.htp.ex.controller.util.RequestHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang.NullArgumentException;

import java.io.IOException;
import java.util.Map;


@SuppressWarnings("unchecked")
public class DoChangeLanguage implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        session.setAttribute(JSPConstants.LOCALE, request.getParameter(JSPConstants.LOCALE));

        try {

            String lastRequestName = (String) session.getAttribute(JSPConstants.LAST_REQUEST_NAME);
            if (lastRequestName == null ) {
                throw new NullArgumentException("invalid request parameters");
            }

            Map<String, String[]> paramsMap = (Map<String, String[]>) session.getAttribute(JSPConstants.LAST_REQUEST_PARAMS);
            if (paramsMap == null ) {
                throw new NullArgumentException("invalid request parameters");
            }

            String lastRequest = RequestHelper.formRequestURL(lastRequestName,paramsMap);
            response.sendRedirect(lastRequest);

        } catch (NullArgumentException e) {
            session.setAttribute(JSPConstants.ERROR_MESSAGE,"error getting last request");
            response.sendRedirect(JSPConstants.CONTROLLER_GO_TO_ERROR_PAGE);
        }
    }
}
