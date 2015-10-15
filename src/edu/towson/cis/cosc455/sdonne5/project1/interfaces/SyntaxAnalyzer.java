
package edu.towson.cis.cosc455.sdonne5.project1.interfaces;

import edu.towson.cis.cosc455.sdonne5.project1.implementation.CompilerException;

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
	 */
	void markdown() throws CompilerException;

	/**
	 * This method implements the BNF grammar rule for the head annotation.
	 * @throws CompilerException
	 */
	void head() throws CompilerException;

	/**
	 * This method implements the BNF grammar rule for the title annotation.
	 * @throws CompilerException

	 */
	void title() throws CompilerException;

	/**
	 * This method implements the BNF grammar rule for the body annotation.
	 * @throws CompilerException
	 */
	void body() throws CompilerException;
	
	/**
	 * This method implements the BNF grammar rule for the paragraph annotation.
	 * @throws CompilerException
	 */
	void paragraph() throws CompilerException;

	/**
	 * This method implements the BNF grammar rule for the inner-text annotation.
	 * @throws CompilerException
	 */
	void innerText() throws CompilerException;
	
	/**
	 * This method implements the BNF grammar rule for the variable-define annotation.
	 * @throws CompilerException
	 */
	void variableDefine() throws CompilerException;
	
	/**
	 * This method implements the BNF grammar rule for the variable-use annotation.
	 * @throws CompilerException
	 */
	void variableUse() throws CompilerException;
	
	/**
	 * This method implements the BNF grammar rule for the bold annotation.
	 * @throws CompilerException
	 */
	void bold() throws CompilerException;

	/**
	 * This method implements the BNF grammar rule for the italics annotation.
	 * @throws CompilerException
	 */
	void italics() throws CompilerException;

	/**
	 * This method implements the BNF grammar rule for the listitem annotation.
	 * @throws CompilerException
	 */
	void listitem() throws CompilerException;
	
	/**
	 * This method implements the BNF grammar rule for the inner-item annotation.
	 * @throws CompilerException
	 */
	void innerItem() throws CompilerException;

	/**
	 * This method implements the BNF grammar rule for the link annotation.
	 * @throws CompilerException
	 */
	void link() throws CompilerException;

	/**
	 * This method implements the BNF grammar rule for the audio annotation.
	 * @throws CompilerException
	 */
	void audio() throws CompilerException;

	/**
	 * This method implements the BNF grammar rule for the video annotation.
	 * @throws CompilerException
	 */
	void video() throws CompilerException;

	/**
	* This method implements the BNF grammar rule for the newline annotation.
	* @throws CompilerException
	*/
	void newline() throws CompilerException;
	
}
