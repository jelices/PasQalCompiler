package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.HashMap;

import compiler.CompilerContext;
import compiler.semantic.type.TypeSimple.Tipo;


public class CamposRegistro extends NonTerminal 
{
	private HashMap<String, Tipo> camposRegistro;
	private ArrayList<String> nombreCamposRegistro;
	
	public CamposRegistro()
	{
		camposRegistro=new HashMap<String, Tipo>();
		nombreCamposRegistro=new ArrayList<String>();
	}
	
	public CamposRegistro(CampoRegistro campo)
	{
		this();
		if(campo==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		ArrayList<String> nombres= campo.getListaIdentificadores();
		for(int i=0; i<nombres.size(); i++)
		{
			nombreCamposRegistro.add(nombres.get(i));
			camposRegistro.put(nombres.get(i), campo.getTipo());
		}
	}
	
	public CamposRegistro(CampoRegistro campo, CamposRegistro campos)
	{
		if(campo==null||campos==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		camposRegistro=campos.getCamposRegistro();
		nombreCamposRegistro=campos.getNombreCamposregistro();
		ArrayList<String> nombres= campo.getListaIdentificadores();
		for(int i=0; i<nombres.size(); i++)
		{
			nombreCamposRegistro.add(nombres.get(i));
			camposRegistro.put(nombres.get(i), campo.getTipo());
		}
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
