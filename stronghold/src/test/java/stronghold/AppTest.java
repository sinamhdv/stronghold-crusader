package stronghold;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import stronghold.controller.SignupMenuController;
import stronghold.controller.messages.SignupAndProfileMenuMessage;

public class AppTest {
	@Test
	public void shouldAnswerWithTrue() {
		assertTrue(true);
	}

	@Test
	public void testSignUpError() {
		SignupAndProfileMenuMessage erorr = SignupMenuController.signup("", "", "lk4ld"
												, "lk4ld", "hdfyh@hghd.com", "");
		assertEquals(erorr, SignupAndProfileMenuMessage.EMPTY_FIELD);
	}

	@Test
	public void testSignCheckError() {
		SignupAndProfileMenuMessage error = SignupMenuController.signUpFactorsError("gcdyuhg", "eywy",
		 "Mobina_Kochaknia34", "Mobina_Kochaknia34", "mobina.kochaknia@yahho.com", "hiiii");
		 assertNull(error);
	}

	@Test
	public void testSignUpError2() {
		SignupAndProfileMenuMessage error = SignupMenuController.signUpFactorsError("_)(*&*)", "eywy",
		 "Mobina_Kochaknia34", "Mobina_kochaknia34", "mobina.kochaknia@yahho.com", "hiiii");
		 assertEquals(SignupAndProfileMenuMessage.INVALID_USERNAME, error);
	}

	@Test
	public void testSignUpError3() {
		SignupMenuController.signup("gcdyuhg", "eywy",
		"Mobina_Kochaknia34", "Mobina_Kochaknia34", "mobina.kochaknia@yahho.com", "hiiii");
		SignupAndProfileMenuMessage error = SignupMenuController.signUpFactorsError("gcdyuhg", "eywy",
		"Mobina_Kochaknia34", "Mobina_Kochaknia34", "mobina.kochaknia@yahho.com", "hiiii");
		assertEquals(SignupAndProfileMenuMessage.USERNAME_EXIST, error);
	}

	@Test
	public void testSignUpError4() {
	    SignupAndProfileMenuMessage error = SignupMenuController.signup("gcdyuhg", "eywy",
		"Mo", "Mobina_Kochaknia34", "mobina.kochaknia@yahho.com", "hiiii");
		assertEquals(SignupAndProfileMenuMessage.PASSWORD_IS_SHORT, error);
	}

	@Test 
	public void testSignUpError5() {
		SignupAndProfileMenuMessage error = SignupMenuController.signUpFactorsError("gcdyuhg", "eywy",
		"efwretwegtse", "Mobina_Kochaknia34", "mobina.kochaknia@yahho.com", "hiiii");
		assertEquals(SignupAndProfileMenuMessage.PASSWORD_DOSNT_HAVE_UPPERCASE, error);
	}

	@Test 
	public void testSignUpError6() {
		SignupAndProfileMenuMessage error = SignupMenuController.signUpFactorsError("gcdyuhg", "eywy",
		"HGVGHFYFY", "Mobina_Kochaknia34", "mobina.kochaknia@yahho.com", "hiiii");
		assertEquals(SignupAndProfileMenuMessage.PASSWORD_DOSNT_HAVE_LOWERCASE, error);
	}

	@Test 
	public void testSignUpError7() {
		SignupAndProfileMenuMessage error = SignupMenuController.signUpFactorsError("gcdyuhg", "eywy",
		"HGVGHFYFYjj", "Mobina_Kochaknia34", "mobina.kochaknia@yahho.com", "hiiii");
		assertEquals(SignupAndProfileMenuMessage.PASSWORD_DOSNET_HAVE_NUMBER, error);
	}

	@Test 
	public void testSignUpError8() {
		SignupAndProfileMenuMessage error = SignupMenuController.signUpFactorsError("gcdyuhg", "eywy",
		"HGxVG66HFYFY", "Mobina_Kochaknia34", "mobina.kochaknia@yahho.com", "hiiii");
		assertEquals(SignupAndProfileMenuMessage.PASSWORD_DOSENT_HAVE_OTHER_CHAR, error);
	}

	@Test 
	public void testSignUpError9() {
		SignupAndProfileMenuMessage error = SignupMenuController.signUpFactorsError("gcdyuhg", "eywy",
		"HGVGH888hb__hhFYFY", "Mobina_Kochaknia34", "mobina.kochaknia@yahho.com", "hiiii");
		assertEquals(SignupAndProfileMenuMessage.PASSWORD_CONFIRMATION_IS_NOT_TRUE, error);
	}

	@Test 
	public void testSignUpError10() {
		SignupAndProfileMenuMessage error = SignupMenuController.signUpFactorsError("gcdyuhg", "eywy",
		"random", "Mobina_Kochaknia34", "mobina.kochaknia@yahho.com", "hiiii");
		assertEquals(SignupAndProfileMenuMessage.RANDOM_PASSWORD_DESNT_HAVE_PASSWORDCONFIRMATION, error);
	}

	@Test 
	public void testSignUpError11() {
		SignupAndProfileMenuMessage error = SignupMenuController.signUpFactorsError("gcdyuhg", "eywy",
		"HGVG66__fcgHFYFY", "HGVG66__fcgHFYFY", "mobina.kochakniayahho.com", "hiiii");
		assertEquals(SignupAndProfileMenuMessage.INVALID_EMAIL, error);
	}

	@Test 
	public void testSignUpError12() {
		SignupMenuController.signUpFactorsError("gcdyuhg", "eywy",
		"HGVG66__fcgHFYFY", "HGVG66__fcgHFYFY", "mobina.kochakn@iayahho.com", "hiiii");
		SignupAndProfileMenuMessage error = SignupMenuController.signup("dasdas", "faad", "wSdd77*das"
											,"wSdd77*das" , "mobina.kochakn@iayahho.com", "hjf");
		assertEquals(SignupAndProfileMenuMessage.EMAIL_EXIST, error);
	}
	
}
