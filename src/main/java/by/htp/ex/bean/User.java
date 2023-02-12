package by.htp.ex.bean;

import by.htp.ex.constants.UserConstants;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class User implements Serializable {

	public User() {
		this.role = UserConstants.ROLE_USER;
	}

	public User(String email, String password) {
		this.email = email;
		this.password = password;
		this.role = UserConstants.ROLE_USER;
	}
	public User(String email, String password, String role) {
		this.email = email;
		this.password = password;
		this.role = role;
	}

	private int id = -1;
	private String email;
	private String password;
	private String role;

	private String name = "";
	private String surname = "";
	private LocalDate birthday = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, password,role);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
