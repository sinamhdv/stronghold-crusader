package stronghold.view;

import stronghold.model.StrongHold;

public class ProfileMenu{
	private void displayHighscore(){
		System.out.println("you're highscore is: " + StrongHold.getCurrentUser().getHighScore());
	}
	private void displayRank(){
		System.out.println("you're rank is: " + StrongHold.getRank(StrongHold.getCurrentUser()));
	}
	private void displaySlogan(){
		if(StrongHold.getCurrentUser().getSlogan() == null)
			System.out.println("you dont have any slogans");
		System.out.println("you're slogan is: " + StrongHold.getCurrentUser().getSlogan());
	}
	private void displayInfo(){
		System.out.print("you're highscore is: " + StrongHold.getCurrentUser().getHighScore() + "\n" +
						"you're rank is: " + StrongHold.getRank(StrongHold.getCurrentUser()) + "\n");
		displaySlogan();
	}
}
