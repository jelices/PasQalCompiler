package compiler.intermediate;

import es.uned.compiler.intermediate.VariableIF;
import es.uned.compiler.semantic.symbol.ScopeIF;

/**
 * Class for variables in intermediate code.
 */

public class Variable
    implements VariableIF 
{
    String  name  = null;
    ScopeIF scope = null;
    int difScope =0;
    
    
    /**
     * Constructor for Variable.
     * @param name The name.
     * @param scope The scope index.
     */
    public Variable (String name, ScopeIF scopeVar, ScopeIF scopeLlam)
    {
    	this.name = name;
        this.scope = scopeVar;
        this.difScope=scopeLlam.getLevel()-scopeVar.getLevel();
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
     * Returns the scope.
     * @return Returns the scope.
     */
    public ScopeIF getScope ()
    {
        return scope;
    }

    /**
     * Sets The scope.
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
        if (!(other instanceof Variable))
        {
            return false;
        }
        
        final Variable aVariable = (Variable) other;
        return ((scope != null) ? (scope == aVariable.scope) : aVariable.scope == null) && 
               ((name  != null) ? (name  == aVariable.name)  : aVariable.name  == null);
    }
    
    public int getDiffScope()
    {
    	return difScope;
    }

    /**
     * Returns a hash code for the object.
     */
    public int hashCode ()
    {
        // TODO: Student Work (optional)
        return 255 * scope.hashCode() +
               ((name != null)? name.hashCode () : 0);
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
