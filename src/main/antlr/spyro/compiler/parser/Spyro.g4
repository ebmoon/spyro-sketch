grammar Spyro;

@header {
package spyro.compiler.parser;
}

parse : program EOF ;

program : declVariables declSignatures declLanguage declExamples declAssumptions? ;

declVariables : VARIABLES LBRACE declVar+ RBRACE ;

declVar 
 : type ID SEMI				#declVisibleVar 
 | HIDDENVAR type ID SEMI	#declHiddenVar
 ; 

declSignatures : SIGNATURES LBRACE declSig+ RBRACE ;

declSig : expr SEMI ;

declLanguage : LANGUAGE LBRACE declLanguageRule+ RBRACE ;

declLanguageRule : type ID ARROW (expr ('|' expr)*) SEMI ;

declExamples : EXAMPLES LBRACE declExampleRule* RBRACE ;

declExampleRule : type ID ARROW (expr ('|' expr)*) SEMI ;

declAssumptions : ASSUMPTIONS LBRACE declAssumption RBRACE SEMI ;

declAssumption : expr SEMI ;

type : ID;

expr
 : LPAREN ID RPAREN ARROW expr              #anonFuncExpr
 | LPAREN expr RPAREN						#parenExpr
 | ID LPAREN (expr (',' expr)*)? RPAREN		#functionExpr
 | MINUS expr                           	#unaryMinusExpr
 | NOT expr                             	#notExpr
 | expr op=(MULT | DIV | MOD) expr      	#multiplicationExpr
 | expr op=(PLUS | MINUS) expr          	#additiveExpr
 | expr op=(LTEQ | GTEQ | LT | GT) expr 	#relationalExpr
 | expr op=(EQ | NEQ) expr              	#equalityExpr
 | expr AND expr                        	#andExpr
 | expr OR expr                         	#orExpr
 | atom                                 	#atomExpr
 ;

atom
 : INT  		 			#numberAtom
 | (TRUE | FALSE)			#booleanAtom
 | ID            			#idAtom
 | NULL          			#nullAtom
 | HOLE			 			#unsizedHoleAtom
 | HOLE LPAREN INT RPAREN	#sizedHoleAtom
 ;

VARIABLES : 'variables';
SIGNATURES : 'signatures';
LANGUAGE : 'language';
EXAMPLES : 'examples';
ASSUMPTIONS : 'assumptions';

OR : '||';
AND : '&&';
EQ : '==';
NEQ : '!=';
GT : '>';
LT : '<';
GTEQ : '>=';
LTEQ : '<=';
PLUS : '+';
MINUS : '-';
MULT : '*';
DIV : '/';
MOD : '%';
NOT : '!';

SEMI : ';';
ASSIGN : '=';
LPAREN : '(';
RPAREN : ')';
LBRACE : '{';
RBRACE : '}';

ARROW : '->';

HIDDENVAR : 'hidden';
TRUE : 'true';
FALSE : 'false';
NULL : 'null';
HOLE : '??';

ID
 : [a-zA-Z_] [a-zA-Z_0-9]*
 ;

INT
 : [0-9]+
 ;

FLOAT
 : [0-9]+ '.' [0-9]* 
 | '.' [0-9]+
 ;

STRING
 : '"' (~["\r\n] | '""')* '"'
 ;

COMMENT
 : '//' ~[\r\n]* -> skip
 ;

SPACE
 : [ \t\r\n] -> skip
 ;

OTHER
 : . 
 ;