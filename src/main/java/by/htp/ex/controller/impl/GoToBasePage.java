package by.htp.ex.controller.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

import by.htp.ex.bean.News;
import by.htp.ex.controller.Command;
import by.htp.ex.service.INewsService;
import by.htp.ex.service.ServiceException;
import by.htp.ex.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import static by.htp.ex.controller.constants.ViewConstants.ERROR_MESSAGE;

public class GoToBasePage implements Command{
	
	private final INewsService newsService = ServiceProvider.getInstance().getNewsService();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<News> latestNews;
		try {
			latestNews = newsService.latestList(5);
			latestNews.sort(new Comparator<News>() {
				@Override
				public int compare(News o1, News o2) {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
					LocalDate date1 = LocalDate.parse(o1.getNewsDate(), formatter);
					LocalDate date2 = LocalDate.parse(o2.getNewsDate(), formatter);
					return date2.compareTo(date1);
				}
			});
			request.setAttribute("news", latestNews);
			//request.setAttribute("news", null);

			request.getRequestDispatcher("WEB-INF/pages/layouts/baseLayout.jsp").forward(request, response);
		} catch (ServiceException e) {
			HttpSession session = request.getSession(true);
			session.setAttribute(ERROR_MESSAGE,"cannot get the latest list of news");
			response.sendRedirect("controller?command=go_to_error_page");
		}

		
	}

}
