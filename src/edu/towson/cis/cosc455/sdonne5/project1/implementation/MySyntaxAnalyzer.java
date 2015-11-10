package edu.towson.cis.cosc455.sdonne5.project1.implementation;

import edu.towson.cis.cosc455.sdonne5.project1.interfaces.SyntaxAnalyzer;

public class MySyntaxAnalyzer implements SyntaxAnalyzer {
	
	private MyLexicalAnalyzer myLA;
	
	public MySyntaxAnalyzer(MyLexicalAnalyzer myLA){
		this.myLA = myLA;
	}
	
	public void markdown() throws CompilerException, LexicalException, SyntaxException {
		//DOCB <variable-define> <head> <body> DOCE
		if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.DOCB)){
			//add to parse tree
			myLA.getNextToken();
			variableDefine();
			head();
			body();
			if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.DOCB)){
				//add to parse tree
				myLA.getNextToken();
			}
			else throw new CompilerException("File is missing an ending token: #END");
		}
		else {
			throw new CompilerException("File does not start with the starting token: #BEGIN");
		}
	}

	public void head() throws CompilerException, LexicalException, SyntaxException {
		//HEAD <title> HEAD | null
		if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.HEAD)){
			//add to parse tree
			myLA.getNextToken();
			title();
			if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.HEAD)){
				//add to parse tree
				myLA.getNextToken();
			}
			else throw new SyntaxException("Missing a closing head token: ^");
		}

	}

	public void title() throws CompilerException, SyntaxException, LexicalException {
		//TITLEB TEXT TITLEE | null
		if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.TITLEB)){
			//add to parse tree
			myLA.getNextToken();
			text();
			if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.TITLEE)){
				//add to parse tree
				myLA.getNextToken();
			}
			else throw new SyntaxException("Missing a closing title token: >");
		}

	}

	public void body() throws CompilerException {
/*<inner-text> <body>
| <paragraph> <body>
| <newline> <body>
| null */

	}

	public void paragraph() throws CompilerException {
		//PARAB <variable-define> <inner-text> PARAE
	}

	public void innerText() throws CompilerException {

	}

	public void variableDefine() throws CompilerException, LexicalException, SyntaxException {
		while(MyCompiler.currentToken.equalsIgnoreCase(Tokens.DEFB)){
			//add to parse tree
			text();
			eqsign();
			text();
			if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.DEFUSEE)){
				//add to parse tree
				myLA.getNextToken();
			}
			else throw new SyntaxException("File is missing ending token for the definition: $END");
			
		}
		
	}
	
	public void eqsign() throws LexicalException {
		if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.EQSIGN)){
			//add to parse tree
			myLA.getNextToken();
		}
	}

	public void variableUse() throws CompilerException {

	}

	public void bold() throws CompilerException {

	}

	public void italics() throws CompilerException {

	}

	public void listitem() throws CompilerException {

	}

	public void innerItem() throws CompilerException {

	}

	public void link() throws CompilerException {

	}

	public void audio() throws CompilerException {

	}

	public void video() throws CompilerException {

	}

	public void newline() throws CompilerException {
		if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.NEWLINE)){
			//do stuff
		}
			
		else {
			//error stuff
		}
	}
	
	//Not sure if needed/how to handle
	public void text() throws CompilerException {
		
	}

}
