package stronghold.view;

public enum TerminalColor {
	BLACK("\033[30m", "\033[40m"),
	BLUE("\033[34m", "\033[44m"),
	GREEN("\033[32m", "\033[42m"),
	CYAN("\033[36m", "\033[46m"),
	RED("\033[31m", "\033[41m"),
	PURPLE("\033[35m", "\033[45m"),
	BROWN("\033[33m", "\033[43m"),
	GRAY("\033[37m", "\033[47m"),
	;

	private final String forgroundColorCode;
	private final String backgroundColorCode;

	private TerminalColor(String forgroundColorCode, String backgroundColorCode) {
		this.forgroundColorCode = forgroundColorCode;
		this.backgroundColorCode = backgroundColorCode;
	}

	public static void setColor(TerminalColor background, TerminalColor forground) {
		System.out.print(background.backgroundColorCode + forground.forgroundColorCode);
	}

	public static void resetColor() {
		System.out.print("\033[0m");
	}
}
