package by.htp.ex.controller.filters;



import jakarta.servlet.*;

import java.io.IOException;

public class CharsetFilter implements Filter {
    private String encoding;
    private ServletContext context;

    private final static String CHARACTER_ENCODING = "characterEncoding";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter(CHARACTER_ENCODING);
        context = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);

        context.log("Charset was set.");

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
