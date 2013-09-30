package compiler.syntax.nonTerminal;

import compiler.CompilerContext;

public class ParteElse extends NonTerminal 
{
	public ParteElse (BloqueSentencias b)
	{
		if(b==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		this.setIntermediateCode(b.getIntermediateCode());
	}

	public ParteElse ()
	{
		super();
	}

}
