package by.htp.ex.util.validation;

import by.htp.ex.bean.News;
import by.htp.ex.bean.User;

public interface INewsValidator extends IValidator {
    INewsValidator checkId();
    INewsValidator checkTitle();
    INewsValidator checkBrief();
    INewsValidator checkContent();
    INewsValidator checkDate();
    INewsValidator checkAuthorId();
    INewsValidator checkStatusId();

    INewsValidator checkAll();
    INewsValidator checkAllExceptId();
    INewsValidator setNews(News news);
    News getNews();
}
