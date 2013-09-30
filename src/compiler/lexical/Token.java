package compiler.lexical;

import es.uned.compiler.lexical.TokenBase;

public class Token 
    extends TokenBase
{
    private String lexema = null;
    private int    id     = 0;
    private int    line   = 0;
    private int    column = 0;
    
    /**
     * Constructor for Token.
     */
    public Token ()
    {
        super ();
    }
    
    /**
     * Constructor for Token.
     * @param id The token id.
     */
    public Token (int id)
    {
        super (id);
        this.id = id;
    }

    /**
     * Returns the id.
     * @return Returns the id.
     */
    public int getId ()
    {
        return id;
    }

    /**
     * Sets The id.
     * @param id The id to set.
     */
    public void setId (int id)
    {
        this.id = id;
    }
    
    /**
     * Returns the column.
     * @return Returns the column.
     */
    public int getColumn ()
    {
        return column;
    }

    /**
     * Sets The column.
     * @param column The column to set.
     */
    public void setColumn (int column)
    {
        this.column = column;
    }

    /**
     * Returns the lexema.
     * @return Returns the lexema.
     */
    public String getLexema ()
    {
        return lexema;
    }

    /**
     * Sets The lexema.
     * @param lexema The lexema to set.
     */
    public void setLexema (String lexema)
    {
        this.lexema = lexema;
    }

    /**
     * Returns the line.
     * @return Returns the line.
     */
    public int getLine ()
    {
        return line;
    }

    /**
     * Sets The line.
     * @param line The line to set.
     */
    public void setLine (int line)
    {
        this.line = line;
    }
}
