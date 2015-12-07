package edu.towson.cis.cosc455.sdonne5.project1.implementation;

import edu.towson.cis.cosc455.sdonne5.project1.interfaces.SyntaxAnalyzer;

public class MySyntaxAnalyzer implements SyntaxAnalyzer {
	
	private MyLexicalAnalyzer myLA;
	private MySemanticAnalyzer mySM;
	private MyStack<String> stack;
	
	public MySyntaxAnalyzer(MyLexicalAnalyzer myLA){
		this.myLA = myLA;
		stack = new MyStack<String>();
		mySM = new MySemanticAnalyzer(stack);
	}
	/**
	 * Called upon completion of syntax and semantic analysis
	 * @return the html code produces by the semantic analyzer
	 */
	public String getHTML(){
		return mySM.convert();
	}
	/**
	 * This method implements the BNF grammar rule for the document annotation.
	 * @throws CompilerException
	 * @throws LexicalException 
	 * @throws SyntaxException 
	 */
	public void markdown() throws CompilerException, LexicalException, SyntaxException {
		if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.DOCB)){
			parse();
			variableDefine();
			//Not registering text before paragraph
			head();
			body();
			if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.DOCE)){
				//TODO: Revise "hack" solution here of directly calling for push
				stack.push(MyCompiler.currentToken);
			}
			else throw new CompilerException("File is missing an ending token: #END");
		}
		else {
			throw new CompilerException("File does not start with the starting token: #BEGIN");
		}
		System.out.println("DONE WITH THE MARKDOWN SYNTAX ANALYSIS");
	}
	/**
	 * This method implements the BNF grammar rule for the head annotation.
	 * @throws CompilerException
	 * @throws LexicalException 
	 * @throws SyntaxException 
	 */
	public void head() throws CompilerException, LexicalException, SyntaxException {
		//TODO: Fix. Starts with currentToken="" yet still enters and doesn't break until second head tag. IS THIS RESOLVED??
		if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.HEAD)){
			parse();
			title();
			if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.HEAD)){
				parse();
			}
			else throw new SyntaxException("Missing a closing head token: ^");
		}

	}
	/**
	 * This method implements the BNF grammar rule for the title annotation.
	 * @throws CompilerException
	 * @throws LexicalException 
	 * @throws SyntaxException 
	 */
	public void title() throws CompilerException, SyntaxException, LexicalException {
		if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.TITLEB)){
			text(false,true);
			if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.TITLEE)){
				parse();
			}
			else throw new SyntaxException("Missing a closing title token: >");
		}
		else if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.TITLEE))
			throw new SyntaxException("Missing the starting title token: <");

	}
	/**
	 * This method implements the BNF grammar rule for the body annotation.
	 * @throws CompilerException
	 * @throws LexicalException 
	 * @throws SyntaxException 
	 */
	public void body() throws CompilerException, LexicalException, SyntaxException {
		if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.PARAB)){
			paragraph();
			body();
		}
		else if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.PARAE)){
			throw new SyntaxException("Ending paragraph token \""+Tokens.PARAE+"\" placed before the starting token \""+Tokens.PARAB+"\"");
		}
		else if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.NEWLINE)){
			newline();
			body();
		}
		else if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.DOCE)){
			return;
		}
		else if(startOfInnerText()){
			//TODO: Figure out differentiation method
			innerText();
			body();
		}

	}
	/**
	 * Determines if the next token belongs to the category of inner text
	 * @return true if the token is inner text, false if it is not
	 */
	public boolean startOfInnerText(){
		if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.USEB) |
				MyCompiler.currentToken.equalsIgnoreCase(Tokens.BOLD) |
				MyCompiler.currentToken.equalsIgnoreCase(Tokens.ITALICS) |
				MyCompiler.currentToken.equalsIgnoreCase(Tokens.LISTITEMB) |
				MyCompiler.currentToken.equalsIgnoreCase(Tokens.AUDIO) |
				MyCompiler.currentToken.equalsIgnoreCase(Tokens.VIDEO) |
				MyCompiler.currentToken.equalsIgnoreCase(Tokens.LINKB) |
				MyCompiler.currentToken.equalsIgnoreCase(Tokens.NEWLINE) |
				isText()){
			return true;
		}
		else return false;
	}
	/**
	 * This method implements the BNF grammar rule for the paragraph annotation.
	 * @throws CompilerException
	 * @throws LexicalException 
	 * @throws SyntaxException 
	 */
	public void paragraph() throws CompilerException, LexicalException, SyntaxException {
		//PARAB <variable-define> <inner-text> PARAE
		if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.PARAB)){
			/*
			 * If there is text originally, then process that text before parsing PARAB
			 * If no text, parse and proceed to variableDefine()
			 */
			if(isText()){
				text(false,true);
			}
			else {
				parse();
			}
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
	/**
	 * This method implements the BNF grammar rule for the inner-text annotation.
	 * @throws CompilerException
	 * @throws LexicalException 
	 * @throws SyntaxException 
	 */
	public void innerText() throws CompilerException, LexicalException, SyntaxException {
		if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.USEB)){
			variableUse();
			innerText();
		}
		else if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.BOLD)){
			bold();
			innerText();
		}
		else if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.ITALICS)){
			italics();
			innerText();
		}
		else if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.LISTITEMB)){
			listitem();
			innerText();
		}
		else if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.AUDIO)){
			audio();
			innerText();
		}
		else if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.VIDEO)){
			video();
			innerText();
		}
		else if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.LINKB)){
			link();
			innerText();
		}
		else if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.NEWLINE)){
			newline();
		}
		else if(isText()){
			text(false,true);
			innerText();
		}

	}
	/**
	 * This method implements the BNF grammar rule for the variable-definition annotation.
	 * @throws CompilerException
	 * @throws LexicalException 
	 * @throws SyntaxException 
	 */
	public void variableDefine() throws CompilerException, LexicalException, SyntaxException {
		while(MyCompiler.currentToken.equalsIgnoreCase(Tokens.DEFB)){
			text(true,true);
			if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.EQSIGN)){//doubling EQSIGN
				
			}
			text(true,true);
			if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.DEFUSEE)){
				text(false,true);
			}
			else throw new SyntaxException("File is missing ending token for the definition: $END");
			
		}
		
	}
	/**
	 * This method implements the BNF grammar rule for the variable-use annotation.
	 * @throws CompilerException
	 * @throws LexicalException 
	 * @throws SyntaxException 
	 */
	public void variableUse() throws CompilerException, LexicalException, SyntaxException  {
		/*TODO: Use the variable's value, not the name
		 * Method to distinguish variable text and remove spaces to minimize error chances
		 * Send boolean to text to trigger removal of spaces or not
		 */
		if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.USEB)){
			text(true,true);//TODO: new text parameter eliminates need for removal of USEB during lookup()
			
			if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.DEFUSEE)){
				/*
				 * If the lookup succeeds we do not need to parse DEFUSEE, 
				 * advance to the next token if there is no text following DEFUSEE before the next token
				 */
				mySM.lookup();
				stack.push(" ");//hack solution to be removed
				text(false,false);
			}
			else throw new SyntaxException("Missing the ending token for calling a variable: $END");
		}
	}
	/**
	 * This method implements the BNF grammar rule for the bold annotation.
	 * @throws CompilerException
	 * @throws LexicalException 
	 * @throws SyntaxException 
	 */
	public void bold() throws CompilerException, LexicalException, SyntaxException {
		if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.BOLD)){
			parse();
			text(false,true);
			if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.BOLD)){
				parse();
			}
			else throw new SyntaxException("Missing the ending token for a bold section: **");
		}
	}
	/**
	 * This method implements the BNF grammar rule for the italics annotation.
	 * @throws CompilerException
	 * @throws LexicalException 
	 * @throws SyntaxException 
	 */
	public void italics() throws CompilerException, LexicalException, SyntaxException {
		if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.ITALICS)){
			parse();
			text(false,true);
			if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.ITALICS)){
				parse();
			}
			else throw new SyntaxException("Missing the ending token for an italics section: *");
		}
	}
	/**
	 * This method implements the BNF grammar rule for the list-item annotation.
	 * @throws CompilerException
	 * @throws LexicalException 
	 * @throws SyntaxException 
	 */
	public void listitem() throws CompilerException, LexicalException, SyntaxException {
		while(MyCompiler.currentToken.equalsIgnoreCase(Tokens.LISTITEMB)){
			innerItem();
			if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.LISTITEME)){
				parse();
			}
			else throw new SyntaxException("Missing the ending token for a list section: ;");
			newline();
		}
	}
	/**
	 * This method implements the BNF grammar rule for the inner-item annotation.
	 * @throws CompilerException
	 * @throws LexicalException 
	 * @throws SyntaxException 
	 */
	public void innerItem() throws CompilerException, LexicalException, SyntaxException {
		/*
		 * If first time through the method (ie: currentToken = LISTITEMB, then hold the parse until isText() check
		 */
		if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.LISTITEMB) && isText()){
			text(false,true);
			innerItem();
		}
		else {
			
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
		}
	}
	/**
	 * This method implements the BNF grammar rule for the link annotation.
	 * @throws CompilerException
	 * @throws LexicalException 
	 * @throws SyntaxException 
	 */
	//TODO: create general link that can be a hyperlink, audio, or video
	public void link() throws CompilerException, SyntaxException, LexicalException {
		if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.LINKB)){
			text(false,true);
			if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.LINKE)){
				parse();
				if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.ADDRESSB)){
					text(false,true);
					if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.ADDRESSE)){
						parse();
					}
					else throw new SyntaxException("Missing ending token for address: )");
				}
			}
			else throw new SyntaxException("Missing ending token for link: ]");
		}

	}
	/**
	 * This method implements the BNF grammar rule for the audio annotation.
	 * @throws CompilerException
	 * @throws LexicalException 
	 * @throws SyntaxException 
	 */
	public void audio() throws CompilerException, LexicalException, SyntaxException {
		if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.AUDIO)){
			parse();
			if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.ADDRESSB)){
				text(false,true);
				if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.ADDRESSE)){
					parse();
				}
			}
		}
		
	}
	/**
	 * This method implements the BNF grammar rule for the video annotation.
	 * @throws CompilerException
	 * @throws LexicalException 
	 * @throws SyntaxException 
	 */
	public void video() throws CompilerException, LexicalException, SyntaxException {
		if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.VIDEO)){
			parse();
			myLA.getNextToken();
			if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.ADDRESSB)){
				text(false,true);
				if(MyCompiler.currentToken.equalsIgnoreCase(Tokens.ADDRESSE)){
					parse();
				}
			}
		}
	}
	/**
	 * This method implements the BNF grammar rule for the newline annotation.
	 * @throws CompilerException
	 * @throws LexicalException 
	 * @throws SyntaxException 
	 */
	public void newline() throws CompilerException, LexicalException, SyntaxException {
		//stack.push(Tokens.NEWLINE);
		//TODO: Well this just isn't right at all
	}
	
	/**
	 * Gathers all characters in the source file between the current token and
	 * the next token as text
	 * @throws CompilerException
	 * @throws LexicalException
	 */
	public void text(boolean removeSpaces, boolean parseCurrentToken) throws CompilerException, LexicalException {
		int startPosition, endPosition, tokenLength, difference;
		String text;
		
		startPosition = myLA.currentPosition;
		//System.out.println("TEXT START: "+ MyCompiler.currentToken);
		if(parseCurrentToken){
			parse();

		}
		else {
			MyCompiler.fetchToken();
		}
		//System.out.println("TEXT END: " + MyCompiler.currentToken);
		tokenLength = myLA.currentToken.length();
		endPosition = myLA.currentPosition - (tokenLength);
		difference = endPosition-startPosition;
		
		char[] temp = new char[difference-1];
		for(int i=0; i < difference-1; i++) {
			temp[i] = myLA.fileCharArray[startPosition];
			startPosition++;
		}
		text = new String(temp);
		
		if(removeSpaces){
			text = text.replaceAll("\\s+","");
		}
		
		System.out.println("PUSHED: " +text);
		stack.push(text);
		
	}
	/**
	 * Determines if the next lexeme in the file is either text or a token
	 * @return true if text, false if token
	 */
	public boolean isText() {
		int position = myLA.currentPosition;
		while(true){
			if(myLA.fileCharArray[position] == ' ' | myLA.fileCharArray[position] == '\r'
					| myLA.fileCharArray[position] == '\n' | myLA.fileCharArray[position] == '\t'){
				position++;
			}
			else if(myLA.fileCharArray[position] == '#' | myLA.fileCharArray[position] == '^' | myLA.fileCharArray[position] == '<' | myLA.fileCharArray[position] == '>' |
					 myLA.fileCharArray[position] == '{' | myLA.fileCharArray[position] == '}' | myLA.fileCharArray[position] == '$'
					 | myLA.fileCharArray[position] == '=' | myLA.fileCharArray[position] == '*' | myLA.fileCharArray[position] == '+'
					 | myLA.fileCharArray[position] == ';' | myLA.fileCharArray[position] == '~' | myLA.fileCharArray[position] == '['
					 | myLA.fileCharArray[position] == ']' | myLA.fileCharArray[position] == '@' | myLA.fileCharArray[position] == '%'
					 | myLA.fileCharArray[position] == '(' | myLA.fileCharArray[position] == ')') {
				return false;
			}
			else return true;
		}
	}
	
	/**
	 * Adds the currentToken to the parse tree and retrieves the next token 
	 * from MyLexicalAnalyzer
	 * 
	 * @throws LexicalException
	 */
	public void parse() throws LexicalException{
		//add token to parse tree
		stack.push(MyCompiler.currentToken);
		System.out.println("PUSHED: " + MyCompiler.currentToken);
		MyCompiler.fetchToken();
		//System.out.println(" FETCHED: " + MyCompiler.currentToken);
	}
	public void parseWithoutAdvance() throws LexicalException{
		//add token to parse tree
		stack.push(MyCompiler.currentToken);
		System.out.println("PUSHED: " + MyCompiler.currentToken);
	}
	
}

