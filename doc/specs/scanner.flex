package compiler.lexical;

import compiler.syntax.sym;
import compiler.lexical.Token;
import es.uned.compiler.lexical.ScannerIF;
import es.uned.compiler.lexical.LexicalError;
import es.uned.compiler.lexical.LexicalErrorManager;

// incluir aqui, si es necesario otras importaciones

%%
 
%public
%class Scanner
%char
%line
%column
%cup
%full
%ignorecase

%implements ScannerIF
%scanerror LexicalError

// incluir aqui, si es necesario otras directivas

%{
  LexicalErrorManager lexicalErrorManager = new LexicalErrorManager ();
  private boolean comment = false;
  StringBuffer string = new StringBuffer();
%} 

%eofval{
	if(comment==true)
	{               
      	LexicalError error = new LexicalError ("Fin de archivo sin cerrar un comentario");
            error.setLine (yyline + 1);
            error.setColumn (yycolumn + 1);
            error.setLexema (yytext ());
            lexicalErrorManager.lexicalError (error);
		System.exit(1);
	}

		Token token =new Token(sym.EOF);
      	token.setLine (yyline + 1);
            token.setColumn (yycolumn + 1);
            token.setLexema (yytext ());
           	return token;
	
%eofval}


FinLinea = \r|\n|\r\n
ComentarioLinea = "//"[^\{\}\r\n]*{FinLinea}
MalComentario= "//"[^\{\}\r\n]*\{[^\r\n]*{FinLinea}
MalComentario2= "//"[^\{\}\r\n]*\}[^\r\n]*{FinLinea}

espacio = [\n\r\f\t" "]+

identificador = [a-zA-Z][a-zA-Z0-9]*
malidentif = [a-zA-Z][a-zA-Z0-9]*([^\0-\}][a-zA-Z0-9]*)*
LiteralEntero = [0-9]+

%state COMENTARIO CADENA
%%


