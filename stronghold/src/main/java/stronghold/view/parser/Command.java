package stronghold.view.parser;

public enum Command {
	EXIT("exit"),
	SIGNUP("signup [-u,username] [-p,password] [-c,passwordConfirm]? [-e,email] [-n,nickname] [-s,slogan]?"),
	;

	private final String specification;

	private Command(String specification) {
		this.specification = specification;
	}

	public String getSpecification() {
		return specification;
	}
}
