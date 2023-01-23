package by.htp.ex.controller.filters;



import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.ws.rs.HttpMethod;
import org.apache.http.client.methods.HttpGet;

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

                session.setAttribute("last_request_name", session.getAttribute("current_request_name"));
                session.setAttribute("last_request_params", session.getAttribute("current_request_params"));

                session.setAttribute("current_request_name", req.getRequestURL().toString());
                session.setAttribute("current_request_params", paramsMap);

                if (session.getAttribute("last_request_name") == null) {
                    session.setAttribute("last_request_name", session.getAttribute("current_request_name"));
                    session.setAttribute("last_request_params", session.getAttribute("current_request_params"));
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
