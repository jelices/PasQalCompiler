package compiler.test;

import java.io.FileReader;

import JFlex.sym;

import compiler.lexical.Token;
import compiler.lexical.Scanner;

import es.uned.compiler.lexical.LexicalErrorManager;
import es.uned.compiler.lexical.ScannerIF;
import es.uned.compiler.lexical.ScannerStub;

/**
 * Test class for lexical checking. 
 */

public class LexicalTestCase
{
    
    /**
     * Constructor for LexicalTestCase.
     */
    public LexicalTestCase ()
    {            
    }
              
    /**
     * Tests the scanner.
     * @param test The test file name.
     */
    public void testScan (String fileName)
    {
        // TODO: Student work
        //       Change lines for comments.
        LexicalErrorManager lexicalErrorManager = new LexicalErrorManager ();
        try
        {
            lexicalErrorManager.lexicalInfo ("Input file > " + fileName);
            FileReader aFileReader = new FileReader (fileName);
            //ScannerIF aScanner = new ScannerStub (aFileReader);
            // TODO: Change preceding line by the following one       
            Scanner aScanner = new Scanner (aFileReader);
            
            Object anObject = aScanner.next_token ();
            while (anObject instanceof Token)
            {
                Token aToken = (Token) anObject;
                if (aToken.sym == sym.EOF) break;
                lexicalErrorManager.lexicalInfo (aToken);
                anObject = aScanner.next_token ();
            }
            lexicalErrorManager.lexicalInfo ("End of file.");
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
    }
      
	/**
     * Starts the scanner test case.
     * @param args Arguments Array containing the path to the input file at the
     *            firts element.
     */
    public static void main (String args[])
    {
        if (args.length < 1 || args.length > 1)
        {
            for (int i = 0; i < args.length; i++)
                System.out.println (args[0]);
            System.err.println ("Use:  java LexicalTestCase ./test/ejemplo.p");
        }
        else
        {
            LexicalTestCase lexicalTest = new LexicalTestCase ();
            String fileName = args[0];
            lexicalTest.testScan (fileName);
        }
    }	
}
