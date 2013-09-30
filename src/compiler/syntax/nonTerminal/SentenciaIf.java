package compiler.syntax.nonTerminal;

import compiler.CompilerContext;
import compiler.intermediate.Label;
import compiler.intermediate.Value;
import compiler.lexical.Token;
import compiler.semantic.type.TypeSimple;
import compiler.semantic.type.TypeSimple.Tipo;

import es.uned.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.compiler.intermediate.IntermediateCodeBuilderIF;
import es.uned.compiler.intermediate.LabelFactoryIF;
import es.uned.compiler.intermediate.LabelIF;
import es.uned.compiler.intermediate.TemporalFactory;
import es.uned.compiler.intermediate.TemporalFactoryIF;
import es.uned.compiler.intermediate.TemporalIF;
import es.uned.compiler.semantic.SemanticErrorManager;
import es.uned.compiler.semantic.symbol.ScopeIF;
import es.uned.compiler.semantic.symbol.ScopeManagerIF;

public class SentenciaIf extends NonTerminal
{

	public SentenciaIf(Token t, Expresion exp, BloqueSentencias bs, ParteElse pe, ScopeManagerIF scopeManager, SemanticErrorManager error)
	{
		if(t==null||exp==null||bs==null||pe==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		if(!((exp.getTypeBase() instanceof TypeSimple)&&(((TypeSimple)exp.getTypeBase()).getTipo()==Tipo.BOOLEANO)))
			error.semanticFatalError("La expresion tras el if tiene que ser tipo booleano", t);
		
		//Codigo Intermedio
		TemporalFactoryIF tf= CompilerContext.getTemporalFactory(scopeManager.getCurrentScope());
		LabelFactoryIF lf=CompilerContext.getLabelFactory();
		TemporalIF temp=exp.getTemp();
		LabelIF pElse=lf.create();
		LabelIF salir=lf.create();
		IntermediateCodeBuilderIF cb = new IntermediateCodeBuilder (scopeManager.getCurrentScope());
		cb.addQuadruples(exp.getIntermediateCode());
		cb.addQuadruple("COMP", temp, new Value(0));
		if(pe.getIntermediateCode().size()>0)
		{
			cb.addQuadruple("BZ", pElse);
		}
		else
		{
			cb.addQuadruple("BZ", salir);
		}
		cb.addQuadruples(bs.getIntermediateCode());
		cb.addQuadruple("BR", salir);
		if(pe.getIntermediateCode().size()>0)
		{
			cb.addQuadruple("LABEL", pElse);
			cb.addQuadruples(pe.getIntermediateCode());
		}
		
		cb.addQuadruple("LABEL", salir);
		this.setIntermediateCode(cb.create());
	}
	

}
