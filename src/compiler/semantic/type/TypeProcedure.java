package compiler.semantic.type;

import java.util.ArrayList;

import es.uned.compiler.semantic.symbol.ScopeIF;
import es.uned.compiler.semantic.type.TypeBase;



/**
 * Class for TypeProcedure.
 */


public class TypeProcedure
    extends TypeBase
{
	public enum PasoPor{VALOR, REFERENCIA};
	private ArrayList<TypeBase> parametros;
	private ArrayList<PasoPor> parametrosPaso;
   /**
     * Constructor for TypeProcedure.
     * @param scope The declaration scope.
     */
    
	public TypeProcedure (ScopeIF scope)
    {
        super (scope);
        parametros=new ArrayList<TypeBase>();
        parametrosPaso= new ArrayList<PasoPor>();
    }

    /**
     * Constructor for TypeProcedure.
     * @param scope The declaration scope
     * @param name The name of the procedure.
     */
    public TypeProcedure (ScopeIF scope, String name)
    {
        super (scope, name.toLowerCase());
        parametros=new ArrayList<TypeBase>();
        parametrosPaso= new ArrayList<PasoPor>();
    }
    
    public void anadirParametro(TypeBase tipoParametro, PasoPor tipoPaso)
    {
    	parametros.add(tipoParametro);
    	parametrosPaso.add(tipoPaso);
    }
    
    public ArrayList<PasoPor> getParametrosPaso()
    {
    	return parametrosPaso;
    }
    
    public ArrayList<TypeBase> getParametros()
    {
    	return parametros;
    }

    /**
     * Compares this object with another one.
     * @param other the other object.
     * @return true if two objects has the same properties.
     */
    public boolean equals (Object other)
    {
        if(!(other instanceof TypeProcedure))
        	return false;
        ArrayList<TypeBase> parametrosO=((TypeProcedure)other).getParametros();
        ArrayList<PasoPor> parametrosPasoO=((TypeProcedure)other).getParametrosPaso();
        if((parametros.size()!=parametrosO.size())||(parametrosPaso.size()!=parametrosPasoO.size())||(parametros.size()!=parametrosPaso.size()))
        	return false;
        for(int i=0;i<parametros.size();i++)
        {
        	if(!(parametrosO.get(i).equals(parametros.get(i))))
        		return false;
        	if(!(parametrosPasoO.get(i).equals(parametrosPaso.get(i))))
        		return false;
        }
        return true;
    }

    /**
     * Returns a hash code for the object.
     */
    public int hashCode ()
    {
        return getName().hashCode()+parametros.hashCode()+parametrosPaso.hashCode();
    }

    /**
     * Return a string representing the object.
     * @return a string representing the object.
     */
    public String toString ()
    {
    	String cadena="Procedimiento "+ this.getName() +" Parametros: " ;
    	for(int i=0; i<parametros.size(); i++)
    	{
    		cadena+=parametros.get(i) + " por " + parametrosPaso.get(i) +", ";
    	}
        return cadena;
    }
}
