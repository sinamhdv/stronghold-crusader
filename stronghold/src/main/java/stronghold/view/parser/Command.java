package stronghold.view.parser;

public enum Command {
	// Menu Navigations
	EXIT("exit"),
	BACK("back"),
	LOGIN_MENU("login menu"),
	SIGNUP_MENU("signup menu"),
	PROFILE_MENU("profile menu"),
	MAP_MENU("map menu"),
	MAP_MANAGEMENT_MENU("map management"),

	// Signup Menu
	SIGNUP("signup [-u,username] [-p,password] [-c,passwordConfirm]? [-e,email] [-n,nickname] [-s,slogan]?"),
	QUESTION_PICK("question pick <-q,questionNumber> [-a,answer] [-c,answerConfirm]"),
	
	// Login Menu
	LOGIN("login [-u,username] [-p,password] [--stay-logged-in,-]?"),
	FORGOT_PASSWORD("forgot my password [-u,username]"),

	// Main Menu
	LOGOUT("logout"),

	// Profile Menu
	CHANGE_USERNAME("profile change username [-u,username]"),
	CHANGE_NICKNAME("profile change nickname [-n,nickname]"),
	CHANGE_PASSWORD("profile change password [-o,oldPassword] [-n,newPassword]"),
	CHANGE_EMAIL("profile change email [-e,email]"),
	CHANGE_SLOGAN("profile change slogan [-s,slogan]"),
	REMOVE_SLOGAN("profile remove slogan"),
	DISPLAY_HIGHSCORE("profile display highscore"),
	DISPLAY_RANK("profile display rank"),
	DISPLAY_SLOGAN("profile display slogan"),
	PROFILE_DISPLAY("profile display"),

	// Map Management Menu
	NEW_MAP("new map [-n,name] <-g,governmentsCount> <-w,width> <-h,height>"),
	EDIT_MAP("edit map [-n,name]"),
	DELETE_MAP("delete map [-n,name]"),
	LIST_MAPS("list maps"),

	// Map Menu
	SHOW_MAP("show map <-x,x> <-y,y>"),
	MOVE_MAP("move map <-u,up=1>? <-d,down=1>? <-r,right=1>? <-l,left=1>?"),
	SHOW_TILE_DETAILS("show tile details <-x,x> <-y,y>"),

	// Game Menu
	SHOW_POPULARITY_FACTORS("show popularity factors"),
	SHOW_POPULARITY("show popularity"),
	SHOW_FOOD_LIST("show food list"),
	SET_FOOD_RATE("food rate <-r,rate>"),
	SHOW_FOOD_RATE("show food rate"),
	SET_TAX_RATE("tax rate <-r,rate>"),
	SHOW_TAX_RATE("show tax rate"),
	SET_FEAR_RATE("fear rate <-r,rate>"),
	DROP_BUILDING("drop building <-x,x> <-y,y> [-t,type]"),
	SELECT_BUILDING("select building <-x,x> <-y,y>"),
	CREATE_UNIT("create unit [-t,type] <-c,count>"),
	REPAIR("repair"),
	SELECT_UNIT("select unit <-x,x> <-y,y>"),
	MOVE_UNIT("move unit to <-x,x> <-y,y>"),
	PATROL_UNIT("patrol unit <-x1,x1> <-y1,y1> <-x2,x2> <-y2,y2>"),
	SET_STANCE("set stance [-s,stanceType]"),
	MELEE_ATTACK("melee attack <-x,x> <-y,y>"),
	RANGED_ATTACK("ranged attack <-x,x> <-y,y>"),
	POUR_OIL("pour oil [-d,direction]"),
	DIG_TUNNEL("dig tunnel <-x,x> <-y,y>"),
	BUILD_SIEGE_EQUIPMENT("build siege equipment [-q,equipment]"),
	DISBAND("disband"),

	// Map Editor Menu
	SET_TEXTURE("set texture <-x,x> <-y,y> [-t,type]"),
	RECTANGLE_SET_TEXTURE("rectangle set texture <-x1,x1> <-y1,y1> <-x2,x2> <-y2,y2> [-t,type]"),
	CLEAR("clear <-x1,x1> <-y1,y1> <-x2,x2> <-y2,y2>"),
	DROP_ROCK("drop rock <-x,x> <-y,y> [-d,direction]"),
	DROP_TREE("drop tree <-x,x> <-y,y> [-t,type]"),
	DROP_UNIT("drop unit <-x,x> <-y,y> [-t,type] <-c,count>"),
	SELECT_GOVERNMENT("select government <-g,government>"),

	// Trade Menu
	TRADE_MENU("trade [-t,resourceType] <-a,amount> <-p,price> [-m,message]"),
	TRADE_LIST("trade list"),
	TRADE_ACCEPT("trade accept [-i,id] [-m,message]"),
	TRADE_HISTORY("trade history"),

	// Market Menu
	SHOW_PRICE_LIST("show price list"),
	BUY("buy [-i,itemName] <-a,amount>"),
	SELL("sell [-i,itemName] <-a,amount>"),
	;

	private final String specification;

	private Command(String specification) {
		this.specification = specification;
	}

	public String getSpecification() {
		return specification;
	}
}
