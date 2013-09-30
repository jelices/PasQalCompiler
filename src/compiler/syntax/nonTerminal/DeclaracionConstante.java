package compiler.syntax.nonTerminal;

import java.util.ArrayList;

import compiler.CompilerContext;
import compiler.semantic.symbol.SymbolConstant;
import compiler.semantic.symbol.SymbolTable;
import compiler.semantic.type.TypeSimple;
import compiler.semantic.type.TypeSimple.Tipo;
import es.uned.compiler.semantic.SemanticErrorManager;
import es.uned.compiler.semantic.symbol.ScopeIF;

public class DeclaracionConstante extends NonTerminal 
{
	private ArrayList<String> listaIdentificadores;
	private Tipo tipo;
	private int valorE;
	private boolean valorB;
	
	public DeclaracionConstante(ListaIdentificadores li, ValorConstante vc)
	{
		if(li==null||vc==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		listaIdentificadores=li.getListaIdentificadores();
		tipo=vc.getTipo();
		if(tipo==Tipo.BOOLEANO)
			valorB=(Boolean)vc.getValor();
		if(tipo==Tipo.ENTERO)
			valorE=(Integer)vc.getValor();
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
	
	public ArrayList<String> getListaIdentificadores()
	{
		return listaIdentificadores;
	}
	
	public void anadirTabla(ScopeIF scope, SemanticErrorManager error)
	{
		SymbolTable table= (SymbolTable) scope.getSymbolTable();
	    TypeSimple ts = new TypeSimple(scope);
	    ts.setTipo(tipo);
	    for(int i=0; i<listaIdentificadores.size(); i++) 
	    {
	    	if(table.containsSymbol(listaIdentificadores.get(i)))
	    		error.semanticFatalError("Constante "+ listaIdentificadores.get(i).toUpperCase() + " repetida");
	    	else
	    	{
	    		SymbolConstant constante= new SymbolConstant(scope,listaIdentificadores.get(i), ts);
	    		constante.setValor(getValor());
	    		table.addSymbol(constante);
	    	}
	    }
	}
	

}
