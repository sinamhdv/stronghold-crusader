package stronghold.controller;

import stronghold.controller.messages.SignupAndProfileMenuMessage;
import stronghold.model.StrongHold;

public class ProfileMenuController {
	public SignupAndProfileMenuMessage changeUserName(String newUsername)
	{
		if (newUsername == null || newUsername.equals("")) return SignupAndProfileMenuMessage.EMPTY_FIELD;
		else if(CentralController.checkUserName(newUsername))
		{
			StrongHold.getCurrentUser().setUserName(newUsername);
			return SignupAndProfileMenuMessage.CHANGE_USERNAME_SUCCESSFUL;
		}
		return SignupAndProfileMenuMessage.INVALID_USERNAME;
	}
	public SignupAndProfileMenuMessage changeNickName(String newNickName)
	{
		if(newNickName == null || newNickName.equals("")) return SignupAndProfileMenuMessage.EMPTY_FIELD;
		else 
		{
			StrongHold.getCurrentUser().setNickName(newNickName);
			return SignupAndProfileMenuMessage.CHANGE_NICKNAME_SUCCESSFUL;
		}
	}
	public SignupAndProfileMenuMessage changePassword(String newPassword)
	{
		if(newPassword == null || newPassword.equals("")) return SignupAndProfileMenuMessage.EMPTY_FIELD;
		else if(CentralController.checkPasswordStrength(newPassword).equals(SignupAndProfileMenuMessage.PASSWORD_IS_STRONG))
		{
			StrongHold.getCurrentUser().setPassword(newPassword);
		}
		return CentralController.checkPasswordStrength(newPassword);	
	}
	public SignupAndProfileMenuMessage changeEmail(String newEmail)
	{
		if(newEmail == null || newEmail.equals("")) return SignupAndProfileMenuMessage.EMPTY_FIELD;
		else if (!newEmail.matches("[\\w\\.]+@[\\w\\.]+//.[\\w\\.]+")) return SignupAndProfileMenuMessage.INVALID_EMAIL;
		StrongHold.getCurrentUser().setEmail(newEmail);
		return SignupAndProfileMenuMessage.CHANGE_EMAIL_SUCCESSFUL;
	}
	public SignupAndProfileMenuMessage changeSlogan(String slogan)
	{
		if(slogan == null || slogan.equals("")) return SignupAndProfileMenuMessage.EMPTY_FIELD;
		StrongHold.getCurrentUser().setSlogan(slogan);
		return SignupAndProfileMenuMessage.CHANGE_SLOGAN_SUCCESSFUL;
	}
}
