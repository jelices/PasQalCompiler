package compiler.syntax.nonTerminal;

import compiler.CompilerContext;
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

public class ExpresionConjunto extends NonTerminal 
{
	private TypeBase typeBase;
	private TemporalIF tempInicio;
	private TemporalIF tempFin;
	
	public ExpresionConjunto(Expresion e1, Expresion e2, ScopeManagerIF scopeManager, SemanticErrorManager error)
	{
		if(e1==null||e2==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		if(!((e1.getTypeBase() instanceof TypeSimple)&&(((TypeSimple)e1.getTypeBase()).getTipo()==Tipo.ENTERO)))
			error.semanticFatalError("La expresion del inicio de rango no es entera");
		if(!((e2.getTypeBase() instanceof TypeSimple)&&(((TypeSimple)e2.getTypeBase()).getTipo()==Tipo.ENTERO)))
			error.semanticFatalError("La expresion de final de rango no es entera");
		typeBase=new TypeSet(scopeManager.getCurrentScope());
		((TypeSet)typeBase).setGenerico(true);
		
		//Codigo Intermedio
		tempInicio=e1.getTemp();
		tempFin=e2.getTemp();
		IntermediateCodeBuilderIF cb = new IntermediateCodeBuilder (scopeManager.getCurrentScope());
		cb.addQuadruples(e1.getIntermediateCode());
		cb.addQuadruples(e2.getIntermediateCode());
		this.setIntermediateCode(cb.create());
	}
	
	public ExpresionConjunto(ScopeManagerIF scopeManager, SemanticErrorManager error)
	{
		typeBase=new TypeSet(scopeManager.getCurrentScope());
		((TypeSet)typeBase).setGenerico(true);	
	}
	
	public TemporalIF getTempInicio()
	{
		return tempInicio;
	}
	
	public TemporalIF getTempFin()
	{
		return tempFin;
	}
	
	public TypeBase getTypeBase()
	{
		return typeBase;
	}
	

}
