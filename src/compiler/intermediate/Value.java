package compiler.intermediate;

import es.uned.compiler.intermediate.OperandIF;

/**
 * Class for literals within intermediate code.
 */

public class Value
    implements OperandIF 
{
    int value  = 0;
      
    /**
     * Constructor for Value.
     * @param value The value.
     */
    public Value (int value)
    {
        super ();
        this.value = value;
    }

    /**
     * Returns the value.
     * @return Returns the value.
     */
    public int getValue ()
    {
        return value;
    }

    /**
     * Sets The value.
     * @param value The value to set.
     */
    public void setValue (int value)
    {
        this.value = value;
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
        if (!(other instanceof Value))
        {
            return false;
        }
        
        final Value aValue = (Value) other;
        return (value == aValue.value);
    }

    /**
     * Returns a hash code for the object.
     */
    public int hashCode ()
    {
        // TODO: Student Work (optional)
        return 255 * value;
    } 

    /**
     * Return a string representing the object.
     * @return a string representing the object.
     */
    public String toString ()
    {        
        // TODO: Student Work (optional)
        return new Integer (value).toString();
    }
}
