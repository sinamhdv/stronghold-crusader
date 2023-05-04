#!/bin/bash
# a script to test the program with input text files in testcases folder

run_command='/usr/bin/env /usr/lib/jvm/java-19-openjdk-amd64/bin/java @/tmp/cp_2l06s9ma3j6g78ajfi0yk4l92.argfile stronghold.Main'
inputs_count=1

for ((i=1;i<=$inputs_count;i++)); do
	echo -n "Running testcase #$i: "
	$run_command < testcases/input$i.txt > testcases/myoutput$i.txt
	if [ -f "testcases/output$i.txt" ]; then
		diff -Bbq testcases/myoutput$i.txt testcases/output$i.txt > /dev/null 2>&1
		if [ $? -eq 0 ]; then
			echo -e '\033[32mOK\033[0m'
		else
			echo -e '\033[31mWRONG\033[0m'
		fi
	else
		echo -e '\033[33mNO OUTPUT FILE\033[0m'
	fi
done
