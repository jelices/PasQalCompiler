package compiler.syntax.nonTerminal;

import java.util.ArrayList;

import compiler.CompilerContext;
import compiler.lexical.Token;
import es.uned.compiler.semantic.SemanticError;

public class ListaIdentificadores extends NonTerminal 
{
	private ArrayList<String> identificadores;
	
	public ListaIdentificadores ()
	{
		identificadores=new ArrayList<String>();
	}
	
	public ListaIdentificadores(Token identificador)
	{
		this();
		if(identificador==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		identificadores.add(identificador.getLexema());
	}
	
	public ListaIdentificadores(ListaIdentificadores lista, Token identificador)
	{
		if(lista==null||identificador==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		identificadores=lista.getListaIdentificadores();
		identificadores.add(0,identificador.getLexema());
	}
	
	public ArrayList<String> getListaIdentificadores()
	{
		return identificadores;
	}

}
