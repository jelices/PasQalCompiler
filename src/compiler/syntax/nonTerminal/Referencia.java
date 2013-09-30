package compiler.syntax.nonTerminal;


import compiler.CompilerContext;
import compiler.intermediate.Value;
import compiler.intermediate.Variable;
import compiler.lexical.Token;
import compiler.semantic.symbol.SymbolConstant;
import compiler.semantic.symbol.SymbolVariable;
import compiler.semantic.type.TypePointer;
import compiler.semantic.type.TypeRecord;
import compiler.semantic.type.TypeSet;
import compiler.semantic.type.TypeSimple;
import compiler.semantic.type.TypeSimple.Tipo;
import es.uned.compiler.intermediate.OperandIF;
import es.uned.compiler.lexical.TokenIF;
import es.uned.compiler.semantic.SemanticErrorManager;
import es.uned.compiler.semantic.symbol.ScopeManagerIF;
import es.uned.compiler.semantic.symbol.SymbolIF;
import es.uned.compiler.semantic.type.TypeBase;

public class Referencia extends NonTerminal {

	public enum TipoModificacion{CONSTANTE, VARIABLE};
	
	private TipoModificacion tipoModificacion;
	private TypeBase tipoReferencia;
	private SymbolIF simbReferencia;
	private String nombre;
	
	//codigo intermedio
	private boolean esCampoRegistro;
	private boolean esReferencia; //si es puntero^ o no (puntero solo no seria)
	private OperandIF variable;
	private int offset;
	
	public Referencia(Token token, ScopeManagerIF scopeManager, SemanticErrorManager error)
	{
		if(token==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		esReferencia=false;
		esCampoRegistro=false;
		offset=0;
		nombre=token.getLexema();
		SymbolIF symbol = scopeManager.searchSymbol(nombre);
		simbReferencia=symbol;
		if(symbol==null)
			error.semanticFatalError("La variable "+ nombre.toUpperCase() +" no se ha definido");
		if(!(symbol instanceof SymbolVariable)&&!(symbol instanceof SymbolConstant))
			error.semanticFatalError(nombre.toUpperCase() + " no es una variable o constante");
		if(symbol instanceof SymbolConstant)
		{
			tipoModificacion=TipoModificacion.CONSTANTE;
			SymbolConstant constante= (SymbolConstant) symbol;
			tipoReferencia=(TypeSimple)constante.getType();
		}	
		else
		{
			tipoModificacion=TipoModificacion.VARIABLE;
			tipoReferencia =(TypeBase)((SymbolVariable) symbol).getType();
			if(!((tipoReferencia instanceof TypeSimple)||(tipoReferencia instanceof TypePointer)
					||(tipoReferencia instanceof TypeRecord)
					||(tipoReferencia instanceof TypeSet)))
				error.semanticFatalError(nombre.toUpperCase() + " no es de un tipo valido");
			esReferencia=((SymbolVariable) symbol).getPorReferencia();
		}
		
		
		//Codigo intermedio
		if(tipoModificacion==TipoModificacion.CONSTANTE)
		{
			SymbolConstant constante= (SymbolConstant) symbol;
			if(((TypeSimple)constante.getType()).getTipo()==Tipo.ENTERO)
				variable=new Value((Integer)constante.getValor());
			if(((TypeSimple)constante.getType()).getTipo()==Tipo.BOOLEANO)
			{
				boolean valor=(Boolean)constante.getValor();
				if(valor)
					variable=new Value(1);
				else
					variable=new Value(0);
			}
		}
		else
			variable=new Variable(token.getLexema(), symbol.getScope(), scopeManager.getCurrentScope());
	}
	
	public Referencia(Token token, String caret, ScopeManagerIF scopeManager, SemanticErrorManager error)
	{
		nombre=token.getLexema();
		esReferencia=true;
		esCampoRegistro=false;
		offset=0;
		tipoModificacion=TipoModificacion.VARIABLE;
		SymbolIF symbol = scopeManager.searchSymbol(token.getLexema());
		simbReferencia=symbol;
		if(symbol==null)
			error.semanticFatalError("No existe la variable "+token.getLexema().toUpperCase());
		if(!(symbol instanceof SymbolVariable))
			error.semanticFatalError("No existe la variable "+token.getLexema().toUpperCase());
		TypeBase tb=(TypeBase) ((SymbolVariable) symbol).getType();
		if(!(tb instanceof TypePointer))
			error.semanticFatalError("La variable "+token.getLexema().toUpperCase() + " no es un puntero");
		tipoReferencia=new TypeSimple(scopeManager.getCurrentScope(), Tipo.ENTERO);
		variable=new Variable(token.getLexema(), symbol.getScope(), scopeManager.getCurrentScope());
	}
	
	public Referencia(Token token, Token token2, ScopeManagerIF scopeManager, SemanticErrorManager error)
	{
		if(token==null||token2==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		nombre=token.getLexema()+"."+token2.getLexema();
		tipoModificacion=TipoModificacion.VARIABLE;
		SymbolIF symbol = scopeManager.searchSymbol(token.getLexema());
		if(symbol==null)
			error.semanticFatalError("No existe la variable "+token.getLexema().toUpperCase());
		if(!(symbol instanceof SymbolVariable))
			error.semanticFatalError("No existe la variable "+token.getLexema().toUpperCase());
		TypeBase tb=(TypeBase) ((SymbolVariable) symbol).getType();
		esReferencia=((SymbolVariable) symbol).getPorReferencia();
		simbReferencia=symbol;
		if(!(tb instanceof TypeRecord))
			error.semanticFatalError("La variable "+token.getLexema().toUpperCase() + " no es un registro");
		TypeRecord tr=(TypeRecord)tb;
		tipoReferencia=tr.getType(token2.getLexema());
		if(tipoReferencia==null)
			error.semanticFatalError("El campo "+ token2.getLexema().toUpperCase() +" no existe dentro de "+ tr);
		if(!((tipoReferencia instanceof TypeSimple)||(tipoReferencia instanceof TypePointer)))
			error.semanticFatalError("El campo "+ token2.getLexema().toUpperCase() +" no es primitivo");
		
		//codigo intermedio
		offset=tr.getOffset(token2.getLexema());
		esCampoRegistro=true;
		variable=new Variable(token.getLexema(), symbol.getScope(), scopeManager.getCurrentScope());	
	}
	
	
	public SymbolIF getSimbolo()
	{
		return simbReferencia;
	}
	
	public TypeBase getTypeBase()
	{
		return tipoReferencia;
	}
	
	public TipoModificacion getTipoModificacion()
	{
		return tipoModificacion;
	}
	
	public String getNombre()
	{
		return nombre;
	}
	
	public OperandIF getVariable()
	{
		return variable;
	}
	
	public boolean esCampoRegistro()
	{
		return esCampoRegistro;
	}
	
	public boolean esReferencia()
	{
		return esReferencia;
	}
	public int getOffset()
	{
		return offset;
	}
}
