package compiler.syntax.nonTerminal;

import compiler.CompilerContext;

public class Cuerpo extends NonTerminal {
	
	public Cuerpo(ListaSentencias ls)
	{
		if(ls==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		this.setIntermediateCode(ls.getIntermediateCode());
	}

}
