package compiler.semantic.type;

import es.uned.compiler.semantic.symbol.ScopeIF;
import es.uned.compiler.semantic.type.TypeBase;


/**
 * Class for TypePointer.
 */

// TODO: Student work
//       Include properties to characterize pointer type

public class TypePointer
    extends TypeBase
{
    
    /**
     * Constructor for TypePointer.
     * @param scope The declaration scope.
     */
    public TypePointer (ScopeIF scope)
    {
        super (scope);
    }

    /**
     * Constructor for TypePointer.
     * @param scope The declaration scope
     * @param name The name of the type.
     */
    public TypePointer (ScopeIF scope, String name)
    {
        super (scope, name);
    }

    /**
     * Compares this object with another one.
     * @param other the other object.
     * @return true if both objects has the same properties.
     */
    public boolean equals (Object other)
    {
        if(!(other instanceof TypePointer))
        	return false;
        return true;
    }

    /**
     * Returns a hash code for the object.
     */
    public int hashCode ()
    {
        // TODO: Student work
        return super.hashCode ();
    }

    /**
     * Return a string representing the object.
     * @return a string representing the object.
     */
    public String toString ()
    {
        return "Tipo puntero";
    } 
}
