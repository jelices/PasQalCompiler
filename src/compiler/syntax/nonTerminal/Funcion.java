package compiler.syntax.nonTerminal;

import java.util.ArrayList;

import compiler.CompilerContext;
import compiler.intermediate.Label;
import compiler.intermediate.Variable;
import compiler.lexical.Token;
import compiler.semantic.symbol.SymbolFunction;
import compiler.semantic.symbol.SymbolTable;
import compiler.semantic.symbol.SymbolVariable;
import compiler.semantic.type.TypeFunction;
import compiler.semantic.type.TypePointer;
import compiler.semantic.type.TypeSimple;
import compiler.semantic.type.TypeTable;
import compiler.semantic.type.TypeProcedure.PasoPor;
import compiler.semantic.type.TypeSimple.Tipo;
import compiler.syntax.nonTerminal.TipoVariable.TipoVar;
import es.uned.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.compiler.intermediate.IntermediateCodeBuilderIF;
import es.uned.compiler.intermediate.TemporalFactoryIF;
import es.uned.compiler.semantic.SemanticErrorManager;
import es.uned.compiler.semantic.symbol.ScopeIF;
import es.uned.compiler.semantic.symbol.ScopeManagerIF;
import es.uned.compiler.semantic.type.TypeBase;
import es.uned.compiler.semantic.type.TypeIF;

public class Funcion extends NonTerminal {

	private ArrayList<Argumento> argumentos;
	private String nombre;
	private Tipo tipoDevuelto;
	
