package compiler.syntax.nonTerminal;

import compiler.CompilerContext;
import compiler.intermediate.Value;
import compiler.intermediate.Variable;
import compiler.semantic.type.TypePointer;
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

public class SentenciaFor extends NonTerminal
{
	
	public SentenciaFor(SentenciaAsignacion sentencia, Expresion vfinal, BloqueSentencias bs, ScopeManagerIF scopeManager, SemanticErrorManager error)
	{
		if(sentencia==null||vfinal==null||bs==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		Referencia ref=sentencia.getReferencia();
		Expresion vinicial=sentencia.getExpresion();
		if(!((sentencia.getTypeBase() instanceof TypeSimple)&&(((TypeSimple)sentencia.getTypeBase()).getTipo()==Tipo.ENTERO)))
			error.semanticFatalError("La variable del for tiene que ser entera");
		if(!((vfinal.getTypeBase() instanceof TypeSimple)&&(((TypeSimple)vfinal.getTypeBase()).getTipo()==Tipo.ENTERO)))
			error.semanticFatalError("La finalizacion del for tiene que ser entera");
		
		
		//Codigo Intermedio
		TemporalFactoryIF tf= CompilerContext.getTemporalFactory(scopeManager.getCurrentScope());
		LabelFactoryIF lf=CompilerContext.getLabelFactory();
		LabelIF bucle=lf.create();
		LabelIF salir=lf.create();
		IntermediateCodeBuilderIF cb = new IntermediateCodeBuilder (scopeManager.getCurrentScope());
		
		TemporalIF t= vinicial.getTemp();
		cb.addQuadruples(sentencia.getIntermediateCode());
		cb.addQuadruples(vfinal.getIntermediateCode());
		cb.addQuadruple("LABEL", bucle);
		cb.addQuadruple("COMP", vfinal.getTemp(), t);
		cb.addQuadruple("BN", salir);
		cb.addQuadruples(bs.getIntermediateCode());
		cb.addQuadruple("INC", t);
		//copiar indice
		if(ref.esCampoRegistro())
		{
			TemporalIF temp2=tf.create();
			cb.addQuadruple("ASP", ref.getVariable(), temp2);
			cb.addQuadruple("SUB", temp2, new Value(ref.getOffset()), temp2); //Cambiar a ADD si la pila es creciente
			cb.addQuadruple("CPP", t, temp2);
		}
		else if(ref.esReferencia())
		{
			cb.addQuadruple("CPP", t, ref.getVariable());
		}
		else
		{
			cb.addQuadruple("MOVE", t, ref.getVariable());
		}
		
		cb.addQuadruple("BR", bucle);
		cb.addQuadruple("LABEL", salir);
		this.setIntermediateCode(cb.create());
	}
	

}
