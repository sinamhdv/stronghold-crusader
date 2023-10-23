#!/bin/bash
# a script to test the program with input text files in testcases folder

# run_command should be updated with VSCode run command after building the project

run_command='/usr/bin/env /usr/lib/jvm/java-19-openjdk-amd64/bin/java @/tmp/cp_2l06s9ma3j6g78ajfi0yk4l92.argfile stronghold.Main'
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
