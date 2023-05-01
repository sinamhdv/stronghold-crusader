package stronghold.view.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.AbstractMap.SimpleEntry;

import stronghold.utils.FormatValidation;

public class CommandParser {
	public static String[] splitTokens(String line) {
		ArrayList<String> tokens = new ArrayList<>();
		for (int i = 0; i < line.length();) {
			while (i < line.length() && Character.isWhitespace(line.charAt(i))) i++;
			if (i >= line.length()) break;
			SimpleEntry<String, Integer> tokenEntry = readToken(line.substring(i));
			if (tokenEntry == null) return null;
			tokens.add(tokenEntry.getKey());
			i += tokenEntry.getValue();
		}
		return tokens.toArray(new String[0]);
	}

	private static SimpleEntry<String, Integer> readToken(String line) {
		int i = (line.charAt(0) == '"' ? 1 : 0);
		String result = "";
		for (; i < line.length(); i++) {
			if (line.charAt(i) == '\\') {
				if (i + 1 < line.length() && line.charAt(i + 1) == '"') {
					result += "\"";
					i++;
				}
				else {
					result += "\\";
					if (i + 1 < line.length() && line.charAt(i + 1) == '\\') i++;
				}
			}
			else if (line.charAt(i) == '"') {
				if (line.charAt(0) != '"') return null;
				if (i + 1 < line.length() && !Character.isWhitespace(line.charAt(i + 1))) return null;
				return new SimpleEntry<>(result, i + 1);
			}
			else if (line.charAt(0) != '"' && Character.isWhitespace(line.charAt(i)))
				return new SimpleEntry<>(result, i);
			else
				result += line.charAt(i);
		}
		return new SimpleEntry<>(result, i);
	}

	public static HashMap<String, String> getMatcher(String[] tokens, Command command) {
		if (tokens == null) return null;
		String[] commandTokens = command.getSpecification().split(" ");
		
		int i = checkMainCommand(tokens, commandTokens);
		if (i == -1) return null;

		HashMap<String, CommandSpecificationToken> commandTokensMap = createGroupsMap(commandTokens, i);
		
		HashMap<String, String> result = new HashMap<>();
		while (i < tokens.length) {
			int forwardSteps = parseSingleArgument(tokens, i, commandTokensMap, result);
			if (forwardSteps == -1) return null;
			i += forwardSteps;
		}
		assert(i == tokens.length);	// TODO: remove this line
		if (!checkRequiredArguments(commandTokensMap)) {
			System.out.println("Parser Error: Please specify all required fields");
			return null;
		}
		return result;
	}

	private static int checkMainCommand(String[] tokens, String[] commandTokens) {
		int i = 0;
		for (; i < commandTokens.length && "[<".indexOf(commandTokens[i].charAt(0)) == -1; i++)
			if (i >= tokens.length || !commandTokens[i].equals(tokens[i]))
				return -1;
		return i;
	}

	private static HashMap<String, CommandSpecificationToken> createGroupsMap(String[] commandTokens, int startIndex) {
		HashMap<String, CommandSpecificationToken> commandTokensMap = new HashMap<>();
		for (int j = startIndex; j < commandTokens.length; j++) {
			CommandSpecificationToken token = new CommandSpecificationToken(commandTokens[j]);
			commandTokensMap.put(token.getSwitchName(), token);
		}
		return commandTokensMap;
	}

	private static int parseSingleArgument(String[] tokens, int i,
	HashMap<String, CommandSpecificationToken> commandTokensMap, HashMap<String, String> result) {
		CommandSpecificationToken token = commandTokensMap.get(tokens[i]);
		if (token == null) return -1;
		commandTokensMap.remove(tokens[i]);
		if (!token.hasArgument()) {
			result.put(token.getGroupName(), token.getGroupName());
			return 1;
		}
		else if (token.getDefaultValue() != null &&
		(i + 1 >= tokens.length || !FormatValidation.isNumber(tokens[i + 1]))) {
			result.put(token.getGroupName(), token.getDefaultValue());
			return 1;
		}
		if (i + 1 >= tokens.length) return -1;
		if (token.isNumeric() && !FormatValidation.isNumber(tokens[i + 1])) return -1;
		result.put(token.getGroupName(), tokens[i + 1]);
		return 2;
	}

	private static boolean checkRequiredArguments(HashMap<String, CommandSpecificationToken> commandTokensMap) {
		for (String key : commandTokensMap.keySet())
			if (!commandTokensMap.get(key).isOptional())
				return false;
		return true;
	}
}
