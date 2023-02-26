package by.htp.ex.dao;

import java.util.List;

import by.htp.ex.bean.News;
import by.htp.ex.service.ServiceException;


public interface INewsDAO {
	List<News> getList() throws DaoException;
	List<News> getCountNewsStartingFrom(int count, int from) throws DaoException;
	News fetchById(int id) throws DaoException;
	int addNews(News news) throws DaoException;
	void updateNews(News news) throws DaoException;
	void deleteNews(int[] newsIds)throws DaoException;

	int getTotalNewsAmount() throws DaoException;
}
