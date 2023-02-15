package by.htp.ex.util.validation;

import by.htp.ex.bean.User;

public interface IUserValidator extends IValidator {

       IUserValidator checkId();
       IUserValidator checkLogin();
       IUserValidator checkPassword();
       IUserValidator checkRole();
       IUserValidator checkName();
       IUserValidator checkSurname();
       IUserValidator checkBirthday();

       IUserValidator checkAll();
       IUserValidator checkAllExceptId();
       IUserValidator setUser(User user);
       User getUser();



}
