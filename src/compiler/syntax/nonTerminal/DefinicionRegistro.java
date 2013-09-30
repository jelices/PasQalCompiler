package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.HashMap;

import compiler.CompilerContext;
import compiler.semantic.type.TypeSimple.Tipo;

public class DefinicionRegistro extends NonTerminal {

	private HashMap<String, Tipo> camposRegistro;
	private ArrayList<String> nombreCamposRegistro;
	
	public DefinicionRegistro(CamposRegistro campos)
	{
		if(campos==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		camposRegistro=campos.getCamposRegistro();
		nombreCamposRegistro=campos.getNombreCamposregistro();
	}
	
	public ArrayList<String> getNombreCamposregistro()
	{
		return nombreCamposRegistro;
	}
	
	public HashMap<String, Tipo> getCamposRegistro()
	{
		return camposRegistro;
	}
	
}
