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

"/*" ( [^*] | [*]+ [^*/] )* [*]+ [/]  { return new Symbol(Sym.MULTI_LINE_COMMENT); }

"\n"+    { return new Symbol(Sym.NEWLINE); }

[^] { throw new RuntimeException(String.format("\n%s\n%d,%d", source, yyline + 1, yycolumn + 1)); }

