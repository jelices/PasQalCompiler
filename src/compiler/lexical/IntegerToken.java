package compiler.lexical;

public class IntegerToken extends Token {

	public IntegerToken (int id)
	{
	        super (id);
	}
	 
	public int getValue()
	{
		return Integer.parseInt(this.getLexema());
	}
}
