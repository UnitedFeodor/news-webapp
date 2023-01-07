package by.htp.ex.controller.impl;

import by.htp.ex.bean.News;
import by.htp.ex.controller.Command;
import by.htp.ex.service.INewsService;
import by.htp.ex.service.ServiceException;
import by.htp.ex.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class GoToAddNews implements Command {
    private final INewsService newsService = ServiceProvider.getInstance().getNewsService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id;
        try {
            id = newsService.list().size() + 1;
        } catch (ServiceException e) {
            throw new RuntimeException(e);
            //TODO exception
        }
        News news = new News(id, "", "", "", "");
        request.setAttribute("news", news);
        request.setAttribute("presentation", "addNews");
        request.getRequestDispatcher("WEB-INF/pages/layouts/baseLayout.jsp").forward(request, response);


    }
}
