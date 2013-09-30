
package compiler;

import java.util.HashMap;

import es.uned.compiler.code.FinalCodeFactory;
import es.uned.compiler.code.FinalCodeFactoryIF;
import es.uned.compiler.intermediate.LabelFactory;
import es.uned.compiler.intermediate.LabelFactoryIF;
import es.uned.compiler.intermediate.TemporalFactory;
import es.uned.compiler.intermediate.TemporalFactoryIF;
import es.uned.compiler.semantic.SemanticErrorManager;
import es.uned.compiler.semantic.symbol.ScopeIF;
import es.uned.compiler.semantic.symbol.ScopeManager;
import es.uned.compiler.semantic.symbol.ScopeManagerIF;
import es.uned.compiler.syntax.SyntaxErrorManager;

/**
 * Class to manage compiler context. It includes references for SyntaxErrorManager,
 * SemanticErrorManager, ScopeManager and FinalCodeFactory.
 */

public class CompilerContext
{
    private static SyntaxErrorManager   syntaxErrorManager   = new SyntaxErrorManager ();
    private static SemanticErrorManager semanticErrorManager = new SemanticErrorManager ();
    private static ScopeManagerIF       scopeManager         = new ScopeManager ();
    private static FinalCodeFactoryIF   finalCodeFactory     = new FinalCodeFactory ();
    
   
    private static HashMap<ScopeIF,TemporalFactoryIF> temporalFactories=new HashMap<ScopeIF, TemporalFactoryIF>();
    private static LabelFactoryIF labelFactory= new LabelFactory();
    /**
     * Returns the scopeManager.
     * @return Returns the scopeManager.
     */
    public static ScopeManagerIF getScopeManager ()
    {
        return scopeManager;
    }
    
    /**
     * Returns the semanticErrorManager.
     * @return Returns the semanticErrorManager.
     */
    public static SemanticErrorManager getSemanticErrorManager ()
    {
        return semanticErrorManager;
    }

    /**
     * Returns the syntaxErrorManager.
     * @return Returns the syntaxErrorManager.
     */
    public static SyntaxErrorManager getSyntaxErrorManager ()
    {
        return syntaxErrorManager;
    }

    /**
     * Returns the finalCodeFactory.
     * @return Returns the finalCodeFactory.
     */
    public static FinalCodeFactoryIF getFinalCodeFactory ()
    {
        return finalCodeFactory;
    }
    
    public static TemporalFactoryIF getTemporalFactory (ScopeIF scope)
    {
       TemporalFactoryIF tf = (TemporalFactoryIF) temporalFactories.get(scope);
       if (tf == null) 
       {
           tf = new TemporalFactory (scope);
           temporalFactories.put (scope, tf);
       }
       return tf;
    }
    
    public static LabelFactoryIF getLabelFactory ()
    {
      return labelFactory;
    }

    
    
}
