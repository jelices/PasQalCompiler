package compiler.syntax.nonTerminal;

import compiler.CompilerContext;
import compiler.lexical.Token;

public class Operador extends NonTerminal {
	
	private String operador;
	
	public Operador(Token operador)
	{
		if(operador==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		this.operador=operador.getLexema();
	}
	
	public String getOperador()
	{
		return operador;
	}

}
