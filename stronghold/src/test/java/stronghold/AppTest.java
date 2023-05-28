package stronghold;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import stronghold.controller.SignupMenuController;
import stronghold.controller.messages.SignupAndProfileMenuMessage;
import stronghold.model.StrongHold;
import stronghold.model.User;

public class AppTest {
	@Test
	public void shouldAnswerWithTrue() {
		assertTrue(true);
	}

	@Test
	public void testSignUpError() {
		SignupAndProfileMenuMessage erorr = SignupMenuController.signup("", "", "lk4ld", "hdfyh@hghd.com", "");
		assertEquals(erorr, SignupAndProfileMenuMessage.EMPTY_FIELD);
	}

	@Test
	public void testSignCheckError() {
		SignupAndProfileMenuMessage error = SignupMenuController.signUpFactorsError("gcdyuhg", "eywy",
				"Mobina_Kochaknia34", "mobina.kochaknia@yahho.com", "hiiii");
		assertNull(error);
	}

	@Test
	public void testSignUpError2() {
		SignupAndProfileMenuMessage error = SignupMenuController.signup("_)(*&*)", "eywy",
				"Mobina_kochaknia34", "mobina.kochaknia@yahho.com", "hiiii");
		assertEquals(SignupAndProfileMenuMessage.INVALID_USERNAME, error);
	}

	@Test
	public void testSignUpError3() {
		StrongHold.getUsers().add(new User("gcdyuhg", "eywy",
				"Mobina_Kochaknia34", "Mobina_Kochaknia34", "mobina.kochaknia@yahho.com", 0, 1, "hiiii"));
		SignupAndProfileMenuMessage error = SignupMenuController.signUpFactorsError("gcdyuhg", "eywy",
				"Mobina_Kochaknia34", "mobina.kochaknia@yahho.com", "hiiii");
		assertEquals(SignupAndProfileMenuMessage.USERNAME_EXIST, error);
	}

	@Test
	public void testSignUpError4() {
		SignupAndProfileMenuMessage error = SignupMenuController.signup("gcdyuhg", "eywy",
				"Mo", "mobina.kochaknia@yahho.com", "hiiii");
		assertEquals(SignupAndProfileMenuMessage.PASSWORD_IS_SHORT, error);
	}

	@Test
	public void testSignUpError5() {
		SignupAndProfileMenuMessage error = SignupMenuController.signup("gcdyuhg", "eywy",
				"efwretwegtse", "mobina.kochaknia@yahho.com", "hiiii");
		assertEquals(SignupAndProfileMenuMessage.PASSWORD_DOSNT_HAVE_UPPERCASE, error);
	}

	@Test
	public void testSignUpError6() {
		SignupAndProfileMenuMessage error = SignupMenuController.signup("gcdyuhg", "eywy",
				"HGVGHFYFY", "mobina.kochaknia@yahho.com", "hiiii");
		assertEquals(SignupAndProfileMenuMessage.PASSWORD_DOSNT_HAVE_LOWERCASE, error);
	}

	@Test
	public void testSignUpError7() {
		SignupAndProfileMenuMessage error = SignupMenuController.signup("gcdyuhg", "eywy",
				"HGVGHFYFYjj", "mobina.kochaknia@yahho.com", "hiiii");
		assertEquals(SignupAndProfileMenuMessage.PASSWORD_DOSNET_HAVE_NUMBER, error);
	}

	@Test
	public void testSignUpError8() {
		SignupAndProfileMenuMessage error = SignupMenuController.signup("gcdyuhg", "eywy",
				"HGxVG66HFYFY", "mobina.kochaknia@yahho.com", "hiiii");
		assertEquals(SignupAndProfileMenuMessage.PASSWORD_DOSENT_HAVE_OTHER_CHAR, error);
	}

	@Test
	public void testSignUpError11() {
		SignupAndProfileMenuMessage error = SignupMenuController.signup("gcdyuhg", "eywy",
				"HGVG66__fcgHFYFY", "mobina.kochakniayahho.com", "hiiii");
		assertEquals(SignupAndProfileMenuMessage.INVALID_EMAIL, error);
	}

	@Test
	public void testSignUpError12() {
		StrongHold.getUsers().add(new User("gcdyuhg", "eywy",
				"Mobina_Kochaknia34", "Mobina_Kochaknia34", "mobina.kochaknia@yahho.com", 0, 1, "hiiii"));
		SignupAndProfileMenuMessage error = SignupMenuController.signup("dasdas", "faad", "wSdd77*das",
				"mobina.kochaknia@yahho.com", "hjf");
		assertEquals(SignupAndProfileMenuMessage.EMAIL_EXIST, error);
	}
}
