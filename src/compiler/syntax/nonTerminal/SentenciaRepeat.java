package compiler.syntax.nonTerminal;

import compiler.CompilerContext;
import compiler.intermediate.Value;
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

public class SentenciaRepeat extends NonTerminal {

	public SentenciaRepeat(ListaSentencias ls, Expresion exp, ScopeManagerIF scopeManager, SemanticErrorManager error)
	{
		if(ls==null||exp==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		if(ls.getVacia())
			error.semanticError("Warning: el repeat no tiene ninguna sentencia");
		if(!((exp.getTypeBase() instanceof TypeSimple)&&(((TypeSimple)exp.getTypeBase()).getTipo()==Tipo.BOOLEANO)))
			error.semanticFatalError("Condicion del repeat no valida, tiene que ser booleana");
		
		//Codigo intermedio
		LabelFactoryIF lf=CompilerContext.getLabelFactory();
		TemporalIF temp=exp.getTemp();
		LabelIF bucle = lf.create();
		IntermediateCodeBuilderIF cb = new IntermediateCodeBuilder (scopeManager.getCurrentScope());
		cb.addQuadruple("LABEL", bucle);
		cb.addQuadruples(ls.getIntermediateCode());
		cb.addQuadruples(exp.getIntermediateCode());
		cb.addQuadruple("COMP",temp, new Value(0));
		cb.addQuadruple("BZ", bucle);
		
		this.setIntermediateCode(cb.create());
	}
}
