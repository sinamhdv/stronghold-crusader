package stronghold.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class Cryptography {
	public static String hashPassword(String password) {
		return DigestUtils.sha256Hex(password);
	}
}
