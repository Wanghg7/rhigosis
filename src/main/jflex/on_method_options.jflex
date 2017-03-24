
package wanghg.x;

%%

%public

%class SimpleCharByChar

/* scanner methods options begin */

/* default is yylex, overrides %cup(java_cup.runtime.Scanner.next_token())  */
%function next

/* default is Yytoken, end-of-file value is <Scanner>.YYEOF for int */
%int

/* not demoed: %intwrap */

/* not demoed: %type */

/* not demoed: %yylexthrow */

%unicode    // safe to leave out, this is the default

%%

[^] { return 7; }

