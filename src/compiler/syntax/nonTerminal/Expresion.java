package compiler.syntax.nonTerminal;


import compiler.CompilerContext;
import compiler.intermediate.Value;
import compiler.semantic.symbol.SymbolVariable;
import compiler.semantic.type.TypePointer;
import compiler.semantic.type.TypeRecord;
import compiler.semantic.type.TypeSet;
import compiler.semantic.type.TypeSimple;
import compiler.semantic.type.TypeSimple.Tipo;
import compiler.syntax.nonTerminal.LlamadaFuncionOProcedimiento.TipoLlamada;
import compiler.syntax.nonTerminal.Referencia.TipoModificacion;

import es.uned.compiler.intermediate.IntermediateCodeBuilder;
import es.uned.compiler.intermediate.IntermediateCodeBuilderIF;
import es.uned.compiler.intermediate.LabelFactoryIF;
import es.uned.compiler.intermediate.LabelIF;
import es.uned.compiler.intermediate.TemporalFactoryIF;
import es.uned.compiler.intermediate.TemporalIF;
import es.uned.compiler.semantic.SemanticErrorManager;
import es.uned.compiler.semantic.symbol.ScopeIF;
import es.uned.compiler.semantic.symbol.ScopeManagerIF;
import es.uned.compiler.semantic.type.TypeBase;


public class Expresion extends NonTerminal {
	
	private boolean validoPorRef;
	private TypeBase typeBase;
	
	private TemporalIF temp;
	private TemporalIF temp2;
	private TemporalIF conjunto[];
	
