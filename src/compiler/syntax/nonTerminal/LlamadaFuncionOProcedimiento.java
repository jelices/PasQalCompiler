package compiler.syntax.nonTerminal;

import compiler.CompilerContext;
import compiler.intermediate.Label;
import compiler.intermediate.Value;
import compiler.intermediate.Variable;
import compiler.lexical.Token;
import compiler.semantic.symbol.SymbolFunction;
import compiler.semantic.symbol.SymbolProcedure;
import compiler.semantic.type.TypeFunction;
import compiler.semantic.type.TypePointer;
import compiler.semantic.type.TypeProcedure;
import compiler.semantic.type.TypeRecord;
import compiler.semantic.type.TypeSet;
import compiler.semantic.type.TypeSimple;
import compiler.semantic.type.TypeProcedure.PasoPor;
import compiler.semantic.type.TypeSimple.Tipo;
import es.uned.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.compiler.intermediate.IntermediateCodeBuilderIF;
import es.uned.compiler.intermediate.TemporalFactoryIF;
import es.uned.compiler.intermediate.TemporalIF;
import es.uned.compiler.semantic.SemanticErrorManager;
import es.uned.compiler.semantic.symbol.ScopeIF;
import es.uned.compiler.semantic.symbol.ScopeManagerIF;
import es.uned.compiler.semantic.symbol.SymbolIF;
import es.uned.compiler.semantic.type.TypeBase;
import es.uned.compiler.semantic.type.TypeIF;

public class LlamadaFuncionOProcedimiento extends NonTerminal 
{
	public enum TipoLlamada {FUNCION, PROCEDIMIENTO};
	
	private TipoLlamada tipoLlamada;
	private TypeBase tipoDevuelto;
	private String nombre;
	private ScopeIF scope;
	private TemporalIF temp;
	
