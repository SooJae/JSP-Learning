package user;

public class UserDTO {
	
	private String userID;
	private String userPassword;
	private String userEmail;
	private String userEmailHash;
	private boolean userChecked;
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserEmailHash() {
		return userEmailHash;
	}
	public void setUserEmailHash(String userEmailHash) {
		this.userEmailHash = userEmailHash;
	}
	public boolean isUserChecked() {
		return userChecked;
	}
	public void setUserChecked(boolean userChecked) {
		this.userChecked = userChecked;
	}
	
	
	public UserDTO() {
	}
	
	public UserDTO(String userID, String userPassword, String userEmail, String userEmailHash, boolean userChecked) {
		this.userID = userID;
		this.userPassword = userPassword;
		this.userEmail = userEmail;
		this.userEmailHash = userEmailHash;
		this.userChecked = userChecked;
	}
	
	
	
}
