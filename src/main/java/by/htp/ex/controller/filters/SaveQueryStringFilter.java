package by.htp.ex.controller.filters;



import by.htp.ex.constants.JSPConstants;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.HttpMethod;

import java.io.IOException;
import java.util.Map;

public class SaveQueryStringFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        if (req.getMethod().equals(HttpMethod.GET)) {
            HttpSession session = req.getSession(false);
            Map<String, String[]> paramsMap = req.getParameterMap();

            if (!paramsMap.isEmpty()) {

                session.setAttribute(JSPConstants.LAST_REQUEST_NAME, session.getAttribute(JSPConstants.CURRENT_REQUEST_NAME));
                session.setAttribute(JSPConstants.LAST_REQUEST_PARAMS, session.getAttribute(JSPConstants.CURRENT_REQUEST_PARAMS));

                session.setAttribute(JSPConstants.CURRENT_REQUEST_NAME, req.getRequestURL().toString());
                session.setAttribute(JSPConstants.CURRENT_REQUEST_PARAMS, paramsMap);

                if (session.getAttribute(JSPConstants.LAST_REQUEST_NAME) == null) {
                    session.setAttribute(JSPConstants.LAST_REQUEST_NAME, session.getAttribute(JSPConstants.CURRENT_REQUEST_NAME));
                    session.setAttribute(JSPConstants.LAST_REQUEST_PARAMS, session.getAttribute(JSPConstants.CURRENT_REQUEST_PARAMS));
                }
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
