package compiler.syntax.nonTerminal;

import compiler.CompilerContext;
import compiler.intermediate.Cadena;
import compiler.intermediate.Value;
import compiler.intermediate.Variable;
import compiler.lexical.Token;
import compiler.semantic.symbol.SymbolVariable;
import compiler.semantic.type.TypeSet;
import compiler.semantic.type.TypeSimple;
import compiler.semantic.type.TypeSimple.Tipo;

import es.uned.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.compiler.intermediate.IntermediateCodeBuilderIF;
import es.uned.compiler.intermediate.LabelFactoryIF;
import es.uned.compiler.intermediate.LabelIF;
import es.uned.compiler.intermediate.TemporalFactoryIF;
import es.uned.compiler.intermediate.TemporalIF;
import es.uned.compiler.semantic.SemanticErrorManager;
import es.uned.compiler.semantic.symbol.ScopeManagerIF;
import es.uned.compiler.semantic.symbol.SymbolIF;
import es.uned.compiler.semantic.type.TypeBase;

public class ExpresionPertenencia extends NonTerminal 
{
	
	private TypeBase typeBase;
	private TemporalIF temp;
	
	public ExpresionPertenencia (Expresion exp, Token t, ScopeManagerIF scopeManager, SemanticErrorManager error)
	{
		if(exp==null||t==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		String nombre=t.getLexema();
		SymbolIF symbol = scopeManager.searchSymbol(nombre);	
		if(symbol==null)
			error.semanticFatalError("La variable "+ nombre.toUpperCase() +" no se ha definido");
		if(!(symbol instanceof SymbolVariable))
			error.semanticFatalError(nombre.toUpperCase() + " no es una variable");
		if(!((SymbolVariable)symbol).asignado())
			error.semanticDebug("Warning: La variable "+ nombre +" puede no haberse inicializado.");
		TypeBase tipoReferencia =(TypeBase)((SymbolVariable) symbol).getType();
		if(!(tipoReferencia instanceof TypeSet))
			error.semanticFatalError(nombre.toUpperCase() + " no es un conjunto");
		if(!((exp.getTypeBase() instanceof TypeSimple)&&(((TypeSimple)exp.getTypeBase()).getTipo()==Tipo.ENTERO)))
			error.semanticFatalError("La expresion previa al IN de " + nombre.toUpperCase() + " no es entera");
	
		typeBase=new TypeSimple(scopeManager.getCurrentScope(), Tipo.BOOLEANO);
		
		//Codigo Intermedio
		TemporalFactoryIF tf= CompilerContext.getTemporalFactory(scopeManager.getCurrentScope());
		LabelFactoryIF lf=CompilerContext.getLabelFactory();
		LabelIF errorL=lf.create();
		LabelIF salirL=lf.create();
		IntermediateCodeBuilderIF cb = new IntermediateCodeBuilder (scopeManager.getCurrentScope());
		temp=tf.create();
		TemporalIF temp3=tf.create();
		TemporalIF temp2=exp.getTemp();
		cb.addQuadruples(exp.getIntermediateCode());
		cb.addQuadruple("COMP", temp2, new Value(((TypeSet)tipoReferencia).getInicio()));
		cb.addQuadruple("BN", errorL);
		cb.addQuadruple("COMP", new Value(((TypeSet)tipoReferencia).getFin()),temp2);
		cb.addQuadruple("BN", errorL);
		cb.addQuadruple("ASP", new Variable(nombre, symbol.getScope(), scopeManager.getCurrentScope()), temp3);
		cb.addQuadruple("SUB", temp2,new Value(((TypeSet)tipoReferencia).getInicio()),temp2);
		cb.addQuadruple("SUB", temp3, temp2,temp3); //Cambiar con pila creciente a ADD
		cb.addQuadruple("REF", temp3, temp);
		cb.addQuadruple("BR", salirL);
		cb.addQuadruple("LABEL", errorL);
		cb.addQuadruple("MOVE", new Value(0), temp);
		cb.addQuadruple("LABEL", salirL);
		this.setIntermediateCode(cb.create());
	}
	
	public TypeBase getTypeBase()
	{
		return typeBase;
	}
	
	public TemporalIF getTemp()
	{
		return temp;
	}

}
