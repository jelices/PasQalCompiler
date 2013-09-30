
package compiler.test;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import compiler.CompilerContext;
import compiler.lexical.Scanner;
import compiler.syntax.parser;

import es.uned.compiler.code.FinalCodeFactoryIF;
import es.uned.compiler.lexical.ScannerIF;
import es.uned.compiler.lexical.ScannerStub;
import es.uned.compiler.semantic.SemanticErrorManager;
import es.uned.compiler.semantic.symbol.Scope;
import es.uned.compiler.semantic.symbol.ScopeIF;
import es.uned.compiler.semantic.symbol.ScopeManagerIF;
import es.uned.compiler.semantic.symbol.SymbolIF;
import es.uned.compiler.semantic.symbol.SymbolTableIF;
import es.uned.compiler.semantic.type.TypeIF;
import es.uned.compiler.semantic.type.TypeTableIF;
import es.uned.compiler.syntax.ParserIF;
import es.uned.compiler.syntax.ParserStub;
import es.uned.compiler.syntax.SyntaxErrorManager;

/**
 * Test clas for final checking (semantic & code generation).
 */

public class FinalTestCase
{
    private static SemanticErrorManager semanticErrorManager = null;
    private static String sourceFileExtension = ".p";  
    
    /**
     * Constructor for FinalTestCase.
     */
    public FinalTestCase ()
    {
    }

    /**
     * Tests the parser against a test file.
     * @param fileName The path of the file test.
     */
    public void testSemantic (String fileName)
    {
        SyntaxErrorManager syntaxErrorManager = new SyntaxErrorManager ();
        
        try 
        {
        	syntaxErrorManager.syntaxInfo ("Input file > " + fileName);

            FinalCodeFactoryIF finalCodeFactory = CompilerContext.getFinalCodeFactory ();            
            String ensName = fileName.replaceFirst (sourceFileExtension+"$", ".ens");
            syntaxErrorManager.syntaxInfo ("Output file > " + ensName);
            File outputFile = new File (ensName);
            finalCodeFactory.setFile (outputFile);
            
            FileReader aFileReader = new FileReader (fileName);
            //ScannerIF aScanner = new ScannerStub (aFileReader); 
            //ParserIF aParser   = new ParserStub (aScanner);     
            // TODO: Change the two preceding lines by the following ones            
             Scanner aScanner = new Scanner (aFileReader);
             parser aParser   = new parser (aScanner);  
            
            
            aParser.parse ();
            if(CompilerContext.getSyntaxErrorManager().getNumberOfErrors()==0)
            	logSemantics ();
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
    }

    /**
     * Logs the semantic info.
     */
    private void logSemantics ()
    {
        logTypeTables ();
        logSymbolTables ();
    }
    
    /**
     * Logs all type tables.
     */
    private void logTypeTables ()
    {
        semanticErrorManager.semanticDebug ("** TYPE TABLES **");
        ScopeManagerIF scopeManager = CompilerContext.getScopeManager ();
        Iterator scopesIt = scopeManager.getAllScopes ().iterator ();
        List dumpedTypeTables = new ArrayList ();
        while (scopesIt.hasNext ())
        {
            Scope aScope = (Scope) scopesIt.next ();
            TypeTableIF aTypeTable = aScope.getTypeTable ();
            if (!dumpedTypeTables.contains (aTypeTable)) logTypeTable (aTypeTable);
        }
    }
    
    /**
     * Logs a type table
     * @param type Table
     */
    private void logTypeTable(TypeTableIF typeTable)
    {	
        Iterator typesIt = typeTable.getTypes ().iterator ();
        while (typesIt.hasNext ())
        {
            TypeIF aType = (TypeIF) typesIt.next ();
            semanticErrorManager.semanticDebug (aType);
        }
    }
    
    /**
     * Logs all symbol tables.
     */
    private void logSymbolTables ()
    {
        semanticErrorManager.semanticDebug ("** SYMBOL TABLES **");
        ScopeManagerIF scopeManager = CompilerContext.getScopeManager ();
        Iterator scopesIt = scopeManager.getAllScopes ().iterator ();
        while (scopesIt.hasNext ())
        {
            ScopeIF scope = (ScopeIF) scopesIt.next ();
            logSymbolTable (scope.getSymbolTable ());
        }
    }

    /**
     * Logs a symbol table.
     * @param symbolTable the symbol table to log.
     */
    private void logSymbolTable (SymbolTableIF symbolTable)
    {
        semanticErrorManager.semanticDebug ("SYMBOL TABLE ["
                                            + symbolTable.getScope ().getName ()
                                            + "]");
        Iterator symbolsIt = symbolTable.getSymbols ().iterator ();
        while (symbolsIt.hasNext ())
        {
            SymbolIF aSymbol = (SymbolIF) symbolsIt.next ();
            semanticErrorManager.semanticDebug (aSymbol);
        }
    }

    /**
     * Starts the parser test case.
     * @param args Arguments Array containing the path to the input file at the
     *             firts element.
     */
    public static void main (String args[])
    {
        if (args.length < 1 || args.length > 1)
        {
            System.err.println ("Use:  java FinalTestCase ./test/ejemplo." + sourceFileExtension);
        }
        else
        {
            FinalTestCase finalTest = new FinalTestCase ();
            String fileName = args[0];
            semanticErrorManager = CompilerContext.getSemanticErrorManager ();
            finalTest.testSemantic (fileName);
        }
    }
}
