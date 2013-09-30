package compiler.syntax.nonTerminal;

import java.util.ArrayList;

import compiler.CompilerContext;
import compiler.semantic.type.TypeProcedure.PasoPor;
import compiler.semantic.type.TypeSimple.Tipo;
import compiler.syntax.nonTerminal.TipoVariable.TipoVar;

public class Argumento extends NonTerminal {

	private TipoVar tipoVar;
	private Tipo tipo;
	private String identificador;
	private ArrayList<String> identificadores;
	private PasoPor pasoPor;
	
	public Argumento (ListaIdentificadores li, TipoVariable tv, boolean porValor)
	{
		if(li==null||tv==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		identificadores=li.getListaIdentificadores();
		tipoVar=tv.getTipoVar();
		if(tipoVar==TipoVar.PRIMITIVA)
			tipo=tv.getTipo();
		if(tipoVar==TipoVar.COMPUESTA)
			identificador=tv.getIdentificador();
		if (porValor)
			pasoPor=PasoPor.VALOR;
		else
			pasoPor=PasoPor.REFERENCIA;
	}
	
	public TipoVar getTipoVar()
	{
		return tipoVar;
	}
	
	public Tipo getTipo()
	{
		return tipo;
	}
	
	public String getIdentificador()
	{
		return identificador;
	}
	public ArrayList<String> getListaIdentificadores()
	{
		return identificadores;
	}
	
	public PasoPor getPasoPor()
	{
		return pasoPor;
	}
}
