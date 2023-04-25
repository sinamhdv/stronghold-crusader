package stronghold.utils;

public class FormatValidation {
	public static boolean isNumber(String string) {
		return string.matches("\\-?\\d+");
	}
}
