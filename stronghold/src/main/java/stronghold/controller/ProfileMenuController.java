package stronghold.controller;

import stronghold.controller.messages.SignupAndProfileMenuMessage;
import stronghold.model.StrongHold;
import stronghold.utils.FormatValidation;

public class ProfileMenuController {
	public static SignupAndProfileMenuMessage changeUserName(String newUsername)
	{
		if (newUsername.equals("")) return SignupAndProfileMenuMessage.EMPTY_FIELD;
		else if(FormatValidation.checkUserName(newUsername))
		{
			StrongHold.getCurrentUser().setUserName(newUsername);
			return SignupAndProfileMenuMessage.CHANGE_USERNAME_SUCCESSFUL;
		}
		return SignupAndProfileMenuMessage.INVALID_USERNAME;
	}
	public static SignupAndProfileMenuMessage changeNickName(String newNickName)
	{
		if(newNickName.equals("")) return SignupAndProfileMenuMessage.EMPTY_FIELD;
		else 
		{
			StrongHold.getCurrentUser().setNickName(newNickName);
			return SignupAndProfileMenuMessage.CHANGE_NICKNAME_SUCCESSFUL;
		}
	}
	public static SignupAndProfileMenuMessage changePassword(String newPassword, String oldPassword)
	{
		if(newPassword.equals("") || oldPassword.equals(""))
			return SignupAndProfileMenuMessage.EMPTY_FIELD;
		else if(!StrongHold.getCurrentUser().getPassword().equals(oldPassword))
			return SignupAndProfileMenuMessage.OLD_PASSWORD_WRONG;
		else if(StrongHold.getCurrentUser().getPassword().equals(newPassword))
			return SignupAndProfileMenuMessage.OLD_AND_NEW_PASSWORD_ARE_EQUAL;
		else if(FormatValidation.checkPasswordStrength(newPassword).equals(SignupAndProfileMenuMessage.PASSWORD_IS_STRONG))
		{
			StrongHold.getCurrentUser().setPassword(newPassword);
		}
		return FormatValidation.checkPasswordStrength(newPassword);	
	}
	public static SignupAndProfileMenuMessage changeEmail(String newEmail)
	{
		if(newEmail.equals("")) return SignupAndProfileMenuMessage.EMPTY_FIELD;
		else if (!newEmail.matches("[\\w\\.]+@[\\w\\.]+//.[\\w\\.]+")) return SignupAndProfileMenuMessage.INVALID_EMAIL;
		StrongHold.getCurrentUser().setEmail(newEmail);
		return SignupAndProfileMenuMessage.CHANGE_EMAIL_SUCCESSFUL;
	}
	public static SignupAndProfileMenuMessage changeSlogan(String slogan)
	{
		if(slogan.equals("")) return SignupAndProfileMenuMessage.EMPTY_FIELD;
		StrongHold.getCurrentUser().setSlogan(slogan);
		return SignupAndProfileMenuMessage.CHANGE_SLOGAN_SUCCESSFUL;
	}
	public static SignupAndProfileMenuMessage removeSlogan()
	{
		StrongHold.getCurrentUser().setSlogan(null);
		return SignupAndProfileMenuMessage.REMOVE_SLOGAN_SUCCESSFUL;
	}
}
