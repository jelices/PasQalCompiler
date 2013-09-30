package compiler.code;

import java.util.ArrayList;


public class generadorCadenas 
{
	private static ArrayList<String> cadenas = new ArrayList<String>();
	private static ArrayList<String> nombresCadenas= new ArrayList<String>();
	private static int contador=0;
	
	public static String agregarCadena(String cadena)
	{
		String nombre=generarNombre();
		cadenas.add(cadena);
		nombresCadenas.add(nombre);
		return nombre;
	}

	private static String generarNombre()
	{
		contador++;
		return "cadena"+contador;
	}
	
	public static String codigoCadenas()
	{
		String codigo="";
		for(int i=0; i<cadenas.size(); i++)
		{
			codigo+= nombresCadenas.get(i)+ ":\tDATA \""+cadenas.get(i)+"\"\n";
		}
		return codigo;
	}
}
