package stronghold.model;

public class User {
	private String userName;
	private String password;
	private String nickName;
	private String slogan;
	private String email;
	private int highScore;
	private int securityQuestionNumber;
	private String securityQuestionAnswer;
	
	public User(String userName, String password, String nickName, String slogan, String email, int highScore,
			int securityQuestionNumber, String securityQuestionAnswer) {
		this.userName = userName;
		this.password = password;
		this.nickName = nickName;
		this.slogan = slogan;
		this.email = email;
		this.highScore = highScore;
		this.securityQuestionNumber = securityQuestionNumber;
		this.securityQuestionAnswer = securityQuestionAnswer;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setSecurityQuestionAnswer(String securityQuestionAnswer) {
		this.securityQuestionAnswer = securityQuestionAnswer;
	}
	public void setSecurityQuestionNumber(int securityQuestionNumber) {
		this.securityQuestionNumber = securityQuestionNumber;
	}
	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserName() {
		return userName;
	}
	public String getEmail() {
		return email;
	}
	public int getHighScore() {
		return highScore;
	}
	public String getNickName() {
		return nickName;
	}
	public String getPassword() {
		return password;
	}
	public String getSecurityQuestionAnswer() {
		return securityQuestionAnswer;
	}
	public int getSecurityQuestionNumber() {
		return securityQuestionNumber;
	}
	public String getSlogan() {
		return slogan;
	}


}