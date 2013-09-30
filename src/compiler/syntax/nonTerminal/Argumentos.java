package compiler.syntax.nonTerminal;

import java.util.ArrayList;

import compiler.CompilerContext;

public class Argumentos extends NonTerminal 
{
	private ArrayList<Argumento> argumentos;
	
	public Argumentos (Argumento arg)
	{
		if(arg==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		argumentos = new ArrayList<Argumento>();
		argumentos.add(arg);
	}
	
	public Argumentos (Argumento arg, Argumentos argumentos)
	{
		if(arg==null||argumentos==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		this.argumentos = argumentos.getArgumentos();
		this.argumentos.add(0,arg);
	}
	
	public ArrayList<Argumento> getArgumentos()
	{
		return argumentos;
	}
	
	
	
}
