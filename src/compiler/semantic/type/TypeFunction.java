package compiler.semantic.type;

import es.uned.compiler.semantic.symbol.ScopeIF;
import es.uned.compiler.semantic.type.TypeBase;
import compiler.semantic.type.TypeProcedure;


/**
 * Class for TypeFunction.
 */



public class TypeFunction
    extends TypeProcedure
{   
	
	private TypeBase devuelve;
    
    /**
     * Constructor for TypeFunction.
     * @param scope The declaration scope.
     */
    public TypeFunction (ScopeIF scope)
    {
        super (scope);
    }

    /**
     * Constructor for TypeFunction.
     * @param scope The declaration scope
     * @param name The name of the function.
     */
    public TypeFunction (ScopeIF scope, String name)
    {
        super (scope, name.toLowerCase());
    }
    
    public TypeBase getDevuelve()
    {
    	return devuelve;
    }
    
    public void setDevuelve(TypeBase t)
    {
    	devuelve=t;
    }

    /**
     * Compares this object with another one.
     * @param other the other object.
     * @return true if both objects has the same properties.
     */
    public boolean equals (Object other)
    {
    	if(!(other instanceof TypeFunction))
    		return false;
        if(!super.equals (other))
        	return false;
        return devuelve.equals(((TypeFunction)other).getDevuelve());
    }

    /**
     * Returns a hash code for the object.
     */
    public int hashCode ()
    {
        return super.hashCode ()+devuelve.hashCode();
    }

    /**
     * Return a string representing the object.
     * @return a string representing the object.
     */
    public String toString ()
    {
    	String cadena="Funcion "+ this.getName() +"Devuelve: "+ devuelve +" Parametros: " ;
    	for(int i=0; i<getParametros().size(); i++)
    	{
    		cadena+=getParametros().get(i) + " por " + getParametrosPaso().get(i) +", ";
    	}
        return cadena;
    }
}
