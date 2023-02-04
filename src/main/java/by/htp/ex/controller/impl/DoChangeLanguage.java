package by.htp.ex.controller.impl;

import by.htp.ex.controller.Command;
import by.htp.ex.constants.ViewConstants;
import by.htp.ex.controller.utilities.Usability;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Map;


@SuppressWarnings("unchecked")
public class DoChangeLanguage implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        session.setAttribute("local", request.getParameter("local"));

        try {

            String lastRequestName = (String) session.getAttribute("last_request_name");
            Map<String, String[]> paramsMap = (Map<String, String[]>) session.getAttribute("last_request_params");
            String lastRequest = Usability.getRequest(lastRequestName,paramsMap);
            if(lastRequest == null) {
                session.setAttribute( ViewConstants.ERROR_MESSAGE,"error getting last request");
                response.sendRedirect("controller?command=go_to_error_page");
            } else {
                response.sendRedirect(lastRequest);
            }
        } catch (Exception e) {
            session.setAttribute( ViewConstants.ERROR_MESSAGE,"error getting last request");
            response.sendRedirect("controller?command=go_to_error_page");
        }
    }
}
