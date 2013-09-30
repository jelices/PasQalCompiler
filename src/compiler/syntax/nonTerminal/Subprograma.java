package compiler.syntax.nonTerminal;

import compiler.CompilerContext;

public class Subprograma extends NonTerminal {
	
	public Subprograma(Funcion f)
	{
		if(f==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		this.setIntermediateCode(f.getIntermediateCode());
	}
	
	public Subprograma(Procedimiento p)
	{
		if(p==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		this.setIntermediateCode(p.getIntermediateCode());
	}

}
