package by.htp.ex.dao.impl;

import java.sql.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import by.htp.ex.bean.News;
import by.htp.ex.constants.NewsConstants;
import by.htp.ex.dao.DaoException;
import by.htp.ex.dao.INewsDAO;
import by.htp.ex.dao.connection_pool.ConnectionPoolException;
import by.htp.ex.dao.connection_pool.ConnectionPoolProvider;

public class NewsDAO implements INewsDAO {


	private static final String Q_INSERT_NEWS = "INSERT INTO news (title, date_added, brief, content, authors_user_id, news_status_id) VALUES (?,?,?,?,?,?)";

	private static final String Q_DELETE_IDS = "DELETE FROM news WHERE id = ?";
	private static final String Q_UPDATE_NEWS_BY_ID = "UPDATE news SET title = ?, brief = ?, content = ? WHERE id = ?";
	private static final String Q_GET_ALL_NEWS = "SELECT * FROM news";
	private static final String Q_GET_NEWS_BY_ID = "SELECT * FROM news WHERE id = ?";
	private static final String Q_GET_ALL_NEWS_BY_DATE_DESC = "SELECT * FROM news ORDER BY date_added DESC";

	List<News> newsStorage = new ArrayList<>();
	{
		newsStorage.add(new News(1, "title1", "brief1brief1brief1brief1brief1brief1brief1", "content1", new Date(51123231L)));
		newsStorage.add(new News(2, "title2", "brief2brief2brief2brief2brief2brief2brief2", "content2", new Date(123213123123L)));
		newsStorage.add(new News(3, "title3", "brief3brief3brief3brief3brief3brief3brief3", "content3", new Date(876535435435344L)));
		newsStorage.add(new News(4, "title4", "brief4brief4brief4brief4brief4brief4brief4", "content4", new Date(23235435344L)));
		newsStorage.add(new News(5, "title5", "brief5brief5brief5brief5brief5brief5brief5", "content5", new Date(1477653543235435344L)));

	}
	@Override
	public List<News> getLatestList(int count) throws DaoException {
		try(Connection connection = ConnectionPoolProvider.getInstance().takeConnection();
			PreparedStatement statement = connection.prepareStatement(Q_GET_ALL_NEWS_BY_DATE_DESC);
			ResultSet rs = statement.executeQuery()) {

			List<News> newsList = new ArrayList<>();
			while (rs.next()) {
				News currNews = new News();
				currNews.setIdNews(rs.getInt(NewsConstants.DB_ID));
				currNews.setTitle(rs.getString(NewsConstants.DB_TITLE));
				currNews.setBrief(rs.getString(NewsConstants.DB_BRIEF));
				currNews.setContent(rs.getString(NewsConstants.DB_CONTENT));
				currNews.setNewsDate(rs.getDate(NewsConstants.DB_DATE));
				newsList.add(currNews);

			}
			return newsList;

		} catch (SQLException e) {
			throw new DaoException(e);
		} catch (ConnectionPoolException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public List<News> getList() throws DaoException {
		try(Connection connection = ConnectionPoolProvider.getInstance().takeConnection();
			 	PreparedStatement statement = connection.prepareStatement(Q_GET_ALL_NEWS);
			 	ResultSet rs = statement.executeQuery()) {

			List<News> newsList = new ArrayList<>();
			while (rs.next()) {
				News currNews = new News();
				currNews.setIdNews(rs.getInt(NewsConstants.DB_ID));
				currNews.setTitle(rs.getString(NewsConstants.DB_TITLE));
				currNews.setBrief(rs.getString(NewsConstants.DB_BRIEF));
				currNews.setContent(rs.getString(NewsConstants.DB_CONTENT));
				currNews.setNewsDate(rs.getDate(NewsConstants.DB_DATE));
				newsList.add(currNews);
			}
			return newsList;

		} catch (SQLException e) {
			throw new DaoException(e);
		} catch (ConnectionPoolException e) {
			throw new DaoException(e);
		}
	}
	@Override
	public News fetchById(int id) throws DaoException {
		try(Connection connection = ConnectionPoolProvider.getInstance().takeConnection();
			PreparedStatement statement = connection.prepareStatement(Q_GET_NEWS_BY_ID)) {
			statement.setInt(1,id);
			try (ResultSet rs = statement.executeQuery()) {
				if(!rs.isBeforeFirst()) {
					throw new DaoException("no news with such id in db");
				}
				//List<News> newsList = new ArrayList<>();
				rs.next();
				News newsFound = new News(id);
				newsFound.setTitle(rs.getString(NewsConstants.DB_TITLE));
				newsFound.setBrief(rs.getString(NewsConstants.DB_BRIEF));
				newsFound.setContent(rs.getString(NewsConstants.DB_CONTENT));
				newsFound.setNewsDate(rs.getDate(NewsConstants.DB_DATE));

				return newsFound;
			} catch (SQLException ex) {
				throw new DaoException(ex);
			}

		} catch (SQLException e) {
			throw new DaoException(e);
		} catch (ConnectionPoolException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public int addNews(News news) throws DaoException {
		try(Connection connection = ConnectionPoolProvider.getInstance().takeConnection();
			PreparedStatement statement = connection.prepareStatement(Q_INSERT_NEWS)) {
			statement.setString(1,news.getTitle());
			statement.setDate(2,news.getNewsDate());
			statement.setString(3,news.getBrief());
			statement.setString(4,news.getContent());
			statement.setInt(5,news.getAuthorId());
			statement.setInt(6,news.getStatusId());

			int rowsAffected = statement.executeUpdate();
			if (rowsAffected != 1) {
				throw new DaoException("error updating news in db");
			}
			return 0;

		} catch (SQLException e) {
			throw new DaoException(e);
		} catch (ConnectionPoolException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public void updateNews(News news) throws DaoException {
		try(Connection connection = ConnectionPoolProvider.getInstance().takeConnection();
			PreparedStatement statement = connection.prepareStatement(Q_UPDATE_NEWS_BY_ID)) {
			statement.setString(1,news.getTitle());
			statement.setString(2,news.getBrief());
			statement.setString(3,news.getContent());

			statement.setInt(4,news.getIdNews());

			int rowsAffected = statement.executeUpdate();
			if (rowsAffected != 1) {
				throw new DaoException("error updating news in db");
			}

		} catch (SQLException e) {
			throw new DaoException(e);
		} catch (ConnectionPoolException e) {
			throw new DaoException(e);
		};

	}

	@Override
	public void deleteNews(String[] idNews) throws DaoException {
		try(Connection connection = ConnectionPoolProvider.getInstance().takeConnection();
			PreparedStatement statement = connection.prepareStatement(Q_DELETE_IDS)) {
			for(String id : idNews) {
				int currId = Integer.parseInt(id);
				statement.setInt(1,currId);
				statement.addBatch();
			}

			int[] affectedRecords = statement.executeBatch(); // TODO ask what is correct

			for (int i = 0; i < affectedRecords.length; i++) {
				if (affectedRecords[i] != 1) {
					throw new DaoException("error deleting news from db");
				}
			}



		} catch (SQLException e) {
			throw new DaoException(e);
		} catch (ConnectionPoolException e) {
			throw new DaoException(e);
		};


		/*
		var idToDelete = new ArrayList<>(Arrays.asList(idNews));
		new ArrayList<>(newsStorage).stream().forEach(o -> {
			String id = o.getIdNews().toString();
			if(idToDelete.contains(id)) {
				newsStorage.remove(o);
		 		idToDelete.remove(id);
			}
		}); */

	}

}
