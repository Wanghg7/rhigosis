package wanghg.rhigosis;
import java_cup.runtime.Symbol;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;

%%

%class SGLexer
%ctorarg String unit
%ctorarg ComplexSymbolFactory fact
%init{
    this.unit = unit;
    this.fact = fact;
%init}
%{
    private final String unit;
    private final ComplexSymbolFactory fact;
    private Symbol symbol(int id) {
        int line = yyline + 1;
        int column = yycolumn + 1;
        String text = yytext();
        Location left = new Location(unit, line, column);
        Location right = new Location(unit, line, column + text.length());
        Symbol sym = fact.newSymbol(SGSym.terminalNames[id], id, left, right, text);
        return sym;
    }
%}
%cupsym SGSym
%cup
%line
%column

%%

[ ]+ {}

[\n]+ { return symbol(SGSym.NL); }

 [a-zA-Z_][a-zA-Z0-9_]+ { return symbol(SGSym.ID); }

"::=" { return symbol(SGSym.CCE); }

"|" { return symbol(SGSym.BAR); }

"[" { return symbol(SGSym.LBRACK); }

"]" { return symbol(SGSym.RBRACK); }

"{" { return symbol(SGSym.LBRACE); }

"}" { return symbol(SGSym.RBRACE); }

"(" { return symbol(SGSym.LPAREN); }

")" { return symbol(SGSym.RPAREN); }

"‘" [^‘’]+ "’" { return symbol(SGSym.LITERAL); }

<<EOF>> { return symbol(SGSym.EOF); }

[^] { throw new RuntimeException(String.format("%s(%d,%d):《%s》", unit, yyline+1, yycolumn+1, yytext())); }

