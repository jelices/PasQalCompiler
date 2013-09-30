package compiler.syntax.nonTerminal;

import compiler.intermediate.Value;
import compiler.semantic.type.TypeSimple.Tipo;
import es.uned.compiler.semantic.type.TypeBase;

public class ValorConstante extends NonTerminal 
{
	private Tipo tipo;
	private int valorE;
	private boolean valorB;
	
	public ValorConstante()
	{
	}
	
	public ValorConstante (int valor)
	{
		tipo=Tipo.ENTERO;
		valorE=valor;
	}
	
	public ValorConstante (boolean valor)
	{
		tipo=Tipo.BOOLEANO;
		valorB=valor;
	}
	
	public Tipo getTipo()
	{
		return tipo;
	}
	
	public Object getValor()
	{
		if (tipo==Tipo.BOOLEANO)
			return valorB;
		if (tipo==Tipo.ENTERO)
			return valorE;
		return null;
	}
	
	public Value getValue()
	{
		if(tipo==Tipo.ENTERO)
			return new Value(valorE);
		if((tipo==Tipo.BOOLEANO)&&(!valorB))
			return new Value(0);
		if((tipo==Tipo.BOOLEANO)&&(valorB))
			return new Value(1);
		return null;
	}

}
