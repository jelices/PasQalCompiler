package compiler.semantic.type;

import es.uned.compiler.semantic.symbol.ScopeIF;
import es.uned.compiler.semantic.type.TypeBase;


/**
 * Class for TypeSimple.
 */

// TODO: Student work
//       Include properties to characterize simple types

public class TypeSimple
    extends TypeBase
{
    
public enum Tipo{ENTERO, BOOLEANO, PUNTERO};
private Tipo tipo;


    /**
     * Constructor for TypeSimple.
     * @param scope The declaration scope.
     */
    public TypeSimple (ScopeIF scope)
    {
        super (scope);
    }
    
    public TypeSimple (ScopeIF scope, Tipo tipo)
    {
        super (scope);
        this.setTipo(tipo);
    }
    
    public void setTipo(Tipo tipo)
    {
    	this.tipo=tipo;
    }
    
    public Tipo getTipo()
    {
    	return tipo;
    }


    /**
     * Compares this object with another one.
     * @param other the other object.
     * @return true if two objects has the same properties.
     */
    public boolean equals (Object other)
    {
    	if(!(other instanceof TypeSimple))
    		return false;
        return (((TypeSimple)other).getTipo()==tipo);
    }

    /**
     * Returns a hash code for the object.
     */
    public int hashCode ()
    {
        return tipo.hashCode();
    }

    /**
     * Return a string representing the object.
     * @return a string representing the object.
     */
    public String toString ()
    {
        if(tipo==Tipo.ENTERO)
        	return "Tipo Entero";
        if(tipo==Tipo.BOOLEANO)
        	return "Tipo Booleano";
        return "Tipo Desconocido";
    }
}
