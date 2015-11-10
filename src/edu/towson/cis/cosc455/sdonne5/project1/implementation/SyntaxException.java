package edu.towson.cis.cosc455.sdonne5.project1.implementation;
//TODO: Add ability to pass the token and have this class determine what the error is
public class SyntaxException extends Exception {
    
	public SyntaxException(String errorMessage) {
        super(errorMessage);
    }
}
