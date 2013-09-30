package compiler.syntax.nonTerminal;

import compiler.CompilerContext;
import compiler.lexical.Token;
import compiler.semantic.type.TypeSimple.Tipo;

public class TipoVariable extends NonTerminal {

	public enum TipoVar {PRIMITIVA, COMPUESTA};
	private TipoVar tipoVar;
	private Tipo tipo;
	private String identificador;
	
	public TipoVariable(TipoPrimitivo t)
	{
		if(t==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		tipoVar=TipoVar.PRIMITIVA;
		tipo=t.getTipo();
	}
	
	public TipoVariable(Token t)
	{
		if(t==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		tipoVar=TipoVar.COMPUESTA;
		identificador=t.getLexema();
	}
	
	public TipoVar getTipoVar()
	{
		return tipoVar;
	}
	
	public Tipo getTipo()
	{
		return tipo;
	}
	
	public String getIdentificador()
	{
		return identificador;
	}
	
	
}