<YYINITIAL> 
{
	{espacio}		{}


    "and" 		 	{
				   Token token =new Token(sym.AND);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
	       		   return token;
                        }
           			     
	
    "begin" 		{
				   Token token =new Token(sym.BEGIN);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }

    "boolean" 		{
				   Token token =new Token(sym.BOOLEAN);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }

    "const" 		{
				   Token token =new Token(sym.CONST);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }
    "do" 			{
				   Token token =new Token(sym.DO);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }

    "else" 			{
				   Token token =new Token(sym.ELSE);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }

    "end"	 		{
		  Token token =new Token(sym.END);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }

    "false" 		{
				   Token token =new Token(sym.FALSE);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }

    "for" 			{
				   Token token =new Token(sym.FOR);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }

    "function" 		{
				   Token token =new Token(sym.FUNCTION);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }

    "if"	 		{
				   Token token =new Token(sym.IF);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }

    "in"	 		{
				   Token token =new Token(sym.IN);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }

    "integer" 		{
				   Token token =new Token(sym.INTEGER);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }

    "or"	 		{
				   Token token =new Token(sym.OR);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }

    "procedure" 		{
				   Token token =new Token(sym.PROCEDURE);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }

    "program" 		{
				   Token token =new Token(sym.PROGRAM);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }

    "set of" 		{
				   Token token =new Token(sym.SETOF);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }

    "record" 		{
				   Token token =new Token(sym.RECORD);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }

    "repeat" 		{
				   Token token =new Token(sym.REPEAT);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }


    "then" 			{
				   Token token =new Token(sym.THEN);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }

    "to" 			{
				   Token token =new Token(sym.TO);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }

    "true" 			{
				   Token token =new Token(sym.TRUE);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }

    "type"	 		{
				   Token token =new Token(sym.TYPE);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }
    "until" 		{
				   Token token =new Token(sym.UNTIL);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }

    "var" 			{
				   Token token =new Token(sym.VAR);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }

    "write" 		{
				   Token token =new Token(sym.WRITE);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }

    "writeln" 		{
				   Token token =new Token(sym.WRITELN);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }

	{identificador}	{
				   Token token =new Token(sym.IDENTIFICADOR);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                       	}
	
	{malidentif}	{  LexicalError error = new LexicalError ("Caracter No Valido en Identificador: " +yytext());
                           error.setLine (yyline + 1);
                           error.setColumn (yycolumn + 1);
                           error.setLexema (yytext ());
                           lexicalErrorManager.lexicalError (error);
                        }

	\"               	{ string.setLength(0); yybegin(CADENA); }

	{LiteralEntero}	{
      			   IntegerToken token =new IntegerToken(sym.LITERALENTERO);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }

    "(" 			{
				   Token token =new Token(sym.OPENPARENTHESIS);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }

    ")" 			{
				   Token token =new Token(sym.CLOSEPARENTHESIS);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }

   "[" 			{
				   Token token =new Token(sym.OPENSQUAREBRACKET);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }

    "]" 			{
				   Token token =new Token(sym.CLOSESQUAREBRACKET);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }


    "," 			{
				   Token token =new Token(sym.COMMA);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }

    ";" 			{
				   Token token =new Token(sym.SEMICOLON);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }

    ":" 			{
				   Token token =new Token(sym.COLON);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }

    "=" 			{
				   Token token =new Token(sym.EQUAL);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }
    "<>" 			{
				   Token token =new Token(sym.NOTEQUAL);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }

    "+" 			{
				   Token token =new Token(sym.PLUS);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }

    "-" 			{
				   Token token =new Token(sym.MINUS);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }

    ">" 			{
				   Token token =new Token(sym.GREATERTHAN);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }
    "<" 			{
				   Token token =new Token(sym.LESSTHAN);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }

    ":=" 			{
				   Token token =new Token(sym.ASSIGMENT);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }

    "." 			{
				   Token token =new Token(sym.POINT);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }

    ".." 			{
				   Token token =new Token(sym.TWOPOINTS);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }


    "^" 			{
				   Token token =new Token(sym.CARET);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }

    "@" 			{
				   Token token =new Token(sym.AT);
      		         token.setLine (yyline + 1);
                           token.setColumn (yycolumn + 1);
                           token.setLexema (yytext ());
           			       return token;
                        }


	//Comentarios
	\{			{comment= true; yybegin(COMENTARIO);}

	{ComentarioLinea}	{}

	{MalComentario}   {  LexicalError error = new LexicalError ("No puede aparecer { en un comentario de linea");
                           error.setLine (yyline + 1);
                           error.setColumn (yycolumn + 1);
                           error.setLexema (yytext ());
                           lexicalErrorManager.lexicalError (error);
                        }

	{MalComentario2}   {  LexicalError error = new LexicalError ("No puede aparecer } en un comentario de linea");                
                           error.setLine (yyline + 1);
                           error.setColumn (yycolumn + 1);
                           error.setLexema (yytext ());
                           lexicalErrorManager.lexicalError (error);
                        }

	"}"
				{  LexicalError error = new LexicalError ("Estas cerrando un comentario sin abrirle");                
                           error.setLine (yyline + 1);
                           error.setColumn (yycolumn + 1);
                           error.setLexema (yytext ());
                           lexicalErrorManager.lexicalError (error);
                        }


    
    // error en caso de coincidir con ningún patrón
	[^]     
                        {  System.out.println(yytext());                
                           LexicalError error = new LexicalError ();
                           error.setLine (yyline + 1);
                           error.setColumn (yycolumn + 1);
                           error.setLexema (yytext ());
                           lexicalErrorManager.lexicalError (error);
                        }
    
}


<COMENTARIO>
{
	\{
			{ 	 LexicalError error = new LexicalError ("No se permite el caracter { en un comentario");
                         error.setLine (yyline + 1);
                         error.setColumn (yycolumn + 1);
                         error.setLexema (yytext ());
                         lexicalErrorManager.lexicalError (error);
				 comment= false; yybegin(YYINITIAL);
               	}
	[^\{\}]	{}

	\} 		{	 comment= false; yybegin(YYINITIAL);}

}

<CADENA>
{
	[^\n\r\"]+	{	string.append( yytext() ); }

	\"		{ 	yybegin(YYINITIAL);
				Token token =new Token(sym.CADENA);
      		      token.setLine (yyline + 1);
                        token.setColumn (yycolumn + 1);
                        token.setLexema (string.toString());
           			return token;
                  }
	{FinLinea}  {	LexicalError error = new LexicalError ("Cadena sin cerrar");
                         error.setLine (yyline + 1);
                         error.setColumn (yycolumn + 1);
                         error.setLexema (yytext ());
                         lexicalErrorManager.lexicalError (error);
				 yybegin(YYINITIAL);
               	}
}
	

                         


