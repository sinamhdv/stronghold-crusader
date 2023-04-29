package stronghold.view.parser;

public class CommandSpecificationToken {
	private final String tokenString;
	private String switchName;
	private String groupName;
	private String defaultValue;
	private boolean isOptional;
	private boolean hasArgument = true;
	private boolean isNumeric;

	public CommandSpecificationToken(String tokenString) {
		this.tokenString = tokenString;
		parseToken();
	}

	private void parseToken() {
		isNumeric = (tokenString.charAt(0) == '<');
		isOptional = (tokenString.charAt(tokenString.length() - 1) == '?');
		String[] parts = tokenString.substring(1, tokenString.length() - 1 - (isOptional ? 1 : 0)).split(",");
		switchName = parts[0];
		if (parts[1].contains("=")) {
			groupName = parts[1].split("=")[0];
			defaultValue = parts[1].split("=")[1];
		}
		else {
			groupName = parts[1];
			defaultValue = null;
		}
		if (groupName.equals("-")) {
			hasArgument = false;
			groupName = switchName;
		}
	}

	public String getDefaultValue() {
		return defaultValue;
	}
	public String getSwitchName() {
		return switchName;
	}
	public String getGroupName() {
		return groupName;
	}
	public String getTokenString() {
		return tokenString;
	}
	public boolean isOptional() {
		return isOptional;
	}
	public boolean hasArgument() {
		return hasArgument;
	}
	public boolean isNumeric() {
		return isNumeric;
	}
}
