package compiler.code;

import compiler.intermediate.Cadena;
import compiler.intermediate.Value;
import compiler.intermediate.Variable;

import es.uned.compiler.code.ExecutionEnvironmentIF;
import es.uned.compiler.intermediate.LabelIF;
import es.uned.compiler.intermediate.QuadrupleIF;
import es.uned.compiler.intermediate.TemporalIF;
import es.uned.compiler.intermediate.VariableIF;

/**
 * Class for the ENS2001 Execution environment.
 */

public class ENS2001Environment
    implements ExecutionEnvironmentIF
{    
	private String cadena=null;
    /**
     * Constructor for ENS2001Environment.
     */
    public ENS2001Environment ()
    {
    }
       
    /**
     * Translate a quadruple into a set of final instruction according to 
     * execution environment. 
     * @param cuadruple The quadruple to be translated.
     * @return A String containing the set (lines) of specific environment instructions. 
     */
    public String translate (QuadrupleIF quadruple)
    {  
    	cadena="";
    	String op1, op2 = null, res=null;
    	
    	//Calculamos los posibles operandos
    	op1=calcularOperando1(quadruple);
    	op2=calcularOperando2(quadruple);
    	res=calcularOperando3(quadruple);
    	
    	//Instrucciones
    	if(quadruple.getOperation().equalsIgnoreCase("LABEL"))
    	{
    			LabelIF lf=(LabelIF)quadruple.getResult();
    			if(lf.getName().equalsIgnoreCase("inicio"))
    			{
    				String c1="MOVE #65535,.SP";
    				String c2="PUSH #65533"; //Vinculo de acceso del programa principal a el mismo (no deberia usarse nunca)
    				String c3="PUSH #65533"; //Vinculo de control del programa principal a el mismo (no deberia usarse nunca)
    				String c4="PUSH #0";	//Direccion de retorno del principal
    				String c5="MOVE #65533,.IX"; //Colocamos el FP
    				return crearEtiqueta(lf.getName())+":   "+"\n"+c1+"\n"+c2+"\n"+c3+"\n"+c4+"\n"+c5;
    			}
    			return crearEtiqueta(lf.getName())+":   NOP";
    	}
    	else if(quadruple.getOperation().equalsIgnoreCase("END"))
    	{
    		return generadorCadenas.codigoCadenas();
    	}
    	else if (quadruple.getOperation().equalsIgnoreCase("HALT"))
    	{
    		return "HALT";
    	}
    	else if (quadruple.getOperation().equalsIgnoreCase("MOVE"))
    	{
    		cadena+= "MOVE "+ op1 +","+op2;
    		return cadena;
    	}
    	else if (quadruple.getOperation().equalsIgnoreCase("ADD"))
    	{
    		String cad1="ADD "+ op1 +","+op2;
    		String cad2="MOVE .A ,"+res;
    		cadena += cad1+"\n"+cad2;
    		return cadena;
    	}
    	else if (quadruple.getOperation().equalsIgnoreCase("SUB"))
    	{
    		String cad1="SUB "+ op1 +","+op2;
    		String cad2="MOVE .A ,"+res;
    		cadena += cad1+"\n"+cad2;
    		return cadena;
    	}
    	else if (quadruple.getOperation().equalsIgnoreCase("DEC"))
    	{
    		cadena+= "DEC "+op1;
    		return cadena;
    	}
    	else if (quadruple.getOperation().equalsIgnoreCase("INC"))
    	{
    		cadena+="INC "+op1;
    		return cadena;
    	}
    	else if (quadruple.getOperation().equalsIgnoreCase("COMP"))
    	{
    		cadena+="CMP "+ op1 +","+op2;
    		return cadena;
    	}
    	else if (quadruple.getOperation().equalsIgnoreCase("OR"))
    	{
    		String cad1="OR "+ op1 +","+op2;
    		String cad2="MOVE .A ,"+res;
    		cadena += cad1+"\n"+cad2;
    		return cadena;
    	}
    	else if (quadruple.getOperation().equalsIgnoreCase("AND"))
    	{
    		String cad1="AND "+ op1 +","+op2;
    		String cad2="MOVE .A ,"+res;
    		cadena += cad1+"\n"+cad2;
    		return cadena;
    	}
    	else if (quadruple.getOperation().equalsIgnoreCase("WRTINT"))
    	{
    		cadena+= "WRINT "+op1;
    		return cadena;
    	}
    	else if (quadruple.getOperation().equalsIgnoreCase("WRTLN"))
    	{
    		return "WRCHAR #10";
    	}
    	else if (quadruple.getOperation().equalsIgnoreCase("WRTSTR"))
    	{
    		cadena += "WRSTR "+op1;
    		return cadena;
    	}
    	else if(quadruple.getOperation().equalsIgnoreCase("ASP"))
    	{
    		cadena+="MOVE "+op1+" ,"+op2;
    		return cadena;
    	}
    	else if(quadruple.getOperation().equalsIgnoreCase("REF"))
    	{
    		String cad1="MOVE "+op1+",.R1";
    		String cad2="MOVE [.R1],"+op2;
    		cadena+= cad1+"\n"+cad2;
    		return cadena;
    	}
       	else if(quadruple.getOperation().equalsIgnoreCase("CPP"))
    	{
    		String cad1="MOVE "+op2+",.R1";
    		String cad2="MOVE "+op1+",[.R1]";
    		cadena+= cad1+"\n"+cad2;
    		return cadena;
    	}
       	else if(quadruple.getOperation().equalsIgnoreCase("BP"))
    	{
    		cadena+= "BP "+ op1;
    		return cadena;
    	}
       	else if(quadruple.getOperation().equalsIgnoreCase("BN"))
    	{
       		cadena+= "BN "+ op1;
    		return cadena;
    	}
       	else if(quadruple.getOperation().equalsIgnoreCase("BR"))
    	{
       		cadena+= "BR "+ op1;
    		return cadena;
    	}
    	else if(quadruple.getOperation().equalsIgnoreCase("BZ"))
    	{
    		cadena+= "BZ "+ op1;
    		return cadena;
    	}
       	else if(quadruple.getOperation().equalsIgnoreCase("BNZ"))
    	{
       		cadena+= "BNZ "+ op1;
    		return cadena;
    	}
       	else if(quadruple.getOperation().equalsIgnoreCase("ACTUALIZAR-SP"))
    	{
       		cadena += "SUB .SP,#"+(generadorTemp.getOffset((TemporalIF)quadruple.getResult())-1)+"\n";
       		cadena += "MOVE .A, .SP";
    		return cadena;
    	}
       	else if(quadruple.getOperation().equalsIgnoreCase("PUSH"))
    	{
       		cadena += "PUSH "+op1;
    		return cadena;
    	}
       	else if(quadruple.getOperation().equalsIgnoreCase("VINC-ACC-HIJO"))
    	{
       		cadena += "PUSH .IX";
    		return cadena;
    	}
       	else if(quadruple.getOperation().equalsIgnoreCase("VINC-ACC-HERMANO"))
    	{
       		cadena += "PUSH #2[.IX]";
    		return cadena;
    	}
       	else if(quadruple.getOperation().equalsIgnoreCase("VINC-CONTROL"))
    	{
       		cadena += "PUSH .IX";
    		return cadena;
    	}
    	else if(quadruple.getOperation().equalsIgnoreCase("CAMBIAR-FP"))
    	{
       		cadena += "MOVE .SP, .IX";
    		return cadena;
    	}
    	else if(quadruple.getOperation().equalsIgnoreCase("CALL"))
    	{
       		cadena += "CALL "+op1;
    		return cadena;
    	}
    	else if(quadruple.getOperation().equalsIgnoreCase("COPIAR-FP-EN-SP"))
    	{
       		cadena += "MOVE .IX, .SP";
    		return cadena;
    	}
    	else if(quadruple.getOperation().equalsIgnoreCase("VINC-CONTROL-a-FP"))
    	{
       		cadena += "MOVE #1[.IX], .IX";
    		return cadena;
    	}
    	else if(quadruple.getOperation().equalsIgnoreCase("SET-VALOR-DEVUELTO"))
    	{
       		cadena += "MOVE " +op1+ ", .R9";
    		return cadena;
    	}
    	else if(quadruple.getOperation().equalsIgnoreCase("CAMBIAR-SP-EXTRAER-ARGUMENTOS"))
    	{
       		cadena += "ADD " +op1+ ", .SP\n";
       		cadena += "MOVE .A, .SP";
    		return cadena;
    	}
    	else if(quadruple.getOperation().equalsIgnoreCase("GET-VALOR-DEVUELTO"))
    	{
       		cadena += "MOVE .R9,"+op2;
    		return cadena;
    	}
    	else if(quadruple.getOperation().equalsIgnoreCase("RET"))
    	{
       		cadena += "MOVE [.SP], .PC";
    		return cadena;
    	}
    	
        return quadruple.toString();  // Dummy code  
    }
    
    
    
    private String calcularOperando1(QuadrupleIF quadruple)
    {
    	String op1 = null;
    	if(quadruple.getOperation().equalsIgnoreCase("ASP"))
    	{ 
    		if(((Variable)quadruple.getResult()).getDiffScope()>0)
			{
    			cadena+="MOVE .IX, .R2\n";
				int difNiveles=((Variable)quadruple.getResult()).getDiffScope();
				do
				{
					cadena+="ADD .R2, #2\n";
					cadena+="MOVE .A, .R2\n";
					cadena+="MOVE [.R2], .R2\n";
					difNiveles--;
				}
				while(difNiveles>0);
				int dirOp1=-generadorVar.getOffset((VariableIF)quadruple.getResult());
				cadena+="ADD .R2,#"+dirOp1+"\n";
				cadena+="MOVE .A ,.R2\n";
				return ".R2";
			}
    		else
    		{
    			int dirOp1=-generadorVar.getOffset((VariableIF)quadruple.getResult());
    			cadena+="MOVE .IX, .R2\n";
    			cadena+="ADD #"+dirOp1+",.R2\n";
    			cadena+="MOVE .A ,.R2\n";
    			return ".R2";
    		}
    	}
    	if (quadruple.getResult()!=null)
    	{
    		if(quadruple.getResult() instanceof TemporalIF)
    		{
    			
    			int dirOp1=-generadorTemp.getOffset((TemporalIF)quadruple.getResult());
    			if(dirOp1<-127)
    			{
    				cadena+="MOVE .IX, .R2\n";
    				cadena+="ADD .R2,#"+dirOp1+"\n";
    				cadena+="MOVE .A, .R2\n";
    				op1="[.R2]";
    			}
    			else
    				op1= "#"+dirOp1+"[.IX]";
    		}
    		else if(quadruple.getResult() instanceof Value)
    		{
    			op1="#"+((Value)quadruple.getResult()).getValue();
    		}
    		else if(quadruple.getResult() instanceof Variable)
    		{
  
    			if(((Variable)quadruple.getResult()).getDiffScope()>0)
    			{
    				cadena+="MOVE .IX, .R2\n";
    				int difNiveles=((Variable)quadruple.getResult()).getDiffScope();
    				do
    				{
    					cadena+="ADD .R2, #2\n";
    					cadena+="MOVE .A, .R2\n";
    					cadena+="MOVE [.R2], .R2\n";
    					difNiveles--;
    				}
    				while(difNiveles>0);
    				int dirOp1=-generadorVar.getOffset((VariableIF)quadruple.getResult());
    				cadena+="ADD .R2,#"+dirOp1+"\n";
					cadena+="MOVE .A, .R2\n";
					op1="[.R2]";
    			}
    			else
    			{
    				int dirOp1=-generadorVar.getOffset((VariableIF)quadruple.getResult());
    				if(dirOp1<-127)
    				{
    					cadena+="MOVE .IX, .R2\n";
    					cadena+="ADD .R2,#"+dirOp1+"\n";
    					cadena+="MOVE .A, .R2\n";
    					op1="[.R2]";
    				}
    				else
    					op1= "#"+dirOp1+"[.IX]";
    				
    			}
    			
    		}
    		else if(quadruple.getResult() instanceof LabelIF)
    		{
    			op1= "/"+crearEtiqueta(((LabelIF)quadruple.getResult()).getName());
    		}
    		else if(quadruple.getResult() instanceof Cadena)
    		{
    			op1="/"+generadorCadenas.agregarCadena(((Cadena)quadruple.getResult()).getValue());
    		}
    	}
    	return op1;
    }
    
    private String calcularOperando2(QuadrupleIF quadruple)
    {
    	String op2=null;
    	
    	if (quadruple.getFirstOperand()!=null)
    	{
    		if(quadruple.getFirstOperand() instanceof TemporalIF)
    		{
    			int dirOp2=-generadorTemp.getOffset((TemporalIF)quadruple.getFirstOperand());
    			if(dirOp2<-127)
    			{
    				cadena+="MOVE .IX, .R3\n";
    				cadena+="ADD .R3,#"+dirOp2+"\n";
    				cadena+="MOVE .A, .R3\n";
    				op2="[.R3]";
    			}
    			else
    				op2= "#"+dirOp2+"[.IX]";
    		}
    		else if(quadruple.getFirstOperand() instanceof Value)
    		{
    			op2="#"+((Value)quadruple.getFirstOperand()).getValue();
    		}
    		else if(quadruple.getFirstOperand() instanceof Variable)
    		{
    			if(((Variable)quadruple.getFirstOperand()).getDiffScope()>0)
    			{
    				cadena+="MOVE .IX, .R3\n";
    				int difNiveles=((Variable)quadruple.getFirstOperand()).getDiffScope();
    				do
    				{
    					cadena+="ADD .R3, #2\n";
    					cadena+="MOVE .A, .R3\n";
    					cadena+="MOVE [.R3], .R3\n";
    					difNiveles--;
    				}
    				while(difNiveles>0);
    				int dirOp2=-generadorVar.getOffset((VariableIF)quadruple.getFirstOperand());
    				cadena+="ADD .R3,#"+dirOp2+"\n";
					cadena+="MOVE .A, .R3\n";
					op2="[.R3]";
    			}
    			else
    			{
    				int dirOp2=-generadorVar.getOffset((VariableIF)quadruple.getFirstOperand());
    				if(dirOp2<-127)
    				{
    					cadena+="MOVE .IX, .R3\n";
    					cadena+="ADD .R3,#"+dirOp2+"\n";
    					cadena+="MOVE .A, .R3\n";
    					op2="[.R3]";
    				}
    				else
    					op2= "#"+dirOp2+"[.IX]";
    			}
    		}
    		else if(quadruple.getFirstOperand()instanceof LabelIF)
    		{
    			op2= "/"+crearEtiqueta(((LabelIF)quadruple.getFirstOperand()).getName());
    		}
    	}
    	
    	return op2;
    }
    
    private String calcularOperando3(QuadrupleIF quadruple)
    {
    	String res=null;
    	if (quadruple.getSecondOperand()!=null)
    	{
    		if(quadruple.getSecondOperand() instanceof TemporalIF)
    		{
    			int dirRes=-generadorTemp.getOffset((TemporalIF)quadruple.getSecondOperand());
    			if(dirRes<-127)
    			{
    				cadena+="MOVE .IX, .R4\n";
    				cadena+="ADD .R4,#"+dirRes+"\n";
    				cadena+="MOVE .A, .R4\n";
    				res="[.R4]";
    			}
    			else
    				res= "#"+dirRes+"[.IX]";
    		}
    		else if(quadruple.getSecondOperand() instanceof Variable)
    		{
    			if(((Variable)quadruple.getSecondOperand()).getDiffScope()>0)
    			{
    				cadena+="MOVE .IX, .R4\n";
    				int difNiveles=((Variable)quadruple.getSecondOperand()).getDiffScope();
    				do
    				{
    					cadena+="ADD .R4, #2\n";
    					cadena+="MOVE .A, .R4\n";
    					cadena+="MOVE [.R4], .R4\n";
    					difNiveles--;
    				}
    				while(difNiveles>0);
    				int dirRes=-generadorVar.getOffset((VariableIF)quadruple.getSecondOperand());
    				cadena+="ADD .R4,#"+dirRes+"\n";
					cadena+="MOVE .A, .R4\n";
					res="[.R4]";
    			}
    			else
    			{
    				int dirRes=-generadorVar.getOffset((VariableIF)quadruple.getSecondOperand());
    				if(dirRes<-127)
    				{
    					cadena+="MOVE .IX, .R4\n";
    					cadena+="ADD .R4,#"+dirRes+"\n";
    					cadena+="MOVE .A, .R4\n";
    					res="[.R4]";
    				}
    				else
    					res= "#"+dirRes+"[.IX]";
    			}
    		}
    	}
    	return res;
    }
    
    private String crearEtiqueta(String nombre)
    {
    	
    	String cadena=nombre;
    	while(!(Character.isLetterOrDigit(cadena.charAt(0))))
    	{
    		cadena = cadena.substring(1);
    	}
    	if(cadena.endsWith("()"))
    		cadena=cadena.substring(0, cadena.length()-2);
    	return cadena;
    }
	

}

