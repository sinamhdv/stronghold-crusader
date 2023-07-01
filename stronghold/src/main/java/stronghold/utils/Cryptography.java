package stronghold.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class Cryptography {
	private static final String JWT_SECRET = "secret";

	public static String hashPassword(String password) {
		return DigestUtils.sha256Hex(password);
	}

	public static String generateJWT(String username) {
		String jsonData = "{\"username\":\"" + username + "\"}";
		String encodedData = Base64.encodeBase64String(jsonData.getBytes());
		String hash = hashPassword(encodedData + JWT_SECRET);
		return encodedData + "." + hash;
	}

	public static boolean verifyJWT(String jwt) {
		try {
			String[] splitToken = jwt.split("\\.");
			return (splitToken.length == 2 &&
				hashPassword(splitToken[0] + JWT_SECRET).equals(splitToken[1]));
		}
		catch (Exception ex) {
			return false;
		}
	}
}
