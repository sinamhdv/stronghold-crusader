package stronghold.model;

import java.io.Serializable;

import stronghold.server.ClientHandler;
import stronghold.utils.Cryptography;
import stronghold.utils.DatabaseManager;
import stronghold.view.ProfileMenu;

public class User implements Comparable<User>, Serializable {
	private String userName;
	private String password;
	private String nickName;
	private String slogan;
	private String email;
	private int highScore;
	private int securityQuestionNumber;
	private String securityQuestionAnswer;
	private String avatarURL = ProfileMenu.getAvatarURLByIndex(1);

	private transient ClientHandler clientHandler;
	
	public User(String userName, String password, String nickName, String slogan, String email, int highScore,
			int securityQuestionNumber, String securityQuestionAnswer) {
		this.userName = userName;
		this.password = Cryptography.hashPassword(password);
		this.nickName = nickName;
		this.slogan = slogan;
		this.email = email.toLowerCase();
		this.highScore = highScore;
		this.securityQuestionNumber = securityQuestionNumber;
		this.securityQuestionAnswer = securityQuestionAnswer;
	}
	public void setEmail(String email) {
		this.email = email.toLowerCase();
		DatabaseManager.updateUser(this);
	}
	public void setHighScore(int highScore) {
		this.highScore = highScore;
		DatabaseManager.updateUser(this);
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
		DatabaseManager.updateUser(this);
	}
	public void setPassword(String password) {
		this.password = Cryptography.hashPassword(password);
		DatabaseManager.updateUser(this);
	}
	public void setSecurityQuestionAnswer(String securityQuestionAnswer) {
		this.securityQuestionAnswer = securityQuestionAnswer;
		DatabaseManager.updateUser(this);
	}
	public void setSecurityQuestionNumber(int securityQuestionNumber) {
		this.securityQuestionNumber = securityQuestionNumber;
		DatabaseManager.updateUser(this);
	}
	public void setSlogan(String slogan) {
		this.slogan = slogan;
		DatabaseManager.updateUser(this);
	}
	public void setUserName(String userName) {
		this.userName = userName;
		DatabaseManager.updateUser(this);
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
	public String getAvatarURL() {
		return avatarURL;
	}
	public void setAvatarURL(String avatarURL) {
		this.avatarURL = avatarURL;
		DatabaseManager.updateUser(this);
	}

	public ClientHandler getClientHandler() {
		return clientHandler;
	}
	public void setClientHandler(ClientHandler clientHandler) {
		this.clientHandler = clientHandler;
	}

	@Override
	public int compareTo(User other)
	{
		int thiRank = StrongHold.getRank(this);
		int otherRank = StrongHold.getRank(other);
		if(thiRank != otherRank)
			return (int)(thiRank - otherRank);
		else
			return this.userName.compareTo(other.userName);
	}
}