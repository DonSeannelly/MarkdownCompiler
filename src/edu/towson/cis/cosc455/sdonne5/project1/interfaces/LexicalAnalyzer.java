package edu.towson.cis.cosc455.sdonne5.project1.interfaces;

import edu.towson.cis.cosc455.sdonne5.project1.implementation.LexicalException;

/**
 * COSC 455 Programming Languages: Implementation and Design.
 *
 * A Simple Lexical Analyzer adapted from Sebesta (2010) by Josh Dehlinger,
 * modified by Adam Conover (2012) and interfaced by Josh Dehlinger (2013)
 *
 * Note that these are not the only methods necessary to implement the  
 * lexical analyzer. You will likely need to add new methods to your implementaion
 * of this interface. 
 * 
 */
 public interface LexicalAnalyzer {

    /** The next character. */
    String nextCharacter = "";

    /** The current position. */
    int currentPosition = 0;

	/**
	 * This is the public method to be called when the Syntax Analyzer needs a new
	 * token to be parsed.
	 * @return the token as a String
	 * @throws LexicalException 
	 */
	public String getNextToken() throws LexicalException;

	/**
	 * This is method gets the next character from the input and places it in
	 * the nextCharacter class variable.
	 *
	 * @return the character
	 */
	char getCharacter();

	 /**
     * This method adds the current character the nextToken.
     * 
     * @param c the character to add
     */
	void addCharacter(char c);

	/**
	 * This is method gets the next character from the input and places it in
	 * the nextCharacter class variable.
	 *
	 * @param c the current character
	 * @return true if the passed character is a space; otherwise false
	 */
	boolean isSpace(char c);

	/**
	 * This method checks to see if the current, possible token is legal in the
	 * defined grammar.
	 *
	 * @return true, if it is a legal token, otherwise false
	 */
	boolean lookupToken();
}
