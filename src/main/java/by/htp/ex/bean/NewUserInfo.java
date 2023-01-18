package by.htp.ex.bean;

import java.io.Serializable;
import java.util.Objects;

public class NewUserInfo implements Serializable {

	private String email;
	private String password;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		NewUserInfo that = (NewUserInfo) o;
		return Objects.equals(email, that.email) && Objects.equals(password, that.password);
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, password);
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
