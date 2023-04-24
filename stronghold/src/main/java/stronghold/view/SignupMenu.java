package stronghold.view;

import java.util.HashMap;
import java.util.Scanner;

import stronghold.view.parser.CommandParser;

public class SignupMenu {
	public static boolean randomPasswordLoop(String randomPassword)
	{
		Scanner scaner = new Scanner(System.in);
		String enterPassword = "";
		while(true)
		{

			System.out.println("Your random password is: " + randomPassword);
			System.out.println("Please re-enter your password here: ");
			enterPassword = scaner.nextLine();
			if (enterPassword.equals(randomPassword)) return true;
		}
	}
	public static String[] securityQuestionLoop()
	{
		while(true)
		{
			System.out.println("Pick your security question: 1. What is my father's name? " +
			"2. Whatwas my first pet's name? "+"3. What is my mother's last name?");
			String line = MainMenu.getScanner().nextLine();
			String[] inputTokens = CommandParser.splitTokens(line);
			HashMap<String, String> matcher;
			//TODO: if(parserinterface)
			/*if(answerConfirmation.equals(answer)&& (number == 1 || number == 2 || number == 3))
			{
				String[] result;
				result[0] = (String) number;
				result[1] = answer;
				return result;
			}*/
		}
	}
}
