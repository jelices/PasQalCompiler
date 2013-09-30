package compiler.semantic.type;

import es.uned.compiler.semantic.symbol.ScopeIF;
import es.uned.compiler.semantic.type.TypeBase;

/**
 * Class for TypeUnion.
 */

// TODO: Student work
//       Include properties to characterize unions

public class TypeUnion
    extends TypeBase
{   
       
    /**
     * Constructor for TypeUnion.
     * @param scope The scope.
     */
    public TypeUnion (ScopeIF scope)
    {
        super (scope);
    }

    /**
     * Constructor for TypeUnion.
     * @param scope The scope
     * @param name The name.
     */
    public TypeUnion (ScopeIF scope, String name)
    {
        super (scope, name);
    }

    /**
     * Constructor for TypeRecord.
     * @param record The record to copy.
     */
    public TypeUnion (TypeUnion record)
    {
        super (record.getScope (), record.getName ());
    }

    /**
     * Compares this object with another one.
     * @param other the other object.
     * @return true if both objects has the same properties.
     */
    public boolean equals (Object other)
    {
        // TODO: Student work
        return super.equals (other);
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
        // TODO: Student work (optional)
        return super.toString ();
    }   
}
