package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.HashMap;

import compiler.CompilerContext;
import compiler.semantic.type.TypeSimple.Tipo;

public class DefinicionTipo extends NonTerminal {

	public enum TiposCompuesto{Registro, Conjunto};
	private TiposCompuesto tipo;
	
	private HashMap<String, Tipo> camposRegistro;
	private ArrayList<String> nombreCamposRegistro;
	private int inicio;
	private int fin;
	
	
	public DefinicionTipo(DefinicionRegistro registro)
	{
		if(registro==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		tipo=TiposCompuesto.Registro;
		camposRegistro=registro.getCamposRegistro();
		nombreCamposRegistro=registro.getNombreCamposregistro();
	}
	
	public DefinicionTipo(DefinicionConjunto conjunto)
	{
		if(conjunto==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		tipo=TiposCompuesto.Conjunto;
		inicio=conjunto.getInicio();
		fin=conjunto.getFin();
	}
	
	public TiposCompuesto getTipo()
	{
		return tipo;
	}
	
	public ArrayList<String> getNombreCamposregistro()
	{
		return nombreCamposRegistro;
	}
	
	public HashMap<String, Tipo> getCamposRegistro()
	{
		return camposRegistro;
	}
	
	public int getInicio()
	{
		return inicio;
	}
	
	public int getFin()
	{
		return fin;
	}
	
	
}