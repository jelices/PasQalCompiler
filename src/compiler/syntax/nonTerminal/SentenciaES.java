package compiler.syntax.nonTerminal;

import compiler.CompilerContext;
import compiler.intermediate.Cadena;

import es.uned.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.compiler.intermediate.IntermediateCodeBuilderIF;
import es.uned.compiler.semantic.symbol.ScopeManagerIF;

public class SentenciaES extends NonTerminal 
{
	public SentenciaES(ScopeManagerIF scopeManager)
	{
		IntermediateCodeBuilderIF cb = new IntermediateCodeBuilder (scopeManager.getCurrentScope());
		cb.addQuadruple("WRTLN",null);
		this.setIntermediateCode(cb.create());
	}
	
	public SentenciaES(MostradoPantalla mp)
	{
		if(mp==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		this.setIntermediateCode(mp.getIntermediateCode());
	}
	

}
