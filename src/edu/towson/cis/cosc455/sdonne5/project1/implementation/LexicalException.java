package edu.towson.cis.cosc455.sdonne5.project1.implementation;

public class LexicalException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;//What is this?

	public LexicalException(){
		super("There are no tokens in the file.");
	}
	public LexicalException(String token){
		super(token + " is not a valid token.");
	}

}