	public Expresion (ValorConstante vc, ScopeManagerIF scopeManager, SemanticErrorManager error)
	{
		if(vc==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		ScopeIF scope=scopeManager.getCurrentScope();
			typeBase= new TypeSimple(scope);
			((TypeSimple)typeBase).setTipo(vc.getTipo());
		validoPorRef=false;
		
		//Codigo Intermedio
		TemporalFactoryIF tf= CompilerContext.getTemporalFactory(scopeManager.getCurrentScope());
		temp=tf.create();
		IntermediateCodeBuilderIF cb = new IntermediateCodeBuilder (scopeManager.getCurrentScope());
		cb.addQuadruple("MOVE", vc.getValue(), temp);
		this.setIntermediateCode(cb.create());
		
	}

	
	public Expresion (Expresion e)
	{
		if(e==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		typeBase=e.getTypeBase();
		validoPorRef=false; //(variable) no es valido para referencia
		
		temp=e.getTemp();
		this.setIntermediateCode(e.getIntermediateCode());
		
	}
	
	public Expresion( Referencia r, ScopeManagerIF scopeManager, SemanticErrorManager error)
	{
		if(r==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		typeBase=r.getTypeBase();
		validoPorRef=true;
		if((r.getSimbolo() instanceof SymbolVariable) &&(!((SymbolVariable)(r.getSimbolo())).asignado()))
			error.semanticDebug("Warning: La variable "+r.getNombre() +" puede no haberse inicializado.");
				
		//Codigo Intermedio
		TemporalFactoryIF tf= CompilerContext.getTemporalFactory(scopeManager.getCurrentScope());
		temp=tf.create();
		IntermediateCodeBuilderIF cb = new IntermediateCodeBuilder (scopeManager.getCurrentScope());
		if(r.getTypeBase() instanceof TypeSet && r.esReferencia())
		{
			temp2=tf.create();
			conjunto=new TemporalIF[((TypeSet)r.getTypeBase()).getTamanyo()];
			cb.addQuadruple("MOVE", r.getVariable(), temp2);
			for(int i=0; i<((TypeSet) r.getTypeBase()).getTamanyo();i++)
			{
				conjunto[i]=tf.create();
				cb.addQuadruple("REF", temp2,conjunto[i]);
				cb.addQuadruple("DEC",temp2);//Cambiar a INC con pila creciente
			}
		}
		else if(r.getTypeBase() instanceof TypeSet)
		{
			if(r.esReferencia())
				validoPorRef=false;
			temp2=tf.create();
			conjunto=new TemporalIF[((TypeSet)r.getTypeBase()).getTamanyo()];
			cb.addQuadruple("ASP", r.getVariable(), temp2);
			for(int i=0; i<((TypeSet) r.getTypeBase()).getTamanyo();i++)
			{
				conjunto[i]=tf.create();
				cb.addQuadruple("REF", temp2,conjunto[i]);
				cb.addQuadruple("DEC",temp2);//Cambiar a INC con pila creciente
			}
			cb.addQuadruple("ASP", r.getVariable(), temp2);
		}
		else if(r.getTypeBase() instanceof TypeRecord)
		{
			if(r.esReferencia())
				validoPorRef=false;
			temp2=tf.create();
			conjunto=new TemporalIF[((TypeRecord)r.getTypeBase()).getTamanyo()];
			cb.addQuadruple("ASP", r.getVariable(), temp2);
			for(int i=0; i<((TypeRecord) r.getTypeBase()).getTamanyo();i++)
			{
				conjunto[i]=tf.create();
				cb.addQuadruple("REF", temp2,conjunto[i]);
				cb.addQuadruple("DEC",temp2);//Cambiar a INC con pila creciente
			}
			cb.addQuadruple("ASP", r.getVariable(), temp2);
		}
		
		else if(r.esCampoRegistro()&&r.esReferencia())
		{
			validoPorRef=false;
			temp2=tf.create();
			cb.addQuadruple("MOVE", r.getVariable(), temp2);
			cb.addQuadruple("SUB",temp2, new Value(r.getOffset()), temp2); //Cambiar a ADD si la pila es creciente
			cb.addQuadruple("REF", temp2, temp);
		}
		else if(r.esCampoRegistro())
		{
			temp2=tf.create();
			cb.addQuadruple("ASP", r.getVariable(), temp2);
			cb.addQuadruple("SUB",temp2, new Value(r.getOffset()), temp2); //Cambiar a ADD si la pila es creciente
			cb.addQuadruple("REF", temp2, temp);
		}
		else if(r.esReferencia())
		{
			validoPorRef=false;
			cb.addQuadruple("REF", r.getVariable(), temp);
		}
		else
		{
			temp2=tf.create();
			cb.addQuadruple("MOVE", r.getVariable(), temp);
			if(r.getTipoModificacion()==TipoModificacion.VARIABLE)
				cb.addQuadruple("ASP", r.getVariable(), temp2);
		}
		this.setIntermediateCode(cb.create());
		
	}
	
	public Expresion(ExpresionPuntero ep)
	{
		if(ep==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		typeBase=ep.getTypeBase();
		validoPorRef=false;
		
		//Codigo intermedio
		temp=ep.getTemp();
		this.setIntermediateCode(ep.getIntermediateCode());
		
	}
	
	public Expresion(ExpresionConjunto ec)
	{
		if(ec==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		typeBase=ec.getTypeBase();
		validoPorRef=false;
		
		temp=ec.getTempInicio();
		temp2=ec.getTempFin();
		this.setIntermediateCode(ec.getIntermediateCode());
		
	}
	
	public Expresion(ExpresionPertenencia ep)
	{
		if(ep==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		typeBase=ep.getTypeBase();
		validoPorRef=false;
		
		temp=ep.getTemp();
		this.setIntermediateCode(ep.getIntermediateCode());
		
	}
	
	public Expresion(Expresion e1, Operador operador, Expresion e2, ScopeManagerIF scopeManager, SemanticErrorManager error)
	{
		if(e1==null||operador==null||e2==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		typeBase=null;
		validoPorRef=false;
		if(operador.getOperador().equalsIgnoreCase("or")||operador.getOperador().equalsIgnoreCase("and"))
		{
			if((!(e1.getTypeBase() instanceof TypeSimple))||(!(((TypeSimple)e1.getTypeBase()).getTipo()==Tipo.BOOLEANO)))
				error.semanticFatalError("operacion "+operador.getOperador().toUpperCase() +" solo entre booleanos" );
			if((!(e2.getTypeBase() instanceof TypeSimple))||(!(((TypeSimple)e2.getTypeBase()).getTipo()==Tipo.BOOLEANO)))
				error.semanticFatalError("operacion "+operador.getOperador().toUpperCase() +" solo entre booleanos" );
			typeBase=new TypeSimple(scopeManager.getCurrentScope(), Tipo.BOOLEANO);
		}
		else if(operador.getOperador().equalsIgnoreCase("=")||operador.getOperador().equalsIgnoreCase("<>"))
		{
			if((e1.getTypeBase() instanceof TypeSimple)&&(e2.getTypeBase() instanceof TypeSimple))
				typeBase=new TypeSimple(scopeManager.getCurrentScope(), Tipo.BOOLEANO);
			else if((e1.getTypeBase() instanceof TypePointer)&&(e2.getTypeBase() instanceof TypePointer))
				typeBase=new TypeSimple(scopeManager.getCurrentScope(), Tipo.BOOLEANO);
			else if((e1.getTypeBase() instanceof TypeSimple)&&(((TypeSimple)e1.getTypeBase()).getTipo()==Tipo.ENTERO)&&(e2.getTypeBase() instanceof TypePointer))
				typeBase=new TypeSimple(scopeManager.getCurrentScope(), Tipo.BOOLEANO);
			else if((e1.getTypeBase() instanceof TypePointer)&&(e2.getTypeBase() instanceof TypeSimple)&&(((TypeSimple)e2.getTypeBase()).getTipo()==Tipo.ENTERO))
				typeBase=new TypeSimple(scopeManager.getCurrentScope(), Tipo.BOOLEANO);
			else
				error.semanticFatalError("operacion "+operador.getOperador().toUpperCase() +" solo entre booleanos, enteros o direcciones de memoria" );
		}
		else if(operador.getOperador().equalsIgnoreCase(">")||operador.getOperador().equalsIgnoreCase("<"))
		{
			if((e1.getTypeBase() instanceof TypeSimple)&&(((TypeSimple)e1.getTypeBase()).getTipo()==Tipo.ENTERO)&&(e2.getTypeBase() instanceof TypeSimple)&&(((TypeSimple)e2.getTypeBase()).getTipo()==Tipo.ENTERO))
				typeBase=new TypeSimple(scopeManager.getCurrentScope(), Tipo.BOOLEANO);
			else if((e1.getTypeBase() instanceof TypePointer)&&(e2.getTypeBase() instanceof TypePointer))
				typeBase=new TypeSimple(scopeManager.getCurrentScope(), Tipo.BOOLEANO);
			else if((e1.getTypeBase() instanceof TypeSimple)&&(((TypeSimple)e1.getTypeBase()).getTipo()==Tipo.ENTERO)&&(e2.getTypeBase() instanceof TypePointer))
				typeBase=new TypeSimple(scopeManager.getCurrentScope(), Tipo.BOOLEANO);
			else if((e1.getTypeBase() instanceof TypePointer)&&(e2.getTypeBase() instanceof TypeSimple)&&(((TypeSimple)e2.getTypeBase()).getTipo()==Tipo.ENTERO))
				typeBase=new TypeSimple(scopeManager.getCurrentScope(), Tipo.BOOLEANO);
			else
				error.semanticFatalError("operacion "+operador.getOperador().toUpperCase() +" solo entre enteros o direcciones de memoria" );
		}
		else if(operador.getOperador().equalsIgnoreCase("+")||operador.getOperador().equalsIgnoreCase("-"))
		{
			if((e1.getTypeBase() instanceof TypeSimple)&&(((TypeSimple)e1.getTypeBase()).getTipo()==Tipo.ENTERO)&&(e2.getTypeBase() instanceof TypeSimple)&&(((TypeSimple)e2.getTypeBase()).getTipo()==Tipo.ENTERO))
				typeBase=new TypeSimple(scopeManager.getCurrentScope(), Tipo.ENTERO);
			else if(operador.getOperador().equalsIgnoreCase("+")&&(e1.getTypeBase() instanceof TypeSet)&&(e2.getTypeBase() instanceof TypeSet)&&(e1.getTypeBase().equals(e2.getTypeBase())))
				typeBase=e1.getTypeBase();
			else if((e1.getTypeBase() instanceof TypeSimple)&&(((TypeSimple)e1.getTypeBase()).getTipo()==Tipo.ENTERO)&&(e2.getTypeBase() instanceof TypePointer))
			{
				//Tipo Entero de vuelta
				typeBase=e1.getTypeBase();
			}
			else if((e1.getTypeBase() instanceof TypePointer)&&(e2.getTypeBase() instanceof TypeSimple)&&(((TypeSimple)e2.getTypeBase()).getTipo()==Tipo.ENTERO))
			{
				//Tipo Entero de vuelta
				typeBase=e2.getTypeBase();
			}
			else
				error.semanticFatalError("operacion "+operador.getOperador().toUpperCase() +" solo entre enteros, direcciones de memoria o conjuntos del mismo tipo" );
		}
		if(typeBase==null)
			error.semanticFatalError("Operacion no permitida");
		
		
		//Codigo intermedio
		TemporalFactoryIF tf= CompilerContext.getTemporalFactory(scopeManager.getCurrentScope());
		LabelFactoryIF lf=CompilerContext.getLabelFactory();
		temp=tf.create();
		IntermediateCodeBuilderIF cb = new IntermediateCodeBuilder (scopeManager.getCurrentScope());
		TemporalIF t1= e1.getTemp();
		TemporalIF t2= e2.getTemp();		
		if(operador.getOperador().equalsIgnoreCase("or"))
		{	
			LabelIF fin=lf.create();
			cb.addQuadruples(e1.getIntermediateCode());
			cb.addQuadruple("MOVE", t1, temp);
			cb.addQuadruple("BNZ",fin);
			cb.addQuadruples(e2.getIntermediateCode());
			cb.addQuadruple("OR", t1, t2, temp);
			cb.addQuadruple("LABEL",fin);
		}
		else if(operador.getOperador().equalsIgnoreCase("and"))
		{
			LabelIF fin=lf.create();
			cb.addQuadruples(e1.getIntermediateCode());
			cb.addQuadruple("MOVE", t1, temp);
			cb.addQuadruple("BZ",fin);
			cb.addQuadruples(e2.getIntermediateCode());
			cb.addQuadruple("AND", t1, t2, temp);
			cb.addQuadruple("LABEL",fin);
		}
		else if(operador.getOperador().equalsIgnoreCase("+"))
		{
			if(typeBase instanceof TypeSet)
			{
				TemporalIF[] conj1=e1.getConjunto();
				TemporalIF[] conj2=e2.getConjunto();
				cb.addQuadruples(e1.getIntermediateCode());
				cb.addQuadruples(e2.getIntermediateCode());
				for(int i=0; i<conj1.length; i++)
				{
					cb.addQuadruple("OR", conj1[i],conj2[i], conj1[i]);
				}
				conjunto=conj1;
				
				
			}
			else
			{
				cb.addQuadruples(e1.getIntermediateCode());
				cb.addQuadruples(e2.getIntermediateCode());
				cb.addQuadruple("ADD", t1, t2, temp);
			}
		}
		else if(operador.getOperador().equalsIgnoreCase("-"))
		{
			cb.addQuadruples(e1.getIntermediateCode());
			cb.addQuadruples(e2.getIntermediateCode());
			cb.addQuadruple("SUB", t1, t2, temp);
		}
		else if(operador.getOperador().equalsIgnoreCase("="))
		{
			cb.addQuadruples(e1.getIntermediateCode());
			cb.addQuadruples(e2.getIntermediateCode());
			LabelIF igual=lf.create();
			LabelIF fin=lf.create();
			cb.addQuadruple("COMP", t1, t2);
			cb.addQuadruple("BZ", igual);
			cb.addQuadruple("MOVE", new Value(0), temp);
			cb.addQuadruple("BR", fin);
			cb.addQuadruple("LABEL", igual);
			cb.addQuadruple("MOVE", new Value(1), temp);
			cb.addQuadruple("LABEL", fin);
		}
		else if(operador.getOperador().equalsIgnoreCase(">"))
		{
			cb.addQuadruples(e1.getIntermediateCode());
			cb.addQuadruples(e2.getIntermediateCode());
			LabelIF mayor=lf.create();
			LabelIF fin=lf.create();
			cb.addQuadruple("COMP", t2, t1);
			cb.addQuadruple("BN", mayor);
			cb.addQuadruple("MOVE", new Value(0), temp);
			cb.addQuadruple("BR", fin);
			cb.addQuadruple("LABEL", mayor);
			cb.addQuadruple("MOVE", new Value(1), temp);
			cb.addQuadruple("LABEL", fin);
			
		}
		else if(operador.getOperador().equalsIgnoreCase("<"))
		{
			cb.addQuadruples(e1.getIntermediateCode());
			cb.addQuadruples(e2.getIntermediateCode());
			LabelIF mayor=lf.create();
			LabelIF fin=lf.create();
			cb.addQuadruple("COMP", t1, t2);
			cb.addQuadruple("BP", mayor);
			cb.addQuadruple("MOVE", new Value(1), temp);
			cb.addQuadruple("BR", fin);
			cb.addQuadruple("LABEL", mayor);
			cb.addQuadruple("MOVE", new Value(0), temp);
			cb.addQuadruple("LABEL", fin);
		}
		else if(operador.getOperador().equalsIgnoreCase("<>"))
		{
			cb.addQuadruples(e1.getIntermediateCode());
			cb.addQuadruples(e2.getIntermediateCode());
			LabelIF igual=lf.create();
			LabelIF fin=lf.create();
			cb.addQuadruple("COMP", t1, t2);
			cb.addQuadruple("BZ", igual);
			cb.addQuadruple("MOVE", new Value(1), temp);
			cb.addQuadruple("BR", fin);
			cb.addQuadruple("LABEL", igual);
			cb.addQuadruple("MOVE", new Value(0), temp);
			cb.addQuadruple("LABEL", fin);
		}
		this.setIntermediateCode(cb.create());
		
	}
	
	public Expresion(LlamadaFuncionOProcedimiento lp, ScopeManagerIF scopeManager, SemanticErrorManager error)
	{
		if(lp==null)
			CompilerContext.getSemanticErrorManager().semanticFatalError("Error previo de sintaxis");
		validoPorRef=false;
		if(lp.getTipoLlamada()!=TipoLlamada.FUNCION)
			error.semanticFatalError(lp.getNombre()+ " es un procedimiento no una funcion");
		else
			typeBase=lp.getTypeBase();
		
		this.setIntermediateCode(lp.getIntermediateCode());
		temp=lp.getTemp();
	}
	
	public TemporalIF getTemp()
	{
		return temp;
	}
	
	public TemporalIF getTemp2()
	{
		return temp2;
	}
	
	public TemporalIF[] getConjunto()
	{
		return conjunto;
	}
	
	public TypeBase getTypeBase()
	{
		return typeBase;
	}
	
	public boolean getValidoPorRef()
	{
		return validoPorRef;
	}

}
