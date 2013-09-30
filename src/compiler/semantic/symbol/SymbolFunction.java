package compiler.semantic.symbol;

import es.uned.compiler.semantic.symbol.ScopeIF;
import es.uned.compiler.semantic.type.TypeBase;
import es.uned.compiler.semantic.type.TypeIF;

/**
 * Class for SymbolFunction.
 */

// TODO: Student work
//       Include properties to characterize function calls

public class SymbolFunction
    extends SymbolProcedure
{
    
	private TypeBase tipoDevuelto;
	
    /**
     * Constructor for SymbolFunction.
     * @param scope The declaration scope.
     * @param name The symbol name.
     * @param type The symbol type.
     */
    public SymbolFunction (ScopeIF scope, 
                           String name,
                           TypeIF type)
    {
        super (scope, name.toLowerCase(), type);
    }
    
    public void setTipoDevuelto(TypeBase tipoDevuelto)
    {
    	this.tipoDevuelto=tipoDevuelto;
    }
    
    public TypeBase getTipoDevuelto()
    {
    	return tipoDevuelto;
    }
    
    /**
     * Compares this object with another one.
     * @param other the other object.
     * @return true if both objects has the same properties.
     */
    public boolean equals (Object other)
    {
    	if(!(other instanceof SymbolFunction))
    		return false;
    	if(!((tipoDevuelto).equals(((SymbolFunction)other).getTipoDevuelto())))
    		return false;
        return super.equals(other);
    }

    /**
     * Returns a hash code for the object.
     */
    public int hashCode ()
    {
        return super.hashCode()+tipoDevuelto.hashCode();
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
