package compiler.semantic.symbol;

import java.util.*;

import es.uned.compiler.semantic.symbol.ScopeIF;
import es.uned.compiler.semantic.symbol.SymbolIF;
import es.uned.compiler.semantic.symbol.SymbolTableIF;

/**
 * Class for SymbolTable.
 */

public class SymbolTable 
    implements SymbolTableIF
{
  
    private ScopeIF scope = null;
    private HashMap<String, SymbolIF> table;
    private int numeroArgumentos=0;
    
    /**
     * Constructor for SymbolTable.
     */
    public SymbolTable ()
    {
        table=new HashMap<String, SymbolIF>();
    }
    
    /**
     * Constructor for SymbolTable.
     * @param The scope of the symbol table.
     */
    public SymbolTable (ScopeIF scope)
    {       
        this ();
        this.scope = scope;
    }
    
    /**
     * Returns the scope of the Symbol table. 
     * @return the scope of the Symbol table. 
     */
    public ScopeIF getScope ()
    {
        return  scope;
    }

    /**
     * Returns a symbol from the symbol table.
     * @param name the symbol name.
     */
    public SymbolIF getSymbol (String name)
    {
        return table.get(name.toLowerCase());
    }
    
    /**
     * Adds a new symbol to the symbol table.
     * @param symbol the symbol.
     */
    public void addSymbol (SymbolIF symbol)
    {
    	if(symbol instanceof SymbolVariable && ((SymbolVariable)symbol).esParametro())
    		numeroArgumentos++;
        table.put(symbol.getName().toLowerCase(), symbol);
    }
    
    /**
     * Adds a new symbol to the symbol table.
     * @param name the symbol name.
     * @param symbol the symbol.
     */
    public void addSymbol (String name, SymbolIF symbol)
    {
    	table.put(name.toLowerCase(), symbol);
    }
    
    /**
     * Returns the list of all symbols of symbol table.
     * @return A list of symbols.
     */
    public List<SymbolIF> getSymbols ()
    {
        return new ArrayList<SymbolIF>(table.values());
    }
    
    /**
     * Indicates if the symbol is contained in the symbol table.
     * @param symbol The symbol. 
     * @return True if the symbol is contained in the symbol table.
     */
    public boolean containsSymbol (SymbolIF symbol)
    {
        return table.containsValue(symbol);
    }
    
    /**
     * Indicates if the symbol is contained in the symbol table.
     * @param name The symbol name. 
     * @return True if the symbol is contained in the symbol table.
     */
    public boolean containsSymbol (String name)
    {
        return table.containsKey(name.toLowerCase());
    }
    
    /**
     * Compares this object with another one.
     * @param other the other object.
     * @return true if both objects has the same properties.
     */
    public boolean equals (Object other)
    {
        if(!(other instanceof SymbolTable))
        	return false;
        if(!(((SymbolTable)other).getScope().getId()==this.getScope().getId()))
        	return false;
       List<SymbolIF> l =((SymbolTable)other).getSymbols();
       for(int i=0;i <l.size();i++)
       {
    	   if(!this.containsSymbol(l.get(i)))
    		   return false;
       }
       return true;
    }

    /**
     * Returns a hash code for the object.
     */
    public int hashCode ()
    {
        return table.values().hashCode();
    } 

    /**
     * Return a string representing the object.
     * @return a string representing the object.
     */
    
    public String toString ()
    {
    	List<SymbolIF> lista=getSymbols();
    	String cadena= "Tabla de simbolos de " + scope.getName() +"\n";
    	cadena+= "Nombre \t Tipo\n------------------\n";
    	for(int i=0; i<lista.size();i++)
    	{
    		cadena+=lista.get(i).getName()+"\t";
    		cadena+= lista.get(i)+"\n";
    	}
    	return cadena;	
    }
   
    public int getNumeroArgumentos()
    {
    	return numeroArgumentos;
    }
    
}
