package compiler.syntax.nonTerminal;


import compiler.CompilerContext;
import compiler.intermediate.Label;

import es.uned.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.compiler.intermediate.IntermediateCodeBuilderIF;
import es.uned.compiler.semantic.symbol.ScopeManagerIF;

public class Programa extends Axiom {
	
	
	public Programa(Subprogramas s, Cuerpo c, ScopeManagerIF scopeManager)
	{
		if(s==null||c==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		IntermediateCodeBuilderIF cb = new IntermediateCodeBuilder (scopeManager.getCurrentScope());
		Label inicio= new Label("Inicio");
		cb.addQuadruple("LABEL", inicio);
		cb.addQuadruples(c.getIntermediateCode());
		cb.addQuadruple("HALT", null);
		cb.addQuadruples(s.getIntermediateCode());
		cb.addQuadruple("END", null);
		this.setIntermediateCode(cb.create());
	}
}
