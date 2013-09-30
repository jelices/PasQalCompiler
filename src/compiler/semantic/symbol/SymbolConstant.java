package compiler.semantic.symbol;

import compiler.semantic.type.TypeSimple;
import compiler.semantic.type.TypeSimple.Tipo;

import es.uned.compiler.semantic.symbol.ScopeIF;
import es.uned.compiler.semantic.symbol.SymbolBase;
import es.uned.compiler.semantic.type.TypeIF;

/**
 * Class for SymbolConstant.
 */


public class SymbolConstant
    extends SymbolBase
{
	private int valorI;
	private boolean valorB;
	private Tipo tipo;
    
    /**
     * Constructor for SymbolConstant.
     * @param scope The declaration scope.
     * @param name The symbol name.
     * @param type The symbol type.
     */
    public SymbolConstant (ScopeIF scope,
                           String name,
                           TypeIF type)
    {
    	super (scope, name.toLowerCase(), type);
    	if(type instanceof TypeSimple)
    		tipo=((TypeSimple) type).getTipo();
    }
    
    public void setValor(Object valor)
    {
    	if(tipo==Tipo.BOOLEANO)
    		valorB=(Boolean) valor;
    	if(tipo==Tipo.ENTERO)
    		valorI=(Integer) valor;
    }
    
    public Object getValor()
    {
    	if(tipo==Tipo.BOOLEANO)
    		return valorB;
    	if(tipo==Tipo.ENTERO)
    		return valorI;
    	return null;
    }
    
    /**
     * Compares this object with another one.
     * @param other the other object.
     * @return true if both objects has the same properties.
     */
    
    public boolean equals (Object other)
    {
        if(!(other instanceof SymbolConstant))
        	return false;
        SymbolConstant otro=(SymbolConstant) other;
        if(!(this.getScope().equals(otro.getScope())))
        	return false;
        if(!(this.getName().equals(otro.getName())))
            return false;
        return true; //No comprobamos el valor porque nos interesa solo que sean iguales en nombre y ambito
    }

    /**
     * Returns a hash code for the object.
     */
    public int hashCode ()
    {
        return super.hashCode();
    } 

    /**
     * Return a string representing the object.
     * @return a string representing the object.
     */
    public String toString ()
    {
    	if(tipo==Tipo.BOOLEANO)
    		return "constante booleana";
    	if(tipo==Tipo.ENTERO)
    		return "constante entera";
    	return "constante indefinida";
        //return super.toString();
    } 
}
