package compiler.syntax.nonTerminal;

import java.util.ArrayList;

import compiler.CompilerContext;
import compiler.semantic.symbol.SymbolConstant;
import compiler.semantic.symbol.SymbolTable;
import compiler.semantic.symbol.SymbolVariable;
import compiler.semantic.type.TypePointer;
import compiler.semantic.type.TypeSimple;
import compiler.semantic.type.TypeSimple.Tipo;
import compiler.syntax.nonTerminal.TipoVariable.TipoVar;
import es.uned.compiler.semantic.SemanticErrorManager;
import es.uned.compiler.semantic.symbol.ScopeIF;
import es.uned.compiler.semantic.symbol.ScopeManager;
import es.uned.compiler.semantic.symbol.ScopeManagerIF;
import es.uned.compiler.semantic.type.TypeIF;

public class DeclaracionVariable extends NonTerminal {
	
	private TipoVar tipoVar;
	private Tipo tipo;
	private String identificador;
	private ArrayList<String> identificadores;
	
	public DeclaracionVariable (ListaIdentificadores li, TipoVariable tv )
	{
		if(li==null||tv==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		identificadores=li.getListaIdentificadores();
		tipoVar=tv.getTipoVar();
		if(tipoVar==TipoVar.PRIMITIVA)
			tipo=tv.getTipo();
		if(tipoVar==TipoVar.COMPUESTA)
			identificador=tv.getIdentificador();
	}
	
	public TipoVar getTipoVar()
	{
		return tipoVar;
	}
	
	public Tipo getTipo()
	{
		return tipo;
	}
	
	public String getIdentificador()
	{
		return identificador;
	}
	public ArrayList<String> getListaIdentificadores()
	{
		return identificadores;
	}
	
	public void anadirTabla(ScopeManagerIF scopeManager, SemanticErrorManager error)
	{
		TypeIF ts = null;
		ScopeIF scope=scopeManager.getCurrentScope();
		SymbolTable table= (SymbolTable) scope.getSymbolTable();
		if(tipoVar==TipoVar.PRIMITIVA)
		{
			if(tipo==Tipo.ENTERO||tipo==Tipo.BOOLEANO)
			{
				ts = new TypeSimple(scope);
				((TypeSimple) ts).setTipo(tipo);
			}
			if(tipo==Tipo.PUNTERO)
				ts = new TypePointer(scope);
		}
		if(tipoVar==TipoVar.COMPUESTA)
		{
			ts=scopeManager.searchType(identificador);
			if(ts==null)
			{
				error.semanticFatalError("Tipo "+ identificador + " no existente");
			}
		}
		
	    for(int i=0; i<identificadores.size(); i++) 
	    {
	    	if(table.containsSymbol(identificadores.get(i)))
	    		error.semanticFatalError("Variable "+ identificadores.get(i).toUpperCase() + " repetida");
	    	else
	    	{
	    		SymbolVariable variable = new SymbolVariable(scope,identificadores.get(i), ts);
	    		if(scope.getLevel()==0)
	    			variable.setGlobal(true);
	    		else
	    			variable.setGlobal(false);
	    		table.addSymbol(variable);
	    	}
	    }
	}
}
