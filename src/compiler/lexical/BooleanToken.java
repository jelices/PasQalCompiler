package compiler.lexical;

public class BooleanToken extends Token {
	public boolean getValue()
	{
		return Boolean.parseBoolean(this.getLexema());
	}
}
