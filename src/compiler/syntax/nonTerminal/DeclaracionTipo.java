package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.HashMap;

import compiler.CompilerContext;
import compiler.lexical.Token;
import compiler.semantic.symbol.SymbolTable;
import compiler.semantic.type.TypePointer;
import compiler.semantic.type.TypeRecord;
import compiler.semantic.type.TypeSet;
import compiler.semantic.type.TypeSimple;
import compiler.semantic.type.TypeTable;
import compiler.semantic.type.TypeSimple.Tipo;
import compiler.syntax.nonTerminal.DefinicionTipo.TiposCompuesto;
import es.uned.compiler.semantic.SemanticErrorManager;
import es.uned.compiler.semantic.symbol.ScopeIF;
import es.uned.compiler.semantic.type.TypeBase;

public class DeclaracionTipo extends NonTerminal 
{
	private String nombre;

	private TiposCompuesto tipo;
	private HashMap<String, Tipo> camposRegistro;
	private ArrayList<String> nombreCamposRegistro;
	private int inicio;
	private int fin;
	
	public DeclaracionTipo(Token identificador, DefinicionTipo tipo)
	{
		if(tipo==null||identificador==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		nombre=identificador.getLexema();
		this.tipo=tipo.getTipo();
		if(this.tipo==TiposCompuesto.Registro)
		{
			camposRegistro=tipo.getCamposRegistro();
			nombreCamposRegistro=tipo.getNombreCamposregistro();
		}
		if(this.tipo==TiposCompuesto.Conjunto)
		{
			inicio=tipo.getInicio();
			fin=tipo.getFin();
		}
	}
	
	private TypeBase getObtenerTipo(ScopeIF scope, SemanticErrorManager error)
	{
		if(tipo==TiposCompuesto.Registro)
		{
			TypeRecord t=new TypeRecord(scope);
			t.setName(nombre);
			for(int i=0; i<nombreCamposRegistro.size(); i++)
			{
				Tipo tipoD=camposRegistro.get(nombreCamposRegistro.get(i));
				if(t.containsValue(nombreCamposRegistro.get(i)))
					error.semanticFatalError("El registro "+ nombre.toUpperCase() +" tiene el campo " + nombreCamposRegistro.get(i).toUpperCase() +" repetido.");
				if((tipoD==Tipo.BOOLEANO)||(tipoD==Tipo.ENTERO))
					t.anadirCampo(nombreCamposRegistro.get(i), new TypeSimple(scope, tipoD));
				if(tipoD==Tipo.PUNTERO)
					t.anadirCampo(nombreCamposRegistro.get(i), new TypePointer(scope));
			}
			return t;
		}
		if(tipo==TiposCompuesto.Conjunto)
		{
			TypeSet t=new TypeSet(scope,nombre,inicio,fin);
			if(inicio>fin)
				error.semanticFatalError("El conjunto "+ nombre.toUpperCase()+" tiene el inicio mayor que el fin");
			return t;
		}
		return null;
	}
	
	public void anadirTabla(ScopeIF scope, SemanticErrorManager error)
	{
		TypeTable table= (TypeTable) scope.getTypeTable();
		if(table.containsType(nombre))
			error.semanticFatalError("El tipo de datos " + nombre.toUpperCase() + " esta repetido");
		table.addType(getObtenerTipo(scope, error));
	}
	

}
