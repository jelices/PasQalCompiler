
package compiler.test;

import java.io.FileReader;

import compiler.lexical.Scanner;
import compiler.syntax.parser;

import es.uned.compiler.lexical.ScannerIF;
import es.uned.compiler.lexical.ScannerStub;
import es.uned.compiler.syntax.ParserIF;
import es.uned.compiler.syntax.ParserStub;
import es.uned.compiler.syntax.SyntaxErrorManager;

/**
 * Test class for syntax cheching.
 */
public class SyntaxTestCase
{
    /**
     * Constructor for SyntaxisTestCase.
     */
    public SyntaxTestCase ()
    {
    }

    /**
     * Tests the parser against a test file.
     * @param fileName The path of the file test.
     */
    public void testParse (String fileName)
    {
        // TODO: Student work
        //       Change lines for comments
        SyntaxErrorManager syntaxErrorManager = new SyntaxErrorManager ();       
        try
        {
            syntaxErrorManager.syntaxInfo ("Input file > " + fileName);
            FileReader aFileReader = new FileReader (fileName);
            //ScannerIF aScanner = new ScannerStub (aFileReader); 
            //ParserIF aParser   = new ParserStub (aScanner);     
            // TODO: Change the two preceding lines by the following ones 
            Scanner aScanner = new Scanner (aFileReader);
            parser aParser   = new parser (aScanner);  
            
            aParser.parse (); 
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
    }

    /**
     * Starts the parser test case.
     * @param args Arguments Array containing the path to the input file at the
     *            firts element.
     */
    public static void main (String args[])
    {
        if (args.length < 1 || args.length > 1)
        {
            System.err.println ("Uso:  java SyntaxTestCase ./test/ejemplo.p");
        }
        else
        {
            SyntaxTestCase syntaxTest = new SyntaxTestCase ();
            String fileName = args[0];
            syntaxTest.testParse (fileName);
        }
    }
}
