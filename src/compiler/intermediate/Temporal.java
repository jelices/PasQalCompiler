package compiler.intermediate;

import es.uned.compiler.intermediate.TemporalIF;
import es.uned.compiler.semantic.symbol.ScopeIF;

/**
 * Class for temporal variables within the intermediate code.
 */

public class Temporal
    implements TemporalIF 
{

	
    String  name    = null;
    ScopeIF scope   = null;
    
    /**
     * Constructor for Temporal.
     * @param name The name.
     */
    public Temporal (String name)
    {
        this.name = name;
    }
    
    /**
     * Constructor for Temporal.
     * @param name The name.
     * @param scope The creation scope.
     */
    public Temporal (String name, ScopeIF scope)
    {
        this (name);
        this.scope = scope;
    }

    /**
     * Returns the name.
     * @return Returns the name.
     */
    public String getName ()
    {
        return name;
    }

    /**
     * Sets The name.
     * @param name The name to set.
     */
    public void setName (String name)
    {
        this.name = name;
    }

    /**
     * Returns the creation scope.
     * @return Returns a scope.
     */
    public ScopeIF getScope ()
    {
        return scope;
    }

    /**
     * Sets The creation scope.
     * @param scope The scope to set.
     */
    public void setScope (ScopeIF scope)
    {
        this.scope = scope;
    }
    
    
    /**
     * Compares this object with another one.
     * @param other the other object.
     * @return true if both objects has the same properties.
     */
    public boolean equals (Object other)
    {
        // TODO: Student Work (optional)
        if (other == null) 
        {
            return false;
        }
        if (this == other)
        {
            return true;
        }
        if (!(other instanceof Temporal))
        {
            return false;
        }
        
        final Temporal aTemporal = (Temporal) other;
        return ((scope != null) ? (scope == aTemporal.scope) : aTemporal.scope == null) &&
               ((name  != null) ? (name  == aTemporal.name)  : aTemporal.name  == null);
    }

    /**
     * Returns a hash code for the object.
     */
    public int hashCode ()
    {
        // TODO: Student Work (optional)
        return 255 * ((scope != null)? scope.hashCode () : 0) +
                     ((name  != null)? name.hashCode ()  : 0);
    } 

    /**
     * Return a string representing the object.
     * @return a string representing the object.
     */
    public String toString ()
    {        
        // TODO: Student Work (optional)

    	return name;
    }
}
