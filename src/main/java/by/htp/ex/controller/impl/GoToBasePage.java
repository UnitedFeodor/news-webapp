package by.htp.ex.controller.impl;

import java.io.IOException;
import java.util.List;

import by.htp.ex.bean.News;
import by.htp.ex.controller.Command;
import by.htp.ex.constants.ViewConstants;
import by.htp.ex.service.INewsService;
import by.htp.ex.service.ServiceException;
import by.htp.ex.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;



public class GoToBasePage implements Command{
	
	private final INewsService newsService = ServiceProvider.getInstance().getNewsService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//TODO make more param strings constants?
		List<News> latestNews;
		try {
			latestNews = newsService.latestList(5);
			if(latestNews.size() > 0) {
				request.setAttribute(ViewConstants.NEWS, latestNews);

			}
			request.getRequestDispatcher("WEB-INF/pages/layouts/baseLayout.jsp").forward(request, response);
		} catch (ServiceException e) {
			HttpSession session = request.getSession(false);
			session.setAttribute(ViewConstants.ERROR_MESSAGE,"cannot get the latest list of news");
			response.sendRedirect("controller?command=go_to_error_page");
		}

		
	}

}
