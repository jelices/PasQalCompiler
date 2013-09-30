package compiler.semantic.type;

import es.uned.compiler.semantic.symbol.ScopeIF;
import es.uned.compiler.semantic.type.TypeBase;

/**
 * Class for TypeEnum.
 */

// TODO: Student work
//       Include properties to characterize enumeration type

public class TypeEnum
    extends TypeBase
{    
    
    /**
     * Constructor for TypeEnum.
     * @param scope The declaration scope.
     */
    public TypeEnum (ScopeIF scope)
    {
        super (scope);
    }

    /**
     * Constructor for TypeEnum.
     * @param scope The declaration scope
     * @param name The name of the type.
     */
    public TypeEnum (ScopeIF scope, String name)
    {
        super (scope, name);
    }
  

    /**
     * Compares this object with another one.
     * @param other the other object.
     * @return true if both objects has the same properties.
     */
    public final boolean equals (Object other)
    {
        // TODO: Student work
        return super.equals (other);
    }

    /**
     * Returns a hash code for the object.
     */
    public final int hashCode ()
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
        // TODO: Student work (optional)
        return super.toString ();
    }   
}
