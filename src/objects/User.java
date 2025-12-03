package objects;

public class User {

	String username, email,	password, role;
	boolean state;
	
	public User(String username, String email, String password, String role, boolean state) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
		this.state = state;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	
}