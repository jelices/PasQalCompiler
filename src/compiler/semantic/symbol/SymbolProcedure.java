package compiler.semantic.symbol;

import java.util.ArrayList;
import java.util.HashMap;

import es.uned.compiler.semantic.symbol.ScopeIF;
import es.uned.compiler.semantic.symbol.SymbolBase;
import es.uned.compiler.semantic.type.TypeBase;
import es.uned.compiler.semantic.type.TypeIF;

/**
 * Class for SymbolProcedure.
 */

// TODO: Student work
//       Include properties to characterize procedure calls

public class SymbolProcedure
    extends SymbolBase
{
   
	private ArrayList<TypeBase> atributos;
	private ArrayList<String> nombreAtributos;
	
    /**
     * Constructor for SymbolProcedure.
     * @param scope The declaration scope.
     * @param name The symbol name.
     * @param type The symbol type.
     */
	
    public SymbolProcedure (ScopeIF scope, 
                            String name,
                            TypeIF type)
    {
        super (scope, name.toLowerCase(), type);
        atributos=new ArrayList <TypeBase>();
        nombreAtributos=new ArrayList<String>();
    }
    
    public boolean containsValue (String name)
    {
        return nombreAtributos.contains(name.toLowerCase());
    }
    
    public TypeBase getSymbol (String name)
    {
    	int indice= nombreAtributos.indexOf(name.toLowerCase());
        return atributos.get(indice);
    }
    
    public ArrayList<String> getNombreAtributos()
    {
    	return nombreAtributos;
    }
    
    public void anadirAtributo(String nombre, TypeBase tipo)
    {
    	atributos.add(tipo);
    	nombreAtributos.add(nombre.toLowerCase());
    }
    
    public ArrayList<TypeBase> getTipoAtributos()
    {
    	return atributos;
    }
       
    /**
     * Compares this object with another one.
     * @param other the other object.
     * @return true if both objects has the same properties.
     */
    public boolean equals (Object other)
    {
    	if(!(other instanceof SymbolProcedure))
    		return false;
    	if(this.getName()!=((SymbolProcedure)other).getName())
        	return false;
    	if(this.getScope()!=((SymbolProcedure)other).getScope())
        	return false;
    	if(this.getType()!=((SymbolProcedure)other).getType())
        	return false;
    	ArrayList<String> nombreAtributosO=((SymbolProcedure)other).getNombreAtributos();
        if(nombreAtributos.size()!=nombreAtributosO.size())
        	return false;
        for(int i=0; i<nombreAtributos.size();i++)
        {
        	String nombreAtributo= nombreAtributos.get(i);
        	if(!(nombreAtributo.equals(nombreAtributosO.get(i))))
        		return false;
        	if((this.getSymbol(nombreAtributo)).equals(((SymbolProcedure)other).getSymbol(nombreAtributo)))
        		return false;
        }
        return true;
    }

    /**
     * Returns a hash code for the object.
     */
    public int hashCode ()
    {
        return super.hashCode() + nombreAtributos.hashCode()+atributos.hashCode();
    } 

    /**
     * Return a string representing the object.
     * @return a string representing the object.
     */
    public String toString ()
    {
        // TODO: Student Work (optional)
        return super.toString();
    } 
}
