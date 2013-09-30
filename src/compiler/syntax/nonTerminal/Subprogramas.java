package compiler.syntax.nonTerminal;


import compiler.CompilerContext;

import es.uned.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.compiler.intermediate.IntermediateCodeBuilderIF;
import es.uned.compiler.semantic.symbol.ScopeManagerIF;

public class Subprogramas extends NonTerminal {

	public Subprogramas()
	{
		super();
	}
	
	public Subprogramas(Subprograma s, Subprogramas bs, ScopeManagerIF scopeManager)
	{
		if(s==null||bs==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		IntermediateCodeBuilderIF cb = new IntermediateCodeBuilder (scopeManager.getCurrentScope());
		cb.addQuadruples(s.getIntermediateCode());
		cb.addQuadruples(bs.getIntermediateCode());
		this.setIntermediateCode(cb.create());
	}
	
}
