package by.htp.ex.util.validation.impl;

import by.htp.ex.bean.User;
import by.htp.ex.constants.UserConstants;
import by.htp.ex.util.validation.IUserValidator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserValidatorImpl implements IUserValidator {

	private List<String> errors;
	private User user;

	private final int FIELD_COUNT = 7;

	public UserValidatorImpl(User user) {
		this.errors = new ArrayList<>(FIELD_COUNT);
		this.user = new User(user);
	}

	public UserValidatorImpl() {
		this.errors = new ArrayList<>(FIELD_COUNT);
	}

	@Override
	public IUserValidator setUser(User user) {
		this.user = new User(user);
		errors.clear();
		return this;
	}
	@Override
	public User getUser() {
		return new User(user);
	}

	@Override
	public List<String> getErrors() {
		return new ArrayList<>(errors);
	}

	private final String ID_ERR = "id error";
	// correct if id >= 0
	@Override
	public IUserValidator checkId() {
		if(user.getId() < 0) {
			errors.add(ID_ERR);
		}
		return this;
	}

	private final String EMAIL_LENIENT_REGEX = "^.+@.+\\..+$";
	private final String LOGIN_ERR = "login error";
	// correct if it's an email TODO current debug variant: correct simply if not empty
	// not empty
	@Override
	public IUserValidator checkLogin() {
		String email = user.getEmail();
		if (email == null || email.isEmpty()) {
			errors.add(LOGIN_ERR);
		}  /*else { //TODO uncomment for proper validation
			Pattern pattern = Pattern.compile(EMAIL_LENIENT_REGEX);
			Matcher matcher = pattern.matcher(email);
			if (!matcher.matches()) {
				errors.add(LOGIN_ERR);
			}
		} */
		return this;
	}

	private final String PASSWORD_ERR = "password error";
	// not empty
	@Override
	public IUserValidator checkPassword() {
		String password = user.getPassword();
		if (password == null || password.isEmpty()) {
			errors.add(PASSWORD_ERR);
		}
		return this;
	}

	private final String ROLE_ERR = "role error";
	// should be one of the role constants
	@Override
	public IUserValidator checkRole() {
		List<String> roleConstants = List.of(new String[]{UserConstants.ROLE_USER, UserConstants.ROLE_ADMIN, UserConstants.ROLE_GUEST});
		if (user.getRole() == null || !roleConstants.contains(user.getRole())) {
			errors.add(ROLE_ERR);
		}
		return this;
	}

	private final String LETTER_SYMBOLS_REGEX = "[a-zA-Z]+";
	private final String NAME_ERR = "name error";
	// only letters allowed
	// can be empty
	@Override
	public IUserValidator checkName() {
		String name = user.getName();
		if(name == null || (!name.isEmpty() && !name.matches(LETTER_SYMBOLS_REGEX))) {
			errors.add(NAME_ERR);
		}
		return this;
	}

	private final String SURNAME_ERR = "surname error";
	// only letters allowed
	// can be empty
	@Override
	public IUserValidator checkSurname() {
		String surname = user.getSurname();

		if(surname == null || (!surname.isEmpty() && !surname.matches(LETTER_SYMBOLS_REGEX))) {
			errors.add(SURNAME_ERR);
		}
		return this;
	}


	private final String BIRTHDAY_ERR = "birthday error";
	// should be in the past
	// should be over ALLOWED_AGE years old
	private final int ALLOWED_AGE = 13;
	@Override
	public IUserValidator checkBirthday() {
		LocalDate birthday = user.getBirthday();
		if (!(birthday == null) && (birthday.isAfter(LocalDate.now()) || birthday.plusYears(ALLOWED_AGE).isAfter(LocalDate.now()))) {
			errors.add(BIRTHDAY_ERR);
		}
		return this;
	}

	@Override
	public IUserValidator checkAll() {
		return this.checkId().checkLogin().checkPassword().checkRole().checkName().checkSurname().checkBirthday();
	}

	@Override
	public IUserValidator checkAllExceptId() {
		return this.checkLogin().checkPassword().checkRole().checkName().checkSurname().checkBirthday();
	}

	@Override
	public boolean isValid() {
		return errors.isEmpty();
	}
}
