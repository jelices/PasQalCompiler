package compiler.syntax.nonTerminal;

import compiler.CompilerContext;
import compiler.syntax.nonTerminal.LlamadaFuncionOProcedimiento.TipoLlamada;
import es.uned.compiler.semantic.SemanticErrorManager;

public class Sentencia extends NonTerminal {
	
	public Sentencia(SentenciaAsignacion sa)
	{
		if(sa==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		this.setIntermediateCode(sa.getIntermediateCode());
	}
	
	public Sentencia(SentenciaIf s)
	{
		if(s==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		this.setIntermediateCode(s.getIntermediateCode());
	}
	
	public Sentencia(SentenciaFor s)
	{
		if(s==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		this.setIntermediateCode(s.getIntermediateCode());
	}
	
	public Sentencia(SentenciaRepeat s)
	{
		if(s==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		this.setIntermediateCode(s.getIntermediateCode());
	}
	
	public Sentencia(LlamadaFuncionOProcedimiento lfp, SemanticErrorManager error)
	{
		if(lfp==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		if(lfp.getTipoLlamada()==TipoLlamada.FUNCION)
			error.semanticError("Warning: Estas llamando a una funcion sin asignarle el valor de retorno");
		//Codigo intermedio
		this.setIntermediateCode(lfp.getIntermediateCode());
	}
	
	public Sentencia(SentenciaES s)
	{
		if(s==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		this.setIntermediateCode(s.getIntermediateCode());
	}
	
	
}
