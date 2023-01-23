package by.htp.ex.controller.filters;

import by.htp.ex.controller.CommandName;
import by.htp.ex.controller.constants.UserConstants;
import by.htp.ex.controller.constants.ViewConstants;
import by.htp.ex.controller.impl.utilities.Security;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static by.htp.ex.controller.constants.ViewConstants.ERROR_MESSAGE;


public class SecurityRoleFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest filteredRequest = new HttpServletRequestWrapper((HttpServletRequest) request){
            @Override
            public String getParameter(final String name) {
                return CommandName.GO_TO_ERROR_PAGE.toString().toLowerCase();

            }
        };

        HttpSession session = ((HttpServletRequest) request).getSession(false);

        String commandNameStr = request.getParameter(ViewConstants.COMMAND);
        if (commandNameStr == null) {
            session.setAttribute(ERROR_MESSAGE,"no command passed in request");
            chain.doFilter(filteredRequest,response);

        } else {
            try {
                CommandName commandName = CommandName.valueOf(commandNameStr.toUpperCase());
                String userRole = (String) session.getAttribute(UserConstants.USER_ROLE);
                if(Security.canExecuteThisRequest(userRole, commandName)) {
                    chain.doFilter(request,response);

                } else {
                    session.setAttribute(ERROR_MESSAGE,"cannot do this request for this role");
                    chain.doFilter(filteredRequest,response);
                }
            } catch (IllegalArgumentException e) {
                session.setAttribute(ERROR_MESSAGE,"invalid command passed in request");
                chain.doFilter(filteredRequest,response);
            }


        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}