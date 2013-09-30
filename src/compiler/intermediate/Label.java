package compiler.intermediate;

import es.uned.compiler.intermediate.LabelIF;

/**
 * Class for Labels within intermediate code.
 */

public class Label
    implements LabelIF 
{
    String name  = null;
    
    /**
     * Constructor for Label.
     * @param name The name.
     */
    public Label (String name)
    {
        this.name = name;
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
        if (!(other instanceof Label))
        {
            return false;
        }
        
        final Label aLabel = (Label) other;
        return ((name != null) ? name.equals (aLabel.name) : aLabel.name == null);
    }

    /**
     * Returns a hash code for the object.
     */
    public int hashCode ()
    {
        // TODO: Student Work (optional)
        return 255 * ((name != null)? name.hashCode () : 0);
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
