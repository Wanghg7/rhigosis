package wanghg.examples.jflex;

%%

%class SuppLexer
%type String

%function next

%%

/* If the list of characters is empty (i.e. [^]), the expression matches any character of the input character set */
[^] { return yytext(); }

