package compiler.semantic.type;

import es.uned.compiler.semantic.symbol.ScopeIF;
import es.uned.compiler.semantic.type.TypeBase;


/**
 * Class for TypeSet.
 */

// TODO: Student work
//       Include properties to characterize sets

public class TypeSet
    extends TypeBase
{
    
	private int inicio;
	private int fin;
	private boolean generico=false;
    /**
     * Constructor for TypeSet.
     * @param scope The declaration scope.
     */
    public TypeSet (ScopeIF scope)
    {
        super (scope);
    }

    /**
     * Constructor for TypeSet.
     * @param scope The declaration scope.
     * @param name The name of the type.
     */
    public TypeSet (ScopeIF scope, String name)
    {
        super (scope, name.toLowerCase());
    }

    /**
     * Compares this object with another one.
     * @param other the other object.
     * @return true if both objects has the same properties.
     */
    public TypeSet (ScopeIF scope, String name, int inicio, int fin)
    {
        super (scope, name.toLowerCase());
        this.setInicio(inicio);
        this.setFin(fin);
    }
    
    public int getInicio()
    {
    	return inicio;
    }
    
    public void setInicio(int inicio)
    {
    	this.inicio=inicio;
    }

    public int getFin()
    {
    	return fin;
    }
    
    public void setFin(int fin)
    {
    	this.fin=fin;
    }

    public void setGenerico(boolean generico)
    {
    	this.generico=generico;
    }
    
    public boolean getGenerico()
    {
    	return generico;
    }
    public boolean equals (Object other)
    {
    	if(!(other instanceof TypeSet))
        	return false;
        if(this.getName()!=((TypeSet)other).getName())
        	return false;
        if(this.getScope()!=((TypeSet)other).getScope())
        	return false;
        return (inicio==((TypeSet)other).getInicio())&&(fin==((TypeSet)other).getFin());
    }

    /**
     * Returns a hash code for the object.
     */
    public int hashCode ()
    {
        return getName().hashCode()+getScope().hashCode()+255*inicio+127*fin;
    }

    /**
     * Return a string representing the object.
     * @return a string representing the object.
     */
    public String toString ()
    {
        return "Tipo conjunto "+ this.getName() +" de "+inicio +" a "+fin;
    }
    
    public int getTamanyo()
    {
    	return (fin- inicio)+1;
    }
}
