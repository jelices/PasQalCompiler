package compiler.semantic.symbol;

import es.uned.compiler.semantic.symbol.ScopeIF;
import es.uned.compiler.semantic.symbol.SymbolBase;
import es.uned.compiler.semantic.type.TypeIF;

/**
 * Class for SymbolVariable.
 */

// TODO: Student work
//       Include properties to characterize variables

public class SymbolVariable
    extends SymbolBase
{  
   
	private int offset;
	private boolean esGlobal;
	private boolean conValor=false;
	private boolean esParametro=false;
	private int numeroParametro;
	private boolean porReferencia=false;
	
    
	/**
     * Constructor for SymbolVariable.
     * @param scope The declaration scope.
     * @param name The symbol name.
     * @param type The symbol type.
     */
    public SymbolVariable (ScopeIF scope, 
                           String name,
                           TypeIF type)
    {
        super (scope, name.toLowerCase(), type);
    }
    
    
    public void setDireccion(int offset)
    {
    	this.offset=offset;
    }
    
    public int getOffset()
    {
    	return offset;
    }
    
    public void setGlobal(boolean global)
    {
    	this.esGlobal=global;
    }
    
    public boolean esGlobal()
    {
    	return esGlobal;
    }
    
    public void setReferencia(boolean referencia)
    {
    	this.porReferencia=referencia;
    }
    
    public boolean getPorReferencia()
    {
    	return esParametro&&porReferencia;
    }
    
    public boolean esParametro()
    {
    	return esParametro;
    }
    
    public void setParametro(int numero)
    {
    	esParametro=true;
    	numeroParametro=numero;
    }
    
    public void asignar()
    {
    	conValor=true;
    }
    
    public boolean asignado()
    {
    	return conValor;
    }
    
    public int getNumeroParametro()
    {
    	return numeroParametro;
    }
            
    /**
     * Compares this object with another one.
     * @param other the other object.
     * @return true if two objects has the same properties.
     */
    public boolean equals (Object other)
    {
        // TODO: Student Work
        return super.equals(other);
    }

    /**
     * Returns a hash code for the object.
     */
    public int hashCode ()
    {
        // TODO: Student Work
        return super.hashCode();
    } 

    /**
     * Return a string representing the object.
     * @return a string representing the object.
     */
    public String toString ()
    {
    	if(!esParametro)
    		return "variable " +this.getType() +" offset de " + offset;
    	else if(porReferencia)
    		return "argumento " + numeroParametro + " por referencia "+ this.getType()+" offset de " + offset;
    	else
    		return "argumento " + numeroParametro + " por valor "+ this.getType()+" offset de " + offset;
        //return super.toString();
    } 
}
