package compiler.syntax.nonTerminal;

import compiler.CompilerContext;
import compiler.intermediate.Cadena;
import compiler.intermediate.Value;
import compiler.intermediate.Variable;
import compiler.semantic.symbol.SymbolVariable;
import compiler.semantic.type.TypePointer;
import compiler.semantic.type.TypeRecord;
import compiler.semantic.type.TypeSet;
import compiler.semantic.type.TypeSimple;
import compiler.semantic.type.TypeSimple.Tipo;
import compiler.syntax.nonTerminal.Referencia.TipoModificacion;

import es.uned.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.compiler.intermediate.IntermediateCodeBuilderIF;
import es.uned.compiler.intermediate.LabelFactoryIF;
import es.uned.compiler.intermediate.LabelIF;
import es.uned.compiler.intermediate.TemporalFactoryIF;
import es.uned.compiler.intermediate.TemporalIF;
import es.uned.compiler.semantic.SemanticErrorManager;
import es.uned.compiler.semantic.symbol.ScopeManagerIF;
import es.uned.compiler.semantic.type.TypeBase;

public class SentenciaAsignacion extends NonTerminal 
{

	private TypeBase typebase;
	private String referencia;
	private Referencia r;
	private Expresion e;
	
	public SentenciaAsignacion(Referencia r, Expresion e, ScopeManagerIF scopeManager, SemanticErrorManager error)
	{
		if(r==null||e==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		this.r=r;
		this.e=e;
		referencia=r.getNombre();
		if(r.getTipoModificacion()==TipoModificacion.CONSTANTE)
			error.semanticFatalError("Asignando un valor a la constante "+ referencia);
		TypeBase refTB=r.getTypeBase();
		TypeBase expTB=e.getTypeBase();
		if(!comprobarAsignacion(refTB, expTB, error))
			error.semanticFatalError("Asignando a "+ referencia + " que es "+ refTB +" un " + expTB);
		if(r.esReferencia()&&(!((SymbolVariable)r.getSimbolo()).asignado()))
			error.semanticError("Warning: El puntero "+r.getNombre() +" puede no haberse inicializado.");
		((SymbolVariable)r.getSimbolo()).asignar();
		
		
		//codigo intermedio
		TemporalIF temp=e.getTemp();
		IntermediateCodeBuilderIF cb = new IntermediateCodeBuilder (scopeManager.getCurrentScope());
		cb.addQuadruples(e.getIntermediateCode());
		if((expTB instanceof TypeSet)&&((TypeSet)expTB).getGenerico()&&(temp!=null))
		{
			TemporalIF temp2=e.getTemp2();
			TemporalFactoryIF tf= CompilerContext.getTemporalFactory(scopeManager.getCurrentScope());
			TemporalIF temp3= tf.create();
			TemporalIF temp4= tf.create();
			LabelFactoryIF lf=CompilerContext.getLabelFactory();
			LabelIF errorL=lf.create();
			LabelIF bucle=lf.create();
			LabelIF poner0=lf.create();
			LabelIF salirL=lf.create();
			cb.addQuadruple("COMP", temp, new Value(((TypeSet)refTB).getInicio()));
			cb.addQuadruple("BN", errorL);
			cb.addQuadruple("COMP", new Value(((TypeSet)refTB).getFin()),temp2);
			cb.addQuadruple("BN", errorL);
			if(r.esReferencia())
			{
				cb.addQuadruple("MOVE", r.getVariable(), temp3);
			}
			else
				cb.addQuadruple("ASP", r.getVariable(), temp3);
			cb.addQuadruple("SUB", temp, new Value(((TypeSet)refTB).getInicio()), temp);
			cb.addQuadruple("SUB", temp2, new Value(((TypeSet)refTB).getInicio()), temp2);
			cb.addQuadruple("MOVE", new Value(0),temp4);
			cb.addQuadruple("LABEL",bucle);
			cb.addQuadruple("COMP", new Value(((TypeSet)refTB).getTamanyo()),temp4);
			cb.addQuadruple("BZ",salirL);
			cb.addQuadruple("COMP",temp4,temp);
			cb.addQuadruple("BN",poner0);
			cb.addQuadruple("COMP",temp2,temp4);
			cb.addQuadruple("BN",poner0);
			cb.addQuadruple("CPP", new Value(1), temp3);
			cb.addQuadruple("INC", temp4);
			cb.addQuadruple("DEC", temp3); //Cambiar a INC si es pila creciente
			cb.addQuadruple("BR",bucle);
			cb.addQuadruple("LABEL",poner0);
			cb.addQuadruple("CPP", new Value(0), temp3);
			cb.addQuadruple("INC", temp4);
			cb.addQuadruple("DEC", temp3); //Cambiar a INC si es pila creciente
			cb.addQuadruple("BR",bucle);
			cb.addQuadruple("LABEL", errorL);
			cb.addQuadruple("WRTSTR", new Cadena("Fuera de rango del conjunto"));
			cb.addQuadruple("WRTLN",null);
			cb.addQuadruple("HALT", null);
			cb.addQuadruple("LABEL", salirL);
			this.setIntermediateCode(cb.create());
		}
		else if((expTB instanceof TypeSet)&&((TypeSet)expTB).getGenerico())
		{
			//conjunto vacio
			TemporalFactoryIF tf= CompilerContext.getTemporalFactory(scopeManager.getCurrentScope());
			temp=tf.create();
			TemporalIF temp2=tf.create();
			LabelFactoryIF lf=CompilerContext.getLabelFactory();
			LabelIF bucle=lf.create();
			LabelIF salirL=lf.create();
			cb.addQuadruple("MOVE", new Value(0),temp);
			if(r.esReferencia())
			{
				cb.addQuadruple("MOVE", r.getVariable(), temp2);
				cb.addQuadruple("WRTINT",temp2);
			}
			else
				cb.addQuadruple("ASP", r.getVariable(), temp2);
			cb.addQuadruple("LABEL",bucle);
			cb.addQuadruple("COMP", new Value(((TypeSet)refTB).getTamanyo()),temp);
			cb.addQuadruple("BZ",salirL);
			cb.addQuadruple("CPP", new Value(0), temp2);
			cb.addQuadruple("INC", temp);
			cb.addQuadruple("DEC", temp2); //Cambiar a INC si es pila creciente
			cb.addQuadruple("BR",bucle);
			cb.addQuadruple("LABEL", salirL);
			this.setIntermediateCode(cb.create());
		}
		else if(expTB instanceof TypeSet)
		{
			TemporalIF[] temporales=e.getConjunto();
			TemporalFactoryIF tf= CompilerContext.getTemporalFactory(scopeManager.getCurrentScope());
			TemporalIF temp2=tf.create();
			if(r.esReferencia())
				cb.addQuadruple("MOVE", r.getVariable(), temp2);
			else
				cb.addQuadruple("ASP", r.getVariable(), temp2);
			for(int i=0; i<temporales.length; i++)
			{
				cb.addQuadruple("CPP", temporales[i],temp2);
				cb.addQuadruple("DEC", temp2);	//cambiar a INC si la pila fuese creciente
			}
			this.setIntermediateCode(cb.create());
		}
		else if(r.esCampoRegistro())
		{
			TemporalFactoryIF tf= CompilerContext.getTemporalFactory(scopeManager.getCurrentScope());
			TemporalIF temp21=tf.create();
			if(r.esReferencia())
				cb.addQuadruple("MOVE", r.getVariable(), temp21);
			else
				cb.addQuadruple("ASP", r.getVariable(), temp21);
			cb.addQuadruple("SUB",temp21, new Value(r.getOffset()), temp21);
			cb.addQuadruple("CPP", temp, temp21);
		}
		else
		{
			if (r.esReferencia())
				cb.addQuadruple("CPP", temp, r.getVariable());
			else
				cb.addQuadruple("MOVE", temp, r.getVariable());
		}
		//System.out.println(cb.create());
		this.setIntermediateCode(cb.create());
	}
	
	
	private boolean comprobarAsignacion(TypeBase ref, TypeBase exp, SemanticErrorManager error)
	{
		//Quitar los if para permitir asignaciones de registros 
		if((ref instanceof TypeRecord)&&(exp instanceof TypeRecord))
		{
			error.semanticFatalError("PasQal no permite asignaciones de registros");	
		}
		if(ref.equals(exp))
		{
			typebase=ref;
			return true;
		}
		if((ref instanceof TypeSimple)&&(((TypeSimple)ref).getTipo()==Tipo.ENTERO)&&(exp instanceof TypePointer))
		{
			typebase=ref;
			return true;
		}
		if((exp instanceof TypeSimple)&&(((TypeSimple)exp).getTipo()==Tipo.ENTERO)&&(ref instanceof TypePointer))
		{
			typebase=ref;
			error.semanticError("Asignando al puntero "+ referencia +" un entero o una expresion entera");
			return true;
		}
		if((ref instanceof TypeSet)&&(exp instanceof TypeSet)&&((TypeSet)exp).getGenerico())
		{
			typebase=ref;
			return true;
		}
		return false;
	}
	
	
	public TypeBase getTypeBase()
	{
		return typebase;
	}
	
	
	public Referencia getReferencia()
	{
		return r;
	}
	
	public Expresion getExpresion()
	{
		return e;
	}
}
