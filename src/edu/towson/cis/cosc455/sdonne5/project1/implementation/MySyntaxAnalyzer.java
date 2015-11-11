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
			parse();
			variableDefine();
			head();
			body();
			if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.DOCB)){
				parse();
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
			parse();
			title();
			if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.HEAD)){
				parse();
			}
			else throw new SyntaxException("Missing a closing head token: ^");
		}

	}

	public void title() throws CompilerException, SyntaxException, LexicalException {
		//TITLEB TEXT TITLEE | null
		if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.TITLEB)){
			parse();
			text();
			if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.TITLEE)){
				parse();
			}
			else throw new SyntaxException("Missing a closing title token: >");
		}
		else if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.TITLEE))
			throw new SyntaxException("Missing the starting title token: <");

	}

	public void body() throws CompilerException, LexicalException, SyntaxException {
/*<inner-text> <body>
| <paragraph> <body>
| <newline> <body>
| null */
		if(startOfInnerText()){//TODO: Figure out differentiation method
			innerText();
			body();
		}
		else if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.PARAB)){
			paragraph();
			body();
		}
		else if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.NEWLINE)){
			newline();
			body();
		}

	}
	
	public boolean startOfInnerText(){
		if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.USEB) |
				MyCompiler.currentToken.equalsIgnoreCase(Tokens.BOLD) |
				MyCompiler.currentToken.equalsIgnoreCase(Tokens.ITALICS) |
				MyCompiler.currentToken.equalsIgnoreCase(Tokens.LISTITEMB) |
				MyCompiler.currentToken.equalsIgnoreCase(Tokens.AUDIO) |
				MyCompiler.currentToken.equalsIgnoreCase(Tokens.VIDEO) |
				MyCompiler.currentToken.equalsIgnoreCase(Tokens.LINKB) |
				MyCompiler.currentToken.equalsIgnoreCase(Tokens.NEWLINE) 
				/*or if text*/){
			return true;
		}
		else return false;
	}

	public void paragraph() throws CompilerException, LexicalException, SyntaxException {
		//PARAB <variable-define> <inner-text> PARAE
		if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.PARAB)){
			parse();
			variableDefine();
			innerText();

			if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.PARAE)){
				parse();
			}
			else throw new SyntaxException("Missing a closing paragraph token: }");
			
		}
		else if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.PARAE))
			throw new SyntaxException("Missing the starting paragraph token: {");
	}

	public void innerText() throws CompilerException, LexicalException, SyntaxException {
		if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.USEB)){
			parse();
			innerText();
		}
		else if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.BOLD)){
			parse();
			innerText();
		}
		else if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.ITALICS)){
			parse();
			innerText();
		}
		else if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.LISTITEMB)){
			parse();
			innerText();
		}
		else if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.AUDIO)){
			parse();
			innerText();
		}
		else if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.VIDEO)){
			parse();
			innerText();
		}
		else if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.LINKB)){
			parse();
			innerText();
		}
		else if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.NEWLINE)){
			newline();
		}
		//else if text() innerText()

	}

	public void variableDefine() throws CompilerException, LexicalException, SyntaxException {
		while(MyCompiler.currentToken.equalsIgnoreCase(Tokens.DEFB)){
			parse();
			text();
			if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.EQSIGN)){
				parse();
			}
			text();
			if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.DEFUSEE)){
				parse();
			}
			else throw new SyntaxException("File is missing ending token for the definition: $END");
			
		}
		
	}

	public void variableUse() throws CompilerException, LexicalException, SyntaxException  {
		if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.USEB)){
			parse();
			text();
			if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.DEFUSEE)){
				parse();
			}
			else throw new SyntaxException("Missing the ending token for calling a variable: $END");
		}
	}

	public void bold() throws CompilerException, LexicalException, SyntaxException {
		if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.BOLD)){
			parse();
			text();
			if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.BOLD)){
				parse();
			}
			else throw new SyntaxException("Missing the ending token for a bold section: **");
		}
	}

	public void italics() throws CompilerException, LexicalException, SyntaxException {
		if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.ITALICS)){
			parse();
			text();
			if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.ITALICS)){
				parse();
			}
			else throw new SyntaxException("Missing the ending token for an italics section: *");
		}
	}

	public void listitem() throws CompilerException, LexicalException, SyntaxException {
		while(MyCompiler.currentToken.equalsIgnoreCase(Tokens.LISTITEMB)){
			parse();
			innerItem();
			if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.LISTITEME)){
				parse();
			}
			else throw new SyntaxException("Missing the ending token for an italics section: *");
		}
	}

	public void innerItem() throws CompilerException, LexicalException, SyntaxException {
		if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.USEB)){
			variableUse();
			innerItem();			
		}
		else if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.BOLD)){
			bold();
			innerItem();			
		}
		else if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.ITALICS)){
			italics();
			innerItem();			
		}
		else if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.LINKB)){
			bold();
			innerItem();			
		}
		//else if TEXT <inner-item>
	}
	//TODO: create general link that can be a hyperlink, audio, or video
	public void link() throws CompilerException, SyntaxException, LexicalException {
		if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.LINKB)){
			parse();
			text();
			if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.LINKE)){
				parse();
				if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.ADDRESSB)){
					parse();
					text();
					if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.ADDRESSE)){
						parse();
					}
					else throw new SyntaxException("Missing ending token for address: )");
				}
			}
			else throw new SyntaxException("Missing ending token for link: ]");
		}

	}

	public void audio() throws CompilerException, LexicalException, SyntaxException {
		
		if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.AUDIO)){
			parse();
			if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.ADDRESSB)){
				parse();
				text();
				if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.ADDRESSE)){
					parse();
				}
			}
		}
		
	}

	public void video() throws CompilerException, LexicalException, SyntaxException {
		if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.VIDEO)){
			parse();
			if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.ADDRESSB)){
				parse();
				text();
				if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.ADDRESSE)){
					parse();
				}
			}
		}
	}

	public void newline() throws CompilerException, LexicalException, SyntaxException {
		if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.NEWLINE)){
			parse();
		}
	}
	
	//TODO: Handle determining what is text
	public void text() throws CompilerException {
		
	}
	
	/**
	 * Adds the currentToken to the parse tree and retrieves the next token 
	 * from MyLexicalAnalyzer
	 * 
	 * @throws LexicalException
	 */
	public void parse() throws LexicalException{
		//add token to parse tree
		myLA.getNextToken();
	}

}