	public LlamadaFuncionOProcedimiento(Token identificador, ScopeManagerIF scopeManager, SemanticErrorManager error)
	{
		if(identificador==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		nombre=identificador.getLexema()+"()";
		scope=scopeManager.getCurrentScope();
		SymbolIF symbol = scopeManager.searchSymbol(nombre);
		if(symbol==null)
			error.semanticFatalError("No encontrada la funcion o el procedimiento "+ nombre);
		//no estoy seguro si se puede llamar a una funcion externa desde una interna
		if(scope.getLevel()>(symbol.getScope().getLevel()+1))
			error.semanticFatalError("La funcion "+ nombre + " no es alcanzable");
		if(!(symbol instanceof SymbolProcedure))
			error.semanticFatalError(nombre+ "no es un procedimiento ni una funcion");
		if(symbol instanceof SymbolFunction)
			tipoLlamada=TipoLlamada.FUNCION;
		else
			tipoLlamada=TipoLlamada.PROCEDIMIENTO;
		if(tipoLlamada==TipoLlamada.FUNCION)
		{
			SymbolFunction sf=(SymbolFunction) symbol;
			tipoDevuelto= sf.getTipoDevuelto();
			if(!((tipoDevuelto instanceof TypeSimple)||(tipoDevuelto instanceof TypePointer)))
				error.semanticFatalError("Tipo no aceptado de vuelta en " + symbol.getName());
		}
		
		

		//Comprobamos los argumentos
		SymbolProcedure sp=(SymbolProcedure) symbol;
		if(sp.getNombreAtributos().size()!=0)
			error.semanticFatalError(symbol.getName()+" no ha sido llamado con el numero correcto de parametros.");
		
		//Codigo intermedio
		IntermediateCodeBuilderIF cb = new IntermediateCodeBuilder (scopeManager.getCurrentScope());
		TemporalFactoryIF tf= CompilerContext.getTemporalFactory(scopeManager.getCurrentScope());
		TemporalIF tempFalso=tf.create();
		cb.addQuadruple("ACTUALIZAR-SP",tempFalso);
		secuenciaLLamada(cb,scopeManager);
		secuenciaSalida(0, cb, scopeManager);
		
		this.setIntermediateCode(cb.create());
	}
	
	private void secuenciaLLamada(IntermediateCodeBuilderIF cb, ScopeManagerIF scopeManager) 
	{;
		//Insertar el vinculo de acceso
		if(scopeManager.getCurrentScope().getSymbolTable().containsSymbol(nombre))
		{
			//llamada como hijo
			cb.addQuadruple("VINC-ACC-HIJO", null);
		}
		else if((scopeManager.getCurrentScope().getLevel())==(scopeManager.searchSymbol(nombre).getScope().getLevel()+1))
		{
			//llamada como hermano
			cb.addQuadruple("VINC-ACC-HERMANO", null);
		}
		/* no dejamos llamadas de hijos a antepasados*/
		
		//Insertar el vinculo de control
		cb.addQuadruple("VINC-CONTROL",null);
		
		//Cambiar el fp
		cb.addQuadruple("CAMBIAR-FP",null);
		
		//Almacenar la direccion de retorno y llamada
		SymbolIF symbol = scopeManager.searchSymbol(nombre);
		cb.addQuadruple("CALL", new Label(nombre));
	}
	
	
	private void secuenciaSalida(int argumentos, IntermediateCodeBuilderIF cb, ScopeManagerIF scopeManager)
	{
		TemporalFactoryIF tf= CompilerContext.getTemporalFactory(scopeManager.getCurrentScope());
		//Solo la extraccion de parametros, lo demas es previo a la llamada ret
		//El valor devuelto se ha dejado en un registro, (sera R9 pero no lo especificamos)
		temp=tf.create();
		if(tipoLlamada==TipoLlamada.FUNCION)
			cb.addQuadruple("GET-VALOR-DEVUELTO", null, temp);
		
		//reducir en 2+numeroArgumentos SP para eliminar el contexto saliente
		cb.addQuadruple("CAMBIAR-SP-EXTRAER-ARGUMENTOS", new Value(2+argumentos));
	}

	public LlamadaFuncionOProcedimiento(Token identificador, ValorParametros vp, ScopeManagerIF scopeManager, SemanticErrorManager error)
	{
		if(identificador==null||vp==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		nombre=identificador.getLexema()+"()";
		scope=scopeManager.getCurrentScope();
		SymbolIF symbol = scopeManager.searchSymbol(nombre);
		TypeIF type = scopeManager.searchType(nombre);
		if(symbol==null)
			error.semanticFatalError("No encontrada la funcion o el procedimiento "+ nombre);
		//no estoy seguro si se puede llamar a una funcion externa desde una interna
		if(scope.getLevel()>(symbol.getScope().getLevel()+1))
			error.semanticFatalError("La funcion "+ nombre + " no es alcanzable");
		if(!(symbol instanceof SymbolProcedure))
			error.semanticFatalError(nombre+ "no es un procedimiento ni una funcion");
		if(symbol instanceof SymbolFunction)
			tipoLlamada=TipoLlamada.FUNCION;
		else
			tipoLlamada=TipoLlamada.PROCEDIMIENTO;
		if(tipoLlamada==TipoLlamada.FUNCION)
		{
			SymbolFunction sf=(SymbolFunction) symbol;
			tipoDevuelto= sf.getTipoDevuelto();
			if(!((tipoDevuelto instanceof TypeSimple)||(tipoDevuelto instanceof TypePointer)))
				error.semanticFatalError("Tipo no aceptado de vuelta en " + symbol.getName());
		}
			
		//Comprobamos los argumentos
		SymbolProcedure sp=(SymbolProcedure) symbol;
		TypeProcedure tp=(TypeProcedure) type;
		if(sp.getNombreAtributos().size()!=vp.getValorParametros().size())
			error.semanticFatalError(symbol.getName()+" no ha sido llamado con el numero correcto de parametros.");
		for(int i=0; i<sp.getNombreAtributos().size();i++)
		{
			if(!comprobarArgumento(sp.getTipoAtributos().get(i), vp.getValorParametros().get(i),error))
				error.semanticFatalError(symbol.getName()+" no ha sido llamado con los parametros adecuados, deberia ser" + sp.getTipoAtributos().get(i) + " y se llama con " + vp.getValorParametros().get(i).getTypeBase());
			if((tp.getParametrosPaso().get(i)==PasoPor.REFERENCIA)&&(!(vp.getValorParametros().get(i).getValidoPorRef())))
				error.semanticFatalError(symbol.getName()+" se le ha pasado una expresion no valida para un paso por referencia");
		}
		
		
		//Codigo intermedio
		IntermediateCodeBuilderIF cb = new IntermediateCodeBuilder (scopeManager.getCurrentScope());
		TemporalFactoryIF tf= CompilerContext.getTemporalFactory(scopeManager.getCurrentScope());
		TemporalIF tempFalso=tf.create();
		for(int i=0; i<vp.getValorParametros().size();i++)
		{
			cb.addQuadruples(vp.getValorParametros().get(i).getIntermediateCode());
		}
		cb.addQuadruple("ACTUALIZAR-SP",tempFalso);
		for(int i=0; i<vp.getValorParametros().size();i++)
		{
			TemporalIF temp2;
			if(tp.getParametrosPaso().get(i)==PasoPor.REFERENCIA)
			{
				temp2=vp.getValorParametros().get(i).getTemp2();
				cb.addQuadruple("PUSH", temp2);
			}
			else if(tp.getParametros().get(i) instanceof TypeSet)
			{
				TemporalIF[] temporales=vp.getValorParametros().get(i).getConjunto();
				for(int j=0; j<temporales.length;j++)
					cb.addQuadruple("PUSH", temporales[j]);
			}
			else if(tp.getParametros().get(i) instanceof TypeRecord)
			{
				TemporalIF[] temporales=vp.getValorParametros().get(i).getConjunto();
				for(int j=0; j<temporales.length;j++)
					cb.addQuadruple("PUSH", temporales[j]);
			}
			else
			{
				temp2=vp.getValorParametros().get(i).getTemp();
				cb.addQuadruple("PUSH", temp2);
			}
		}
		secuenciaLLamada(cb,scopeManager);
		secuenciaSalida(vp.getValorParametros().size() ,cb, scopeManager);
		this.setIntermediateCode(cb.create());
	}

	private boolean comprobarArgumento(TypeBase typeBase, Expresion expresion, SemanticErrorManager error) 
	{
		if((expresion.getTypeBase() instanceof TypePointer) &&  (typeBase instanceof TypeSimple) &&(((TypeSimple)typeBase).getTipo()==Tipo.ENTERO))
		{
			error.semanticError("Warning: Se esta pasando una direccion de memoria a "+ nombre +" en vez de un entero");
			return true;
		}
		return expresion.getTypeBase().equals(typeBase);
	}
	
	public TypeBase getTypeBase()
	{
		return tipoDevuelto;
	}
	
	public TipoLlamada getTipoLlamada()
	{
		return tipoLlamada;
	}
	
	public String getNombre()
	{
		return nombre;
	}

	
	public TemporalIF getTemp()
	{
		if(tipoLlamada==TipoLlamada.FUNCION)
			return temp;
		else
			return null;
	}

	
}
