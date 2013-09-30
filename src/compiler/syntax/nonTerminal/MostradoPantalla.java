package compiler.syntax.nonTerminal;

import compiler.CompilerContext;
import compiler.intermediate.Cadena;
import compiler.lexical.Token;
import compiler.semantic.type.TypePointer;
import compiler.semantic.type.TypeSimple;
import compiler.semantic.type.TypeSimple.Tipo;

import es.uned.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.compiler.intermediate.IntermediateCodeBuilderIF;
import es.uned.compiler.intermediate.TemporalIF;
import es.uned.compiler.semantic.SemanticErrorManager;
import es.uned.compiler.semantic.symbol.ScopeManagerIF;

public class MostradoPantalla extends NonTerminal {
	
	public MostradoPantalla(Expresion exp, ScopeManagerIF scopeManager, SemanticErrorManager error)
	{
		if(exp==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		if(!((exp.getTypeBase() instanceof TypeSimple)&&(((TypeSimple)exp.getTypeBase()).getTipo()==Tipo.ENTERO)))
			if(!(exp.getTypeBase() instanceof TypePointer))
				error.semanticFatalError("Write solo admite expresiones enteras o cadenas");
	
		//Codigo Intermedio
		TemporalIF temp=exp.getTemp();
		IntermediateCodeBuilderIF cb = new IntermediateCodeBuilder (scopeManager.getCurrentScope());
		cb.addQuadruples(exp.getIntermediateCode());
		cb.addQuadruple("WRTINT", temp);
		this.setIntermediateCode(cb.create());
		
	}

	public MostradoPantalla(Token cadena, ScopeManagerIF scopeManager)
	{
		if(cadena==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		IntermediateCodeBuilderIF cb = new IntermediateCodeBuilder (scopeManager.getCurrentScope());
		cb.addQuadruple("WRTSTR", new Cadena(cadena.getLexema()));
		this.setIntermediateCode(cb.create());
	}

}
