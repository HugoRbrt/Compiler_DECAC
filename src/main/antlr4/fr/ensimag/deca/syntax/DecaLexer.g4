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

// Any character
fragment STRING_CAR: ~('"' | '\\' | '\n' | '\t');

fragment DIGIT: '0'..'9';

INT: DIGIT+;

// Special symbols
COMMA: ',';

OBRACE: '{';

CBRACE: '}';

OPARENT: '(';

CPARENT: ')';

OR: '||';

SEMI: ';';

// Whitespace
WS: (' ' | EOL | '\r') {skip();};

// Keywords
PRINT: 'print';

PRINTLN: 'println';

// Commment
SEP_COMMENT_PARA: '/*' .*? '*/' {skip();};

// Single-line comment
SEP_COMMENT_LINE:  '//' .*? (EOL | EOF) {skip();};

// String
STRING: '"' (STRING_CAR | '\\"' | '\\\\')* '"';






