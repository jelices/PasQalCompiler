package compiler.semantic.type;


import java.util.ArrayList;
import java.util.HashMap;

import compiler.semantic.symbol.SymbolVariable;

import es.uned.compiler.semantic.symbol.ScopeIF;
import es.uned.compiler.semantic.type.TypeBase;

/**
 * Class for TypeRecord.
 */

// TODO: Student work
//       Include properties to characterize records

public class TypeRecord
    extends TypeBase
{   
	
	private HashMap<String,SymbolVariable> campos;
	private ArrayList<String> nombreCampos;
	
    /**
     * Constructor for TypeRecord.
     * @param scope The declaration scope.
     */
    public TypeRecord (ScopeIF scope)
    {
        super (scope);
        campos=new HashMap<String, SymbolVariable>();
        nombreCampos=new ArrayList<String>();
    }
    
    /**
     * Constructor for TypeRecord.
     * @param scope The declaration scope.
     * @param name The name of the type.
     */
    public TypeRecord (ScopeIF scope, String name)
    {   
        super (scope, name.toLowerCase());
        campos=new HashMap<String, SymbolVariable>();
        nombreCampos=new ArrayList<String>();
    }

    
    public boolean containsValue (String name)
    {
        return campos.containsKey(name.toLowerCase());
    }
    
    public SymbolVariable getSymbol (String name)
    {
        return campos.get(name.toLowerCase());
    }
    
    public TypeBase getType (String name)
    {
    	if(campos.get(name.toLowerCase())!=null)
    		return (TypeBase)campos.get(name.toLowerCase()).getType();
    	return null;
    }
    
    public ArrayList<String> getNombreCampos()
    {
    	return nombreCampos;
    }
    
    /**
     * Constructor for TypeRecord.
     * @param record The record to copy.
     * @throws Exception 
     */
    public TypeRecord (TypeRecord record)
    {
    	super (record.getScope (), record.getName ());
        campos=new HashMap<String, SymbolVariable>();
        nombreCampos=new ArrayList<String>();
        for(int i=0; i<record.getNombreCampos().size();i++)
        {
        	String campo=record.getNombreCampos().get(i).toLowerCase();
        	campos.put(campo, record.getSymbol(campo));
        	nombreCampos.add(campo);
        }
    }
    
    public void anadirCampo(String nombre, TypeBase tipo)
    {
    	if(tipo instanceof TypeSimple || tipo instanceof TypePointer)
    	{
    		campos.put(nombre.toLowerCase(), new SymbolVariable(tipo.getScope(), nombre.toLowerCase(), tipo));
    		nombreCampos.add(nombre.toLowerCase());
    	}
    }

	public int getOffset(String lexema) {
		return nombreCampos.indexOf(lexema.toLowerCase());
	}   

    /**
     * Compares this object with another one.
     * @param other the other object.
     * @return true if both objects has the same properties.
     */
    public boolean equals (Object other)
    {
        if(!(other instanceof TypeRecord))
        	return false;
        if(this.getName()!=((TypeRecord)other).getName())
        	return false;
        if(this.getScope()!=((TypeRecord)other).getScope())
        	return false;
        ArrayList<String> nombreCamposO=((TypeRecord)other).getNombreCampos();
        if(nombreCampos.size()!=nombreCamposO.size())
        	return false;
        for(int i=0; i<nombreCampos.size();i++)
        {
        	String nombreCampo= nombreCampos.get(i);
        	if(!(nombreCampo.equals(nombreCamposO.get(i))))
        		return false;
        	if(!((this.getSymbol(nombreCampo)).equals(((TypeRecord)other).getSymbol(nombreCampo))))
        		return false;
        }
        return true;
    }

    /**
     * Returns a hash code for the object.
     */
    public int hashCode ()
    {
        return getName().hashCode()+nombreCampos.hashCode()+campos.hashCode();
    }

    /**
     * Return a string representing the object.
     * @return a string representing the object.
     */
    public String toString ()
    {
        return "Tipo Record " + this.getName();
    }

    public int getTamanyo()
    {
    	return nombreCampos.size();
    }

}
