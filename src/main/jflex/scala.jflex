package wanghg.rhigosis;
import java_cup.runtime.Symbol;

%%

%class Lexer
%ctorarg String source
%init{
    this.source = source;
%init}
%{
    private final String source;
%}
%cupsym Sym
%cup
%line
%column

%%

"/*"    { return new Symbol(Sym.MULTI_LINE_COMMENT); }

[^] { throw new RuntimeException(String.format("%s,%d,%d", source, yyline, yycolumn)); }

