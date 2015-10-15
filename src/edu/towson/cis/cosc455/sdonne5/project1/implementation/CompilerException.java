
package edu.towson.cis.cosc455.sdonne5.project1.implementation;

/**
 * Parsing exception. For simplicity, this is used for both lexical errors as
 * well as parsing errors. A more complete design would include a hierarchy of
 * specific error types.
 *
 * Developed by Adam Conover (2012) and modified by Josh Dehlinger (2013), used with permission.
 *
 */
public class CompilerException extends Exception {

    /**
     * Instantiates a new CompilerException.
     *
     * @param errorMessage the error message to be printed
     */
    public CompilerException(String errorMessage) {
        super(errorMessage);
    }

    /**
     * Gets the error message.
     *
     * @return the error message
     */
    public String getErrorMessage() {
        return super.getMessage();
    }
}
