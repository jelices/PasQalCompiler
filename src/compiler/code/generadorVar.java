package compiler.code;

import java.util.HashMap;
import java.util.List;
import compiler.semantic.symbol.SymbolTable;
import compiler.semantic.symbol.SymbolVariable;
import compiler.semantic.type.TypePointer;
import compiler.semantic.type.TypeRecord;
import compiler.semantic.type.TypeSet;
import compiler.semantic.type.TypeSimple;
import es.uned.compiler.intermediate.VariableIF;
import es.uned.compiler.semantic.symbol.ScopeIF;

public class generadorVar {

	private static HashMap<ScopeIF,Integer> finalVariables=new HashMap<ScopeIF, Integer>();

	public static Integer getFinal(ScopeIF scope)
	{
		Integer fin= finalVariables.get(scope);
		if(fin==null)
		{
			fin=rellenarVariables(scope);
			finalVariables.put(scope, fin);
		}
		return fin;
	}
	
	private static int rellenarVariables(ScopeIF scope)
	{
		int contador=-2;
		int parametros=((SymbolTable)scope.getSymbolTable()).getNumeroArgumentos();
		List simbolos=scope.getSymbolTable().getSymbols();
		//Buscamos los parametros y asignamos el offset
		while (parametros!=0)
		{
			for(int i=0; i<simbolos.size(); i++)
			{
				if(simbolos.get(i) instanceof SymbolVariable)
				{
					SymbolVariable var= (SymbolVariable)simbolos.get(i);
					if(var.esParametro()&&(var.getNumeroParametro()==parametros))
					{
						if(var.getPorReferencia())
							contador--;
						else if((var.getType() instanceof TypeSimple)||(var.getType() instanceof TypePointer))
							contador--;
						else if(var.getType() instanceof TypeRecord)
								contador-=((TypeRecord)var.getType()).getTamanyo();
						else if(var.getType() instanceof TypeSet)
							contador-=((TypeSet)var.getType()).getTamanyo();
						var.setDireccion(contador);
						parametros--;
					}
				}
			}
		}
		
		contador=1;
		//Asignamos las variables locales
		for(int i=0; i<simbolos.size(); i++)
		{
			if(simbolos.get(i) instanceof SymbolVariable)
			{
				SymbolVariable var= (SymbolVariable)simbolos.get(i);
				if(!var.esParametro())
				{
					var.setDireccion(contador);
					if((var.getType() instanceof TypeSimple)||(var.getType() instanceof TypePointer))
						contador++;
					else if(var.getType() instanceof TypeRecord)
							contador+=((TypeRecord)var.getType()).getTamanyo();
					else if(var.getType() instanceof TypeSet)
						contador+=((TypeSet)var.getType()).getTamanyo();
				}
			}
		}
		return contador;
	}

	public static int getOffset(VariableIF result) 
	{
		getFinal(result.getScope());
		return ((SymbolVariable)result.getScope().getSymbolTable().getSymbol(result.getName())).getOffset();
	}
	
	
}
