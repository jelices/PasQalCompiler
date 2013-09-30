package compiler.semantic.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import es.uned.compiler.semantic.symbol.ScopeIF;
import es.uned.compiler.semantic.type.TypeIF;
import es.uned.compiler.semantic.type.TypeTableIF;

/**
 * Class for TypeTable.
 */

public class TypeTable 
    implements TypeTableIF
{

    private ScopeIF scope = null;
    private HashMap<String, TypeIF> table;
    
    /**
     * Constructor for TypeTable.
     */
    public TypeTable ()
    {     
    	table=new HashMap<String, TypeIF>();
    }
    
    /**
     * Constructor for TypeTable.
     * @param scope The scope where type table is defined.
     */
    public TypeTable (ScopeIF scope)
    {   
        this ();
        this.scope = scope;
    }
    
    /**
     * Returns the scope of this Symbol table. 
     * @return the scope of this Symbol table. 
     */
    public ScopeIF getScope ()
    {
        return  scope;
    }
    
    /**
     * Looks up a type by name.
     * @param name The name of the type.
     * @return A type.
     */
    public TypeIF getType (String name)
    {
    	return table.get(name.toLowerCase());
    }
    
    /**
     * Adds a new type in the type table.
     * @param type the type.
     */
    public void addType (TypeIF type)
    {
    	table.put(type.getName().toLowerCase(), type);
    }
    
    /**
     * Sets a type in the type table.
     * @param name the type name.
     * @param type the type.
     */
    public void addType (String name, TypeIF type)
    {
    	table.put(name.toLowerCase(), type);
    }
    
    /**
     * Returns the list of types.
     * @return A list of types.
     */
    public List<TypeIF> getTypes ()
    {    
    	return new ArrayList<TypeIF>(table.values());
    }
    
    /**
     * Indicates whether the type is contained in the type table.
     * @param type The type. 
     * @return True if the symbol is contained in the type table.
     */
    public boolean containsType (TypeIF symbol)
    {
    	return table.containsValue(symbol);
    }
    
    /**
     * Indicates whether the type is contained in the type table.
     * @param name The type name. 
     * @return True if the type is contained in the type table.
     */
    public boolean containsType (String name)
    {
    	return table.containsKey(name.toLowerCase());
    }
    
    /**
     * Compares this object with another one.
     * @param other the other object.
     * @return true if two objects has the same properties.
     */
    public boolean equals (Object other)
    {
    	if(!(other instanceof TypeTable))
        	return false;
        if(!(((TypeTable)other).getScope().getId()==this.getScope().getId()))
        	return false;
       List<TypeIF> l =((TypeTable)other).getTypes();
       for(int i=0;i <l.size();i++)
       {
    	   if(!this.containsType(l.get(i)))
    		   return false;
       }
       return true;
    }

    /**
     * Returns a hash code for the object.
     */
    public int hashCode ()
    {
    	return table.values().hashCode();
    } 

    /**
     * Return a string representing the object.
     * @return a string representing the object.
     */
    
    public String toString ()
    {
    	List<TypeIF> lista=getTypes();
    	String cadena= "Tabla de tipos de " + scope.getName() +"\n";
    	cadena+= "Nombre \t Tipo\n------------------\n";
    	for(int i=0; i<lista.size();i++)
    	{
    		cadena+=lista.get(i).getName()+"\t";
    		cadena+= lista.get(i)+"\n";
    	}
    	return cadena;
    } 
}
