package compiler.code;

import java.util.HashMap;

import es.uned.compiler.intermediate.TemporalIF;
import es.uned.compiler.semantic.symbol.ScopeIF;

public class generadorTemp 
{
	
	private static HashMap<TemporalIF,Integer> temporales=new HashMap<TemporalIF, Integer>();
	private static HashMap<ScopeIF,Integer> valorTemporal=new HashMap<ScopeIF, Integer>();
	
	public static int getOffset(TemporalIF temp)
	{
		Integer offset = (Integer) temporales.get(temp);
		if(offset==null)
		{
			offset=(Integer)valorTemporal.get(temp.getScope());
			if(offset==null)
			{
				offset=generadorVar.getFinal(temp.getScope());
				
			}
			else
			{
				valorTemporal.remove(offset);
				offset++;
			}
			valorTemporal.put(temp.getScope(), offset);
			temporales.put(temp, offset);
		}
		return offset;
	}
	
	
}
