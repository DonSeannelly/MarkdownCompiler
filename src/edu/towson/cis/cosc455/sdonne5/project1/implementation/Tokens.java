package edu.towson.cis.cosc455.sdonne5.project1.implementation;

public class Tokens {

	public static final String DOCB = "#BEGIN";
	public static final String DOCE = "#END";
	public static final String HEAD = "^";
	public static final String TITLEB = "<";
	public static final String TITLEE = ">";
	public static final String PARAB = "{";
	public static final String PARAE = "}";
	public static final String DEFB = "$DEF";
	public static final String DEFUSEE = "$END";
	public static final String EQSIGN = "=";
	public static final String USEB = "$USE";
	public static final String BOLD = "**";
	public static final String ITALICS = "*";
	public static final String LISTITEMB = "+";
	public static final String LISTITEME = ";";
	public static final String NEWLINE = "~";
	public static final String LINKB = "[";
	public static final String LINKE = "]";
	public static final String AUDIO = "@";
	public static final String VIDEO = "%";
	public static final String ADDRESSB = "(";
	public static final String ADDRESSE = ")";
	//public static final String TEXT = ;
	//create array to store all tokens
	private static String[] tokens = { 
			DOCB, DOCE, HEAD, TITLEB,
			TITLEE, PARAB, PARAE, DEFB,
			DEFUSEE, EQSIGN, USEB, BOLD,
			ITALICS, LISTITEMB, LISTITEME,
			NEWLINE, LINKB, LINKE, AUDIO,
			VIDEO, ADDRESSB, ADDRESSE
		};
	/**
	 * Checks if the provided token exists in the language
	 * 
	 * @return true if token exists in the token array, else return false
	 */
	public static boolean isToken(String toCheck){
		for(int i=0;i<tokens.length;i++){
			if(toCheck.equalsIgnoreCase(tokens[i])){
				return true;
			}
		}
		return false;
	}
}
