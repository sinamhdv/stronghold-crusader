# Commands

This is a description of the commands used in the CLI of the project.
The notations used for describing each argument:

- [-u,username] => required argument
- [-s,slogan]? => optional argument
- [-d,down=1]? => optional numeric argument with default value of 1
	- The default value is used only if the switch is present but the argument is not
- [--stay-logged-in,-]? => optional switch without any argument
	- If the switch is present, this is saved in the matcher with a key value of "--stay-logged-in" and a non-null value

## Signup Menu
- signup [-u,username] [-p,password] [-c,passwordConfirm]? [-e,email] [-n,nickname] [-s,slogan]?
