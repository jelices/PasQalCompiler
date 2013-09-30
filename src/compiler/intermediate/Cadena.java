package compiler.intermediate;

import es.uned.compiler.intermediate.OperandIF;

public class Cadena implements OperandIF 
{
	String cadena  = "";
    
    /**
     * Constructor for Value.
     * @param value The value.
     */
    public Cadena (String cadena)
    {
        super ();
        this.cadena = cadena;
    }

    /**
     * Returns the value.
     * @return Returns the value.
     */
    public String getValue ()
    {
        return cadena;
    }

    /**
     * Sets The value.
     * @param value The value to set.
     */
    public void setValue (String cadena)
    {
        this.cadena = cadena;
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
        if (!(other instanceof Cadena))
        {
            return false;
        }
        
        final Cadena aCadena = (Cadena) other;
        return (cadena.equals(aCadena.cadena));
    }

    /**
     * Returns a hash code for the object.
     */
    public int hashCode ()
    {
        // TODO: Student Work (optional)
        return cadena.hashCode();
    } 

    /**
     * Return a string representing the object.
     * @return a string representing the object.
     */
    public String toString ()
    {        
        // TODO: Student Work (optional)
        return cadena;
    }
}
