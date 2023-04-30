package stronghold.view;

import java.util.ArrayList;

public class Terminal2DPrinter {
	private ArrayList<String> outputArray = new ArrayList<>();
	private int currentLine = 0;

	public ArrayList<String> getOutputArray() {
		return outputArray;
	}

	public void addOutput(String[] output) {
		// add as many lines as necessary
		while (currentLine + output.length - 1 >= outputArray.size())
			outputArray.add("");

		// pad bottom lines to match the size of current line
		for (int i = 0; i < output.length; i++) {
			while (outputArray.get(currentLine + i).length() < outputArray.get(currentLine).length())
				outputArray.set(currentLine + i, outputArray.get(currentLine + i) + " ");
		}

		// add one line of output
		for (int i = 0; i < output.length; i++)
			outputArray.set(currentLine + i, outputArray.get(currentLine + i) + output[i]);
	}

	public void addNewLine() {
		outputArray.add("");
		currentLine = outputArray.size() - 1;
	}

	public void printOutput() {
		for (String line : outputArray)
			System.out.println(line);
	}
}
