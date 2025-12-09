package objects;

import java.util.ArrayList;

public class User {

	String username, password, email, role;
	boolean isNew;
	ArrayList<String> preferencesList = new ArrayList<>();
	
	public User(String username, String password, String email, String role, boolean isNew,
			ArrayList<String> preferencesList) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.isNew = isNew;
		this.preferencesList = preferencesList;
	}
	public User(String username, String password, String email, String role, boolean isNew) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.isNew = isNew;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public boolean isNew() {
		return isNew;
	}
	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}
	public ArrayList<String> getPreferencesList() {
		return preferencesList;
	}
	public void setPreferencesList(ArrayList<String> preferencesList) {
		this.preferencesList = preferencesList;
	}
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", email=" + email + ", role=" + role
				+ ", isNew=" + isNew + ", preferencesList=" + preferencesList + "]";
	}
}