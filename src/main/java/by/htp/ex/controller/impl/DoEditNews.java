package by.htp.ex.controller.impl;

import by.htp.ex.bean.News;
import by.htp.ex.controller.Command;
import by.htp.ex.service.INewsService;
import by.htp.ex.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static by.htp.ex.bean.NewsAttributes.*;
import static by.htp.ex.bean.UserAttributes.USER_ROLE;
import static by.htp.ex.bean.ViewAttributes.ERROR_MESSAGE;


public class DoEditNews implements Command {

    private final INewsService newsService = ServiceProvider.getInstance().getNewsService();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String role = (String) session.getAttribute(USER_ROLE);

        if (role.equals("admin")) {
            int id = Integer.parseInt(request.getParameter(NEWS_ID));

            News newNews = new News(id,request.getParameter(NEWS_TITLE),request.getParameter(NEWS_BRIEF),request.getParameter(NEWS_CONTENT),request.getParameter(NEWS_DATE));
            newsService.update(newNews);
            response.sendRedirect("controller?command=go_to_news_list");
        } else {
            request.setAttribute(ERROR_MESSAGE,"user with such role cannot delete news");
            request.getRequestDispatcher("controller?command=go_to_error_page");
        }
    }
}
