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
	}
}
