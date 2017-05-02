package wanghg.rhigosis;
import java_cup.runtime.Symbol;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;

%%

%class Lexer
%ctorarg String unit
%init{
    this.unit = unit;
%init}
%{
    private final String unit;
    private ComplexSymbol symbol = null;
    private Symbol symbol(int id) {
        return null;
    }
%}
%cupsym Sym
%cup
%line
%column

//MultilineComment = "/*" ( [^*] | "*"+ [^*/] )* "*"+ "/"
//SinglelineComment = "//" .* "\n"

%%

[^] { throw new RuntimeException(String.format("\n%s\n%d,%d", unit, yyline + 1, yycolumn + 1)); }

