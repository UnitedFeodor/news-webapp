package by.htp.ex.controller.impl;

import by.htp.ex.controller.Command;
import by.htp.ex.service.INewsService;
import by.htp.ex.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.htp.ex.bean.NewsAttributes.NEWS_ID;

public class DoDeleteNews implements Command {
    private final INewsService newsService = ServiceProvider.getInstance().getNewsService();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] newsIds = request.getParameterValues(NEWS_ID);
        if (newsIds != null) {
            newsService.delete(newsIds);
            response.sendRedirect("controller?command=go_to_news_list");
        } else {
            request.setAttribute("error_msg","no news to delete selected");
            request.getRequestDispatcher("controller?command=go_to_error_page").forward(request, response);
            //request.getRequestDispatcher("/WEB-INF/pages/tiles/error.jsp").forward(request, response);

            //response.sendRedirect("/WEB-INF/pages/tiles/error.jsp");
        }
        }
}
