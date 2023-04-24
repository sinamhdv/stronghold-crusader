# Commands

This is a description of the commands used in the CLI of the project.
The notations used for describing each argument:

- [-u,username] => required argument
- [-s,slogan]? => optional argument
- [-d,down=1]? => optional numeric argument with default value of 1
	- The default value is used only if the switch is present but the argument is not
- [--stay-logged-in,-]? => optional switch without any argument
	- If the switch is present, this is saved in the matcher with a key value of "--stay-logged-in" and a non-null value

## Notes
If a required argument is not present, the parser will not add its key to the resulting matcher.
So, matcher.get(groupName) will be null. These errors must be handled in the controller.
Also, except for arguments with default values, the parser doesn't handle numeric and text arguments
differently so calling Integer.parseInt on a parsed token that is expected to be numeric might throw errors.
It must be explicitly ensured in the controller that these numeric arguments are actually numeric.

## Signup Menu
- signup [-u,username] [-p,password] [-c,passwordConfirm]? [-e,email] [-n,nickname] [-s,slogan]?
- question pick [-q,questionNumber] [-a,answer] [-c,answerConfirm]

## Login Menu
- login [-u,username] [-p,password] [--stay-logged-in,-]?
- forgot my password [-u,username]

## Main Menu
- logout

## Profile Menu
- profile change username [-u,username]
- profile change nickname [-n,nickname]
- profile change password [-o,oldPassword] [-n,newPassword]
- profile change email [-e,email]
- profile change slogan [-s,slogan]
- profile remove slogan
- profile display highscore
- profile display rank
- profile display slogan
- profile display

## Map Menu
- show map [-x,x] [-y,y]
- map [-u,up=1]? [-d,down=1]? [-r,right=1]? [-l,left=1]?
- show details [-x,x] [-y,y]

## Game Menu
- show popularity factors
- show popularity
- show food list
- food rate [-r,rate]
- show food rate
- tax rate [-r,rate]
- show tax rate
- fear rate [-r,rate]
- drop building [-x,x] [-y,y] [-t,type]
- select building [-x,x] [-y,y]
- create unit [-t,type] [-c,count]