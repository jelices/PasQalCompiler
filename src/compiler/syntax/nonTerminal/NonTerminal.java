package compiler.syntax.nonTerminal;

import java.util.ArrayList;
import java.util.List;

import es.uned.compiler.syntax.nonTerminal.NonTerminalIF;

/**
 * Abstract class for non terminals.
 */

public abstract class NonTerminal
    implements NonTerminalIF
{
    List intermediateCode = null;
    
    /**
     * Constructor for ProgramModule.
     */
    public NonTerminal ()
    {
        this.intermediateCode = new ArrayList (); 
    }
    
    /**
     * Returns the code.
     * @return Returns the code.
     */
    public List getIntermediateCode ()
    {
        return intermediateCode;
    }

    /**
     * Sets The code.
     * @param code The code to set.
     */
    public void setIntermediateCode (List intermediateCode)
    {
        this.intermediateCode = intermediateCode;
    }
}
