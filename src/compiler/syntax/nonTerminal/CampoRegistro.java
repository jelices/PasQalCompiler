package compiler.syntax.nonTerminal;

import java.util.ArrayList;

import compiler.CompilerContext;
import compiler.semantic.type.TypeSimple.Tipo;

public class CampoRegistro extends NonTerminal {

	private Tipo tipo;
	private ArrayList<String> identificadores;
	
	public CampoRegistro()
	{
		identificadores=new ArrayList<String>();
	}
	
	public CampoRegistro(ListaIdentificadores li, TipoPrimitivo tp)
	{
		if(li==null||tp==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		identificadores=li.getListaIdentificadores();
		tipo=tp.getTipo();
	}
	
	public Tipo getTipo()
	{
		return tipo;
	}
	
	public ArrayList<String> getListaIdentificadores()
	{
		return identificadores;
	}
}
