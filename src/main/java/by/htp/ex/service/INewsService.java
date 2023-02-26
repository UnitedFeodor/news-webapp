package by.htp.ex.service;

import java.util.List;

import by.htp.ex.bean.News;

public interface INewsService {

  void delete(int[] newsIds) throws ServiceException;
  void add(News news) throws ServiceException;

  void find() throws ServiceException;
  void update(News news) throws ServiceException;
  
  List<News> getCountNewsStartingFrom(int count, int from)  throws ServiceException;

  int getTotalNewsAmount() throws ServiceException;
  List<News> list()  throws ServiceException;
  News findById(int id) throws ServiceException;
}
