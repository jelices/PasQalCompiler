package compiler.syntax.nonTerminal;

import compiler.CompilerContext;
import compiler.semantic.type.TypeSimple.Tipo;

public class TipoPrimitivo extends NonTerminal 
{
	private Tipo tipo;
	
	public TipoPrimitivo() 
	{
	}
	
	public TipoPrimitivo(Tipo tipo) 
	{
		if(tipo==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		this.tipo=tipo;
	}

	
	public Tipo getTipo()
	{
		return tipo;
	}

}
