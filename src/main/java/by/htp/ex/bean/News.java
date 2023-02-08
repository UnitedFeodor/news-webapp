package by.htp.ex.bean;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

public class News implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer idNews = -1;
	private String title = "";
	private String brief = "";
	private String content = "";
	private Date newsDate = new Date(System.currentTimeMillis());

	private Integer authorId = -1;
	private Integer statusId = 1;



	public News(){}

	public News(int idNews){
		this.idNews = idNews;
	}

	public News(int idNews, String title, String brief, String content, Date newsDate) {
		super();
		this.idNews = idNews;
		this.title = title;
		this.brief = brief;
		this.content = content;
		this.newsDate = newsDate;
	}

	public Integer getIdNews() {
		return idNews;
	}

	public void setIdNews(Integer idNews) {
		this.idNews = idNews;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
		
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public Date getNewsDate() {
		return newsDate;
	}

	public void setNewsDate(Date newsDate) {
		this.newsDate = newsDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		News news = (News) o;
		return idNews.equals(news.idNews) && title.equals(news.title) && brief.equals(news.brief) && content.equals(news.content) && newsDate.equals(news.newsDate) && authorId.equals(news.authorId) && statusId.equals(news.statusId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idNews, title, brief, content, newsDate, authorId, statusId);
	}
}
