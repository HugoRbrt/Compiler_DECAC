lexer grammar DecaLexer;

options {
   language=Java;
   // Tell ANTLR to make the generated lexer class extend the
   // the named class, which is where any supporting code and
   // variables will be placed.
   superClass = AbstractDecaLexer;
}

@members {
}

// Deca lexer rules.

// End of line
fragment EOL: '\n' | '\t';

fragment DIGIT: '0'..'9';

// Floats
fragment NUM: DIGIT+;

fragment SIGN: '+' | '-' | /* epsilon */;

fragment EXP: ('E' | 'e') SIGN NUM;

fragment DEC: NUM '.' NUM;

fragment FLOATDEC: (DEC | DEC EXP) ('F' | 'f' | /* epsilon */);

fragment DIGITHEX: ('0'..'9') | ('A'..'F') | ('a'..'f');

fragment NUMHEX: DIGITHEX+;

fragment FLOATHEX: ('0x' | '0X') NUMHEX '.' NUMHEX ('P' | 'p') SIGN NUM ('F' | 'f' | /* epsilon */);

FLOAT: FLOATDEC | FLOATHEX;

// Integers
fragment POSITIVE_DIGIT: '1'..'9';

INT: '0' | (POSITIVE_DIGIT DIGIT*);

// Special symbols
COMMA: ',';

EQUALS: '=';

EQEQ: '==';

GEQ: '>=';

LEQ: '<=';

GT: '>';

LT: '<';

NEQ: '!=';

OBRACE: '{';

CBRACE: '}';

OPARENT: '(';

CPARENT: ')';

PLUS: '+';

MINUS: '-';

TIMES: '*';

SLASH: '/';

PERCENT: '%';

EXCLAM: '!';

AND: '&&';

OR: '||';

SEMI: ';';

// Whitespace
WS: (' ' | EOL | '\r') {skip();};

// Keywords
ASM: 'asm';

ELSE: 'else';

FALSE: 'false';

IF: 'if';

NULL: 'null';

PRINT: 'print';

PRINTLN: 'println';

PRINTX: 'printx';

PRINTLNX: 'printlnx';

READINT: 'readInt';

READFLOAT: 'readFloat';

TRUE: 'true';

WHILE: 'while';

// Identifier
fragment LETTER: ('a'..'z') | ('A'..'Z');

IDENT: (LETTER | '$' | '_')(LETTER | DIGIT | '$' | '_')*;

// Commment
SEP_COMMENT_PARA: '/*' .*? '*/' {skip();};

// Single-line comment
SEP_COMMENT_LINE:  '//' .*? (EOL | EOF) {skip();};

// String
fragment STRING_CAR: ~('"' | '\\' | '\n' | '\t');

STRING: '"' (STRING_CAR | '\\"' | '\\\\')* '"';

// Multi-line String
MULTI_LINE_STRING: '"' (STRING_CAR | EOL | '\\"' | '\\\\')* '"';

// File inclusion
fragment FILENAME: (LETTER | DIGIT | '.' | '-' | '_')+;

INCLUDE: '#include' (' ')* '"' FILENAME '"' {doInclude(getText());};






