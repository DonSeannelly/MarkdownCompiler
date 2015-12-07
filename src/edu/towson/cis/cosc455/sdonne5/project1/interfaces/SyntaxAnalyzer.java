
package edu.towson.cis.cosc455.sdonne5.project1.interfaces;

import edu.towson.cis.cosc455.sdonne5.project1.implementation.CompilerException;
import edu.towson.cis.cosc455.sdonne5.project1.implementation.LexicalException;
import edu.towson.cis.cosc455.sdonne5.project1.implementation.SyntaxException;

/**
 * COSC 455 Programming Languages: Implementation and Design.
 *
 * A Simple Syntax Analyzer adapted from Sebesta (2010) by Josh Dehlinger,
 * modified by Adam Conover (2012) and interfaced by Josh Dehlinger (2013).
 *
 * Note that these are not the only methods necessary to parse the BNF
 * grammar rules. You will likely need to add new methods to your implementaion
 * of this interface.
 *
 */
public interface SyntaxAnalyzer {

	/**
	 * This method implements the BNF grammar rule for the document annotation.
	 * @throws CompilerException
	 * @throws LexicalException 
	 * @throws SyntaxException 
	 */
	void markdown() throws CompilerException, LexicalException, SyntaxException;

	/**
	 * This method implements the BNF grammar rule for the head annotation.
	 * @throws CompilerException
	 * @throws LexicalException 
	 * @throws SyntaxException 
	 */
	void head() throws CompilerException, LexicalException, SyntaxException;

	/**
	 * This method implements the BNF grammar rule for the title annotation.
	 * @throws CompilerException
	 * @throws SyntaxException 
	 * @throws LexicalException 

	 */
	void title() throws CompilerException, SyntaxException, LexicalException;

	/**
	 * This method implements the BNF grammar rule for the body annotation.
	 * @throws CompilerException
	 * @throws SyntaxException 
	 * @throws LexicalException 
	 */
	void body() throws CompilerException, LexicalException, SyntaxException;
	
	/**
	 * This method implements the BNF grammar rule for the paragraph annotation.
	 * @throws CompilerException
	 * @throws SyntaxException 
	 * @throws LexicalException 
	 */
	void paragraph() throws CompilerException, LexicalException, SyntaxException;

	/**
	 * This method implements the BNF grammar rule for the inner-text annotation.
	 * @throws CompilerException
	 * @throws SyntaxException 
	 * @throws LexicalException 
	 */
	void innerText() throws CompilerException, LexicalException, SyntaxException;
	
	/**
	 * This method implements the BNF grammar rule for the variable-define annotation.
	 * @throws CompilerException
	 * @throws LexicalException 
	 * @throws SyntaxException 
	 */
	void variableDefine() throws CompilerException, LexicalException, SyntaxException;
	
	/**
	 * This method implements the BNF grammar rule for the variable-use annotation.
	 * @throws CompilerException
	 * @throws SyntaxException 
	 * @throws LexicalException 
	 */
	void variableUse() throws CompilerException, LexicalException, SyntaxException;
	
	/**
	 * This method implements the BNF grammar rule for the bold annotation.
	 * @throws CompilerException
	 * @throws SyntaxException 
	 * @throws LexicalException 
	 */
	void bold() throws CompilerException, LexicalException, SyntaxException;

	/**
	 * This method implements the BNF grammar rule for the italics annotation.
	 * @throws CompilerException
	 * @throws SyntaxException 
	 * @throws LexicalException 
	 */
	void italics() throws CompilerException, LexicalException, SyntaxException;

	/**
	 * This method implements the BNF grammar rule for the listitem annotation.
	 * @throws CompilerException
	 * @throws SyntaxException 
	 * @throws LexicalException 
	 */
	void listitem() throws CompilerException, LexicalException, SyntaxException;
	
	/**
	 * This method implements the BNF grammar rule for the inner-item annotation.
	 * @throws CompilerException
	 * @throws SyntaxException 
	 * @throws LexicalException 
	 */
	void innerItem() throws CompilerException, LexicalException, SyntaxException;

	/**
	 * This method implements the BNF grammar rule for the link annotation.
	 * @throws CompilerException
	 * @throws SyntaxException 
	 * @throws LexicalException 
	 */
	void link() throws CompilerException, SyntaxException, LexicalException;

	/**
	 * This method implements the BNF grammar rule for the audio annotation.
	 * @throws CompilerException
	 * @throws SyntaxException 
	 * @throws LexicalException 
	 */
	void audio() throws CompilerException, LexicalException, SyntaxException;

	/**
	 * This method implements the BNF grammar rule for the video annotation.
	 * @throws CompilerException
	 * @throws SyntaxException 
	 * @throws LexicalException 
	 */
	void video() throws CompilerException, LexicalException, SyntaxException;

	/**
	* This method implements the BNF grammar rule for the newline annotation.
	* @throws CompilerException
	 * @throws SyntaxException 
	 * @throws LexicalException 
	*/
	void newline() throws CompilerException, LexicalException, SyntaxException;
	
}
