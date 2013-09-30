package compiler.syntax.nonTerminal;

import compiler.CompilerContext;
import compiler.intermediate.Variable;
import compiler.lexical.Token;
import compiler.semantic.symbol.SymbolVariable;
import compiler.semantic.type.TypePointer;
import compiler.semantic.type.TypeSimple;
import compiler.semantic.type.TypeSimple.Tipo;

import es.uned.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.compiler.intermediate.IntermediateCodeBuilderIF;
import es.uned.compiler.intermediate.TemporalFactoryIF;
import es.uned.compiler.intermediate.TemporalIF;
import es.uned.compiler.semantic.SemanticErrorManager;
import es.uned.compiler.semantic.symbol.ScopeManagerIF;
import es.uned.compiler.semantic.symbol.SymbolIF;
import es.uned.compiler.semantic.type.TypeBase;

public class ExpresionPuntero extends NonTerminal 
{
	private TypeBase typeBase;
	private TemporalIF temp;
	
	public ExpresionPuntero( Token id, ScopeManagerIF scopeManager, SemanticErrorManager error)
	{
		if(id==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		typeBase=null;
		SymbolIF symbol = scopeManager.searchSymbol(id.getLexema());
		if(symbol==null)
			error.semanticFatalError("No existe la variable "+id.getLexema().toUpperCase());
		if(!(symbol instanceof SymbolVariable))
			error.semanticFatalError("No existe la variable "+id.getLexema().toUpperCase());
		if((!(symbol.getType() instanceof TypeSimple))||(((TypeSimple)symbol.getType()).getTipo()!=Tipo.ENTERO))
				error.semanticFatalError("La variable "+ id.getLexema().toUpperCase() +" no es entera");
			typeBase=new TypePointer(scopeManager.getCurrentScope());
			
		
		//Codigo Intermedio
		TemporalFactoryIF tf= CompilerContext.getTemporalFactory(scopeManager.getCurrentScope());
		temp=tf.create();
		IntermediateCodeBuilderIF cb = new IntermediateCodeBuilder (scopeManager.getCurrentScope());
		cb.addQuadruple("ASP", new Variable(symbol.getName(), symbol.getScope(), scopeManager.getCurrentScope()), temp);
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