	public Funcion(Token identificador, DefinicionArgumentos argumentos, TipoPrimitivo tp, ScopeManagerIF scopeManager, SemanticErrorManager error)
	{
		if(identificador==null||argumentos==null||tp==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		nombre=identificador.getLexema();
		this.argumentos=argumentos.getArgumentos();
		tipoDevuelto=tp.getTipo();
		this.anadirTabla(scopeManager, error);
	}
	
	private TypeFunction crearTypeFunction(ScopeManagerIF scopeManager, SemanticErrorManager error)
	{
		ScopeIF scope= scopeManager.getCurrentScope();
		TypeFunction tf=new TypeFunction(scope);
		tf.setName((nombre+"()"));
		if(tipoDevuelto==Tipo.PUNTERO)
			tf.setDevuelve(new TypePointer(scope));
		if(tipoDevuelto==Tipo.ENTERO||tipoDevuelto==Tipo.BOOLEANO)
		{
			TypeSimple ts= new TypeSimple(scope);
			ts.setTipo(tipoDevuelto);
			tf.setDevuelve(ts);
		}
		for(int i=0; i<argumentos.size(); i++)
		{
			TypeBase ts = null;
			Argumento arg=argumentos.get(i);
			if(arg.getTipoVar()==TipoVar.PRIMITIVA)
			{
				if(arg.getTipo()==Tipo.ENTERO||arg.getTipo()==Tipo.BOOLEANO)
				{
					ts = new TypeSimple(scope);
					((TypeSimple) ts).setTipo(arg.getTipo());
				}
				if(arg.getTipo()==Tipo.PUNTERO)
				{
					ts = new TypePointer(scope);
					if (arg.getPasoPor()==PasoPor.REFERENCIA)
						error.semanticError("Warning: el paso de un puntero por referencia, no realiza la funcion esperada (pasQal no acepta doble indireccion)");
				}
			}
			if(arg.getTipoVar()==TipoVar.COMPUESTA)
			{
				ts=(TypeBase)scopeManager.searchType(arg.getIdentificador());
				if(ts==null)
				{
					error.semanticFatalError("Tipo "+ arg.getIdentificador() + " no existente");
				}
			}
			for(int j=0; j<arg.getListaIdentificadores().size();j++)
			{
				tf.anadirParametro(ts, arg.getPasoPor());
			}
		}
		return tf;
	}
	
	private SymbolFunction crearSymbolFunction(ScopeManagerIF scopeManager, SemanticErrorManager error)
	{
		ScopeIF scope= scopeManager.getCurrentScope();
		TypeFunction tf=(TypeFunction)scopeManager.searchType((nombre+"()"));
		SymbolFunction sf=new SymbolFunction(scope, (nombre+"()"),tf);
		if(tipoDevuelto==Tipo.PUNTERO)
			sf.setTipoDevuelto(new TypePointer(scope));
		if(tipoDevuelto==Tipo.ENTERO||tipoDevuelto==Tipo.BOOLEANO)
		{
			TypeSimple ts= new TypeSimple(scope);
			ts.setTipo(tipoDevuelto);
			sf.setTipoDevuelto(ts);
		}

		for(int i=0; i<argumentos.size(); i++)
		{
			TypeBase ts = null;
			Argumento arg=argumentos.get(i);
			if(arg.getTipoVar()==TipoVar.PRIMITIVA)
			{
				if(arg.getTipo()==Tipo.ENTERO||arg.getTipo()==Tipo.BOOLEANO)
				{
					ts = new TypeSimple(scope);
					((TypeSimple) ts).setTipo(arg.getTipo());
				}
				if(arg.getTipo()==Tipo.PUNTERO)
					ts = new TypePointer(scope);
			}
			if(arg.getTipoVar()==TipoVar.COMPUESTA)
			{
				ts=(TypeBase)scopeManager.searchType(arg.getIdentificador());
				if(ts==null)
				{
					error.semanticFatalError("Tipo "+ arg.getIdentificador() + " no existente");
				}
			}
			for(int j=0; j<arg.getListaIdentificadores().size();j++)
			{
				sf.anadirAtributo(arg.getListaIdentificadores().get(j), ts);
			}
		}
		return sf;
		
	}
	
	private void anadirTabla(ScopeManagerIF scopeManager, SemanticErrorManager error)
	{
		ScopeIF scope=scopeManager.getCurrentScope();
		TypeTable tt=(TypeTable)scope.getTypeTable();
		if(tt.containsType(nombre+"()"))
			error.semanticFatalError("Funcion "+ nombre + "() ya existente");
		tt.addType(crearTypeFunction(scopeManager, error));
		SymbolTable st = (SymbolTable)scope.getSymbolTable();
		if(st.containsSymbol(nombre+"()"))
			error.semanticFatalError("Funcion "+ nombre + "() ya existente");
		st.addSymbol(crearSymbolFunction(scopeManager, error));
	}
	
	public void anadirAtributos(ScopeManagerIF scopeManager, SemanticErrorManager error)
	{
		TypeIF ts = null;
		ScopeIF scope=scopeManager.getCurrentScope();
		SymbolTable table = (SymbolTable)scope.getSymbolTable();
		
		TypeFunction tf=(TypeFunction)scopeManager.searchType((nombre+"()"));
		
		if(tipoDevuelto==Tipo.ENTERO||tipoDevuelto==Tipo.BOOLEANO)
		{
			ts = new TypeSimple(scope);
			((TypeSimple) ts).setTipo(tipoDevuelto);
		}
		if(tipoDevuelto==Tipo.PUNTERO)
			ts = new TypePointer(scope);
		if(table.containsSymbol(nombre))
    		error.semanticFatalError("Variable "+ nombre.toUpperCase() + " repetida");
    	else
    	{
    		SymbolVariable variable = new SymbolVariable(scope, nombre, ts);
    		table.addSymbol(variable);
    	}
		
		int numeroArgumento=1;
		for(int i=0; i<argumentos.size(); i++)
		{
			Argumento arg=argumentos.get(i);
			
			if(arg.getTipoVar()==TipoVar.PRIMITIVA)
			{
				if(arg.getTipo()==Tipo.ENTERO||arg.getTipo()==Tipo.BOOLEANO)
				{
					ts = new TypeSimple(scope);
					((TypeSimple) ts).setTipo(arg.getTipo());
				}
				if(arg.getTipo()==Tipo.PUNTERO)
					ts = new TypePointer(scope);
			}
			if(arg.getTipoVar()==TipoVar.COMPUESTA)
			{
				ts=scopeManager.searchType(arg.getIdentificador());
				if(ts==null)
				{
					error.semanticFatalError("Tipo "+ arg.getIdentificador() + " no existente");
				}
			}
			
		    for(int j=0; j<arg.getListaIdentificadores().size(); j++) 
		    {
		    	if(table.containsSymbol(arg.getListaIdentificadores().get(j)))
		    		error.semanticFatalError("Variable "+ arg.getListaIdentificadores().get(j).toUpperCase() + " repetida");
		    	else
		    	{
		    		SymbolVariable variable = new SymbolVariable(scope,arg.getListaIdentificadores().get(j), ts);
		    		variable.setParametro(numeroArgumento);
		    		if(tf.getParametrosPaso().get(j)==PasoPor.REFERENCIA)
		    			variable.setReferencia(true);
		    		else
		    			variable.setReferencia(false);
		    		numeroArgumento++;
		    		variable.asignar();
		    		table.addSymbol(variable);
		    	}
		    }
		}
	}
	
	public void comprobarVuelta(ScopeManagerIF scopeManager, SemanticErrorManager error)
	{
		if(!(((SymbolVariable)scopeManager.searchSymbol(nombre)).asignado()))
			error.semanticFatalError("La funcion "+ nombre+ " no asigna nada a su valor devuelto");
	}
	
	public void generarCodigoIntermedio(Subprogramas s, Cuerpo c, ScopeManagerIF scopeManager, SemanticErrorManager error)
	{
		if(s==null||c==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		TemporalFactoryIF tf= CompilerContext.getTemporalFactory(scopeManager.getCurrentScope());
		Label funcion=new Label(nombre+"()");
		IntermediateCodeBuilderIF cb = new IntermediateCodeBuilder (scopeManager.getCurrentScope());
		cb.addQuadruple("LABEL", funcion);
		cb.addQuadruples(c.getIntermediateCode());
		
		//Secuencia de salida
		cb.addQuadruple("SET-VALOR-DEVUELTO", new Variable(nombre, scopeManager.searchSymbol(nombre).getScope(), scopeManager.getCurrentScope()));
		cb.addQuadruple("COPIAR-FP-EN-SP", null);
		cb.addQuadruple("VINC-CONTROL-a-FP", null);
		cb.addQuadruple("RET", null);
		
		cb.addQuadruples(s.getIntermediateCode());
		
		this.setIntermediateCode(cb.create());
	}
	
	
}

