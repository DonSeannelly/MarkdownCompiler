package edu.towson.cis.cosc455.sdonne5.project1.implementation;

public class MySemanticAnalyzer {
	//TODO: Fix scoping: after a block exits resolve the definition of the variable
	private MyStack<String> stack;
	private int headCount, italicsCount, boldCount;
	
	public MySemanticAnalyzer(MyStack<String> s){
		stack = s;
	}
	/**
	 * Searches the parse tree to determine if the variable being used was previously defined
	 * @return true if the variable was previously defined
	 * @throws CompilerException
	 */
	public boolean lookup() throws CompilerException{
		
		String lookupToken = stack.pop();
		String variableData = "";
		System.out.println("Looking up: "+lookupToken);
		System.out.print("First ");
		MyStack<String> tempStack = new MyStack<String>();
		while(true){
			System.out.println("Peek: "+stack.peek());
			if(stack.peek().equalsIgnoreCase(lookupToken)){
				//variable verified only if $DEF is one further peek
				/*
				 * Ensure $DEF is one further peek in the stack
				 * Go back to EQSIGN, then back one further to arrive at the text of the variable name
				 * Only push the variable's value back onto the stack, REMOVE: $USE and $END
				 */
				tempStack.push(stack.pop());
				if(stack.peek().equalsIgnoreCase(Tokens.DEFB)){
					//push the variable name back on
					stack.push(tempStack.pop());
					//push the EQSIGN back on
					stack.push(tempStack.pop());
					//push the variable data back on and store what that data is
					stack.push(tempStack.pop());
					variableData = stack.peek();
				}
				while(!tempStack.isEmpty()){
					//return tempStack to main stack
					stack.push(tempStack.pop());
					//push the variable data onto the stack
					if(tempStack.isEmpty()){
						//remove $USE token
						stack.pop();
						stack.push(variableData);
					}
				}
				return true;
			}
			else {
				//Pop off the main stack to the holding stack for further search of the main stack
				System.out.println("Popping: "+stack.peek());
				tempStack.push(stack.pop());
				if(stack.isEmpty()){
					//throw static semantic error of variable not defined
					throw new CompilerException("The variable -" + lookupToken +"- was not defined in the file.");
				}
			}
		}
	}
	/**
	 * Converts the parse tree into html code
	 * @return the html code as a String
	 */
	public String convert(){
		/*
		 * If the current token pulled off stack isToken, convert to HTML equivalent
		 * else, output = currentToken + output;
		 */
		String output = "";
		String writeToken = "";
		while(!stack.isEmpty()){
			writeToken = stack.pop();
			if(Tokens.isToken(writeToken)){
				if(writeToken.equalsIgnoreCase(Tokens.DEFUSEE)){
					/*
					 * If the end token for a definition is found, then
					 * remove that token and every parsed token up until writeToken = DEFB
					 */
					writeToken = "";
					while(!stack.pop().equalsIgnoreCase(Tokens.DEFB));
				}
				else {
					//convert to HTML
					writeToken = getHTML(writeToken);
				}
			}
			output = writeToken + output;
		}
		return output;
	}
	/**
	 * Converts mkd syntax to html
	 * @param s - the token to be converted
	 * @return the html equivalent of the mkd syntax
	 */
	public String getHTML(String s){
		switch(s){
		case Tokens.DOCB:
			return "<html>";
		case Tokens.DOCE:
			return "</html>";
		case Tokens.HEAD:
			//differentiate based on position in file return "<head>"; and return "</head>";
			if(headCount == 0){
				return "</head>";
			}
			else {
				//return headCount to 0, the opening tag has been found
				headCount = 0;
				return "<head>";
			}
		case Tokens.TITLEB:
			return "<title>";
		case Tokens.TITLEE:
			return "</title>";
		case Tokens.PARAB:
			return "<p>";
		case Tokens.PARAE:
			return "</p>";
		case Tokens.EQSIGN:
			//TODO: No need for EQSIGN to enter this method, it is already in its HTML form
			return "=";
		case Tokens.BOLD:
			//differentiate based on position in file return "<b>"; and return "</b>";
			if(boldCount == 0){
				return "</b>";
			}
			else {
				//return boldCount to 0, the opening tag has been found
				boldCount = 0;
				return "<b>";
			}
		case Tokens.ITALICS:
			//differentiate based on position in file return "<i>"; and return "</i>";
			if(italicsCount == 0){
				return "</i>";
			}
			else {
				//return italicsCount to 0, the opening tag has been found
				italicsCount = 0;
				return "<i>";
			}
		case Tokens.LISTITEMB:
			return "<ul>";
		case Tokens.LISTITEME:
			return "</ul>";
		case Tokens.NEWLINE:
			return "<br>";
		case Tokens.LINKB:
			return "<>";
		case Tokens.LINKE:
			return "<>";
		case Tokens.AUDIO:
			return "<>";
		case Tokens.VIDEO:
			return "<>";
		case Tokens.ADDRESSB:
			return "<>";
		case Tokens.ADDRESSE:
			return "<>";
		}
		return s;
	}
}
