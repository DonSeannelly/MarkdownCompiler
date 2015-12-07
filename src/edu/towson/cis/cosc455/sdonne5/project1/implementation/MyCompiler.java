package edu.towson.cis.cosc455.sdonne5.project1.implementation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class MyCompiler {

	public static String currentToken = "";
	private static MyLexicalAnalyzer myLA;
	private static MySyntaxAnalyzer mySA;
	
	public static void main(String[] args) throws FileNotFoundException, LexicalException, CompilerException, SyntaxException {
		//Verifies the .mkd extension
		if(!args[0].substring(args[0].length()-4, args[0].length()).equalsIgnoreCase(".mkd")){
			throw new CompilerException("The source file is not of .mkd extension");
		}
		//Takes in the runtime parameter to use for the file path
		File file = new File(args[0]);
		System.out.println(args[0]);
		//Gets priming imput from the lexical analyzer
		myLA = new MyLexicalAnalyzer(file);
		fetchToken();
		//starts the syntax analyzer
		mySA = new MySyntaxAnalyzer(myLA);
		//printTokens(5);
		mySA.markdown();
		//produces the final file
		produceFile(args[0]);
	}
	/**
	 * Produces a .html file from the finished product of the semantic analyzer
	 * and places the file in the same folder as the .mkd sent to compile
	 * @param s is the file path to the .mkd file provided
	 */
	public static void produceFile(String s){
		try {
			//removes the mkd from the file extension and replaces it with html
			File file = new File(s.substring(0, s.length()-3) + "html");
			//writes out the file
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(mySA.getHTML());
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Retrieves the next token from the myLA object
	 * @throws LexicalException
	 */
	public static void fetchToken() throws LexicalException{
		currentToken = myLA.getNextToken();
	}

}
