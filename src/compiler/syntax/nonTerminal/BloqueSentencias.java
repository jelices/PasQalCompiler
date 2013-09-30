package compiler.syntax.nonTerminal;

import compiler.CompilerContext;

public class BloqueSentencias extends NonTerminal {
	
	public BloqueSentencias(Sentencia s)
	{
		if(s==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		this.setIntermediateCode(s.getIntermediateCode());
	}
	
	public BloqueSentencias(ListaSentencias ls)
	{
		if(ls==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		this.setIntermediateCode(ls.getIntermediateCode());
	}
}
