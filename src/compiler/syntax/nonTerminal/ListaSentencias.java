package compiler.syntax.nonTerminal;


import compiler.CompilerContext;

import es.uned.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.compiler.intermediate.IntermediateCodeBuilderIF;
import es.uned.compiler.semantic.symbol.ScopeManagerIF;

public class ListaSentencias extends NonTerminal {
	
	private boolean vacia;
	
	public ListaSentencias()
	{
		super();
		vacia=true;
	}
	
	public ListaSentencias(Sentencia s, ListaSentencias ls, ScopeManagerIF scopeManager)
	{
		if(s==null||ls==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		IntermediateCodeBuilderIF cb = new IntermediateCodeBuilder (scopeManager.getCurrentScope());
		if(s!=null)
			cb.addQuadruples(s.getIntermediateCode());
		cb.addQuadruples(ls.getIntermediateCode());
		this.setIntermediateCode(cb.create());
		vacia=false;
	}
	
	public boolean getVacia()
	{
		return vacia;
	}
}
