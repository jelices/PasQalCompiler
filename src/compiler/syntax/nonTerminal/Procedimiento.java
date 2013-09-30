package compiler.syntax.nonTerminal;

import java.util.ArrayList;

import compiler.CompilerContext;
import compiler.intermediate.Label;
import compiler.intermediate.Value;
import compiler.intermediate.Variable;
import compiler.lexical.Token;
import compiler.semantic.symbol.SymbolProcedure;
import compiler.semantic.symbol.SymbolTable;
import compiler.semantic.symbol.SymbolVariable;
import compiler.semantic.type.TypePointer;
import compiler.semantic.type.TypeProcedure;
import compiler.semantic.type.TypeSimple;
import compiler.semantic.type.TypeTable;
import compiler.semantic.type.TypeProcedure.PasoPor;
import compiler.semantic.type.TypeSimple.Tipo;
import compiler.syntax.nonTerminal.TipoVariable.TipoVar;
import es.uned.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.compiler.intermediate.IntermediateCodeBuilderIF;
import es.uned.compiler.intermediate.TemporalFactoryIF;
import es.uned.compiler.intermediate.TemporalIF;
import es.uned.compiler.semantic.SemanticErrorManager;
import es.uned.compiler.semantic.symbol.ScopeIF;
import es.uned.compiler.semantic.symbol.ScopeManagerIF;
import es.uned.compiler.semantic.type.TypeBase;
import es.uned.compiler.semantic.type.TypeIF;

public class Procedimiento extends NonTerminal 
{
	private ArrayList<Argumento> argumentos;
	private String nombre;
	
	public Procedimiento(Token identificador, DefinicionArgumentos argumentos, ScopeManagerIF scopeManager, SemanticErrorManager error)
	{
		if(identificador==null||argumentos==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		nombre=identificador.getLexema() +"()";
		this.argumentos=argumentos.getArgumentos();
		this.anadirTabla(scopeManager, error);
	}
	
	private TypeProcedure crearTypeProcedure(ScopeManagerIF scopeManager, SemanticErrorManager error)
	{
		ScopeIF scope= scopeManager.getCurrentScope();
		TypeProcedure tp=new TypeProcedure(scope);
		tp.setName((nombre));
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
				tp.anadirParametro(ts, arg.getPasoPor());
			}
		}
		return tp;
	}
	
	private SymbolProcedure crearSymbolProcedure(ScopeManagerIF scopeManager, SemanticErrorManager error)
	{
		ScopeIF scope= scopeManager.getCurrentScope();
		TypeProcedure tp=(TypeProcedure)scopeManager.searchType(nombre);
		SymbolProcedure sp=new SymbolProcedure(scope, (nombre),tp);
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
				sp.anadirAtributo(arg.getListaIdentificadores().get(j), ts);
			}
		}
		return sp;
		
	}
	
	private void anadirTabla(ScopeManagerIF scopeManager, SemanticErrorManager error)
	{
		ScopeIF scope=scopeManager.getCurrentScope();
		TypeTable tt=(TypeTable)scope.getTypeTable();
		if(tt.containsType(nombre))
			error.semanticFatalError("Procedimiento "+ nombre + " ya existente");
		tt.addType(crearTypeProcedure(scopeManager, error));
		SymbolTable st = (SymbolTable)scope.getSymbolTable();
		if(st.containsSymbol(nombre))
			error.semanticFatalError("Procedimiento "+ nombre + " ya existente");
		st.addSymbol(crearSymbolProcedure(scopeManager, error));
	}
	
	public void anadirAtributos(ScopeManagerIF scopeManager, SemanticErrorManager error)
	{
		TypeIF ts = null;
		ScopeIF scope=scopeManager.getCurrentScope();
		SymbolTable table = (SymbolTable)scope.getSymbolTable();
		TypeProcedure tp=(TypeProcedure)scopeManager.searchType(nombre);
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
		    		if(tp.getParametrosPaso().get(numeroArgumento-1)==PasoPor.REFERENCIA)
		    		{
		    			variable.setReferencia(true);
		    		}
		    		else
		    		{
		    			variable.setReferencia(false);
		    		}
		    		numeroArgumento++;
		    		variable.asignar();
		    		table.addSymbol(variable);
		    	}
		    }
		}
	}
	
	public void generarCodigoIntermedio(Subprogramas s, Cuerpo c, ScopeManagerIF scopeManager, SemanticErrorManager error)
	{
		if(s==null||c==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		TemporalFactoryIF tf= CompilerContext.getTemporalFactory(scopeManager.getCurrentScope());
		TemporalIF temp2= tf.create();
		Label procedimiento=new Label(nombre);
		IntermediateCodeBuilderIF cb = new IntermediateCodeBuilder (scopeManager.getCurrentScope());
		cb.addQuadruple("LABEL", procedimiento);
		cb.addQuadruples(c.getIntermediateCode());
		
		//Secuencia de salida
		cb.addQuadruple("COPIAR-FP-EN-SP", null);
		cb.addQuadruple("VINC-CONTROL-a-FP", null);
		cb.addQuadruple("RET", null);
		
		cb.addQuadruples(s.getIntermediateCode());
		
		this.setIntermediateCode(cb.create());
	}
	
}
