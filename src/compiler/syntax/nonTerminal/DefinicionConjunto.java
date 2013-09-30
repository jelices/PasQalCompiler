package compiler.syntax.nonTerminal;

import compiler.CompilerContext;
import compiler.lexical.IntegerToken;
import es.uned.compiler.semantic.SemanticErrorManager;

public class DefinicionConjunto extends NonTerminal {

	private int inicio;
	private int fin;
	public DefinicionConjunto(IntegerToken inicio, IntegerToken fin)
	{
		if(inicio==null||fin==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		this.inicio=inicio.getValue();
		this.fin = fin.getValue();
	}
	
	public int getInicio()
	{
		return inicio;
	}
	
	public int getFin()
	{
		return fin;
	}
	
}
