package edu.towson.cis.cosc455.sdonne5.project1.implementation;

import java.io.File;
import java.io.FileNotFoundException;

public class MyCompiler {

	public static String currentToken = "";
	private static MyLexicalAnalyzer myLA;
	
	public static void main(String[] args) throws FileNotFoundException, LexicalException {
		File file = new File(args[0]);
		myLA = new MyLexicalAnalyzer(file);
		
	}
	/**
	 * Retrieves the next token from the myLA object
	 * @throws LexicalException
	 */
	public void fetchToken() throws LexicalException{
		currentToken = myLA.getNextToken();
	}

}
