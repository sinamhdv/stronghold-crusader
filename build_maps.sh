#!/bin/bash
# a script to test the program with input text files in testcases folder
# also used to build maps automatically

# The run_command in this script must be updated with VSCode running command each time the project is built

run_command='/usr/bin/env /usr/lib/jvm/java-19-openjdk-amd64/bin/java @/tmp/cp_1q5g9b3oxxxwqlhcic6xq60m3.argfile -m stronghold/stronghold.view.LoginMenu'
inputs_count=3

RED='\033[31m'
GREEN='\033[32m'
YELLOW='\033[33m'
RESET='\033[0m'

# rm -i stronghold/src/main/database/users.json

for ((i=1;i<=$inputs_count;i++)); do
	echo -n "Running testcase #$i (`tail -1 testcases/input$i.txt`): "
	$run_command < testcases/input$i.txt > testcases/myoutput$i.txt 2>&1
	if [ $? -ne 0 ]; then
		echo -e $RED'RUNTIME ERROR'$RESET
	elif [ -f "testcases/output$i.txt" ]; then
		diff -Bbq testcases/myoutput$i.txt testcases/output$i.txt > /dev/null 2>&1
		if [ $? -eq 0 ]; then
			echo -e $GREEN'OK'$RESET
		else
			echo -e $RED'WRONG OUTPUT'$RESET
		fi
	else
		echo -e $GREEN'OK'$RESET
	fi
done
