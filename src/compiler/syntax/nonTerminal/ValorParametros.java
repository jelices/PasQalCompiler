package compiler.syntax.nonTerminal;

import java.util.ArrayList;

import compiler.CompilerContext;
import compiler.syntax.nonTerminal.NonTerminal;

public class ValorParametros extends NonTerminal {
	ArrayList<Expresion> valorParametros;
	
	public ValorParametros(Expresion exp)
	{
		if(exp==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		valorParametros=new ArrayList<Expresion>();
		valorParametros.add(exp);
	}
	
	public ValorParametros(Expresion exp, ValorParametros vp)
	{
		if(exp==null||vp==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		this.valorParametros=vp.getValorParametros();
		valorParametros.add(0,exp);
	}
	
	public ArrayList<Expresion> getValorParametros()
	{
		return valorParametros;
	}
}
