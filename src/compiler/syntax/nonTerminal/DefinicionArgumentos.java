package compiler.syntax.nonTerminal;

import java.util.ArrayList;

import compiler.CompilerContext;

public class DefinicionArgumentos extends NonTerminal {

private ArrayList<Argumento> argumentos;
	
	public DefinicionArgumentos() 
	{
		argumentos = new ArrayList<Argumento>();
	}
	
	public DefinicionArgumentos (Argumentos args)
	{
		if(args==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		argumentos=args.getArgumentos();
	}
	
	public ArrayList<Argumento> getArgumentos()
	{
		return argumentos;
	}
}
