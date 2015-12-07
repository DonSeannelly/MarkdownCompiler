/**
 * 
 */
package edu.towson.cis.cosc455.sdonne5.project1.implementation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.towson.cis.cosc455.sdonne5.project1.interfaces.LexicalAnalyzer;

/**
 * @author sdonne5
 *
 */
public class MyLexicalAnalyzer implements LexicalAnalyzer {

    public String currentToken = "";
    
    public int currentPosition = 0;
    
    public char[] fileCharArray;
    
	public MyLexicalAnalyzer(File file){
		String str = "";
		try {
			str = new Scanner( file ).useDelimiter("\\A").next();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		fileCharArray = str.toCharArray();
	}
	
	/**
	 * This is the public method to be called when the Syntax Analyzer needs a new
	 * token to be parsed.
	 * @return the token as a String
	 * @throws LexicalException 
	 */
	public String getNextToken() throws LexicalException {
		currentToken = "";
		boolean noTokenFound = true;
		
		while(noTokenFound){
			char character = getCharacter();
			//If the character = the start of a token
			if(character == '#' | character == '^' | character == '<' | character == '>' |
					 character == '{' | character == '}' | character == '$'
					 | character == '=' | character == '*' | character == '+'
					 | character == ';' | character == '~' | character == '['
					 | character == ']' | character == '@' | character == '%'
					 | character == '(' | character == ')'){
				
				//flip the flag to exit loop, there was a token found
				noTokenFound=false;
				
				if(character == '#' | character == '$'){
					//get character until space
					addCharacter(character);//adds trigger input to token
					character = getCharacter();
					while(!isSpace(character)){
						addCharacter(character);
						character = getCharacter();
					}
					if(lookupToken()){
						return currentToken;
					}
					else {
						throw new LexicalException(currentToken);
					}
				}
				else if(character == '*'){
					//Look ahead to check if italics
					if(fileCharArray[currentPosition+1] == '*'){
						addCharacter(fileCharArray[currentPosition+1]);
						currentPosition+=1;
					}
					//else just bold
				}
				else
					return Character.toString(character);
			}
		}
		throw new LexicalException();
		
	}


	/**
	 * This method gets the next character from the input and places it in
	 * the nextCharacter class variable.
	 * @return 
	 *
	 * @return the character
	 */
	public char getCharacter() {
		//interface instruction is unclear, should I return or add to class variable?
		//TODO: add error throw instead of returning a space
		if(currentPosition<fileCharArray.length){
			currentPosition+=1;
			return fileCharArray[currentPosition-1];
		}
		return ' ';
	}

	/**
     * This method adds the current character to the nextToken.
     * 
     * @param c the character to add
     */
	public void addCharacter(char c) {
		currentToken += c;
	}

	/**
	 * This is method gets the next character from the input and places it in
	 * the nextCharacter class variable.
	 *
	 * @param c the current character
	 * @return true, if is space; otherwise false
	 */
	public boolean isSpace(char c) {
		if(c == ' ' | c == '\r'){
			return true;
		}
		return false;
	}

	/**
	 * This method checks to see if the current, possible token is legal in the
	 * defined grammar.
	 *
	 * @return true, if it is a legal token, otherwise false
	 */
	public boolean lookupToken() {
		if (Tokens.isToken(currentToken)){
			return true;
		}		
		return false;
	}



}
