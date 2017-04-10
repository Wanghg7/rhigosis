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
    private final ComplexSymbolFactory fact = new ComplexSymbolFactory();
    private final Location BLANK = new Location("", 0, 0);
    private ComplexSymbol symbol = null;
    private Symbol symbol(int id) {
        Location loc = new Location(unit, yyline+1, yycolumn+1);
        if (symbol != null)
            symbol.xright = loc;
        return symbol = (ComplexSymbol)fact.newSymbol(Sym.terminalNames[id], id, loc, BLANK, yytext());
    }
%}
%cupsym Sym
%cup
%line
%column

MultilineComment = "/*" ( [^*] | "*"+ [^*/] )* "*"+ "/"
SinglelineComment = "//" .* "\n"

%x MLS

%%

{MultilineComment}  {}

{SinglelineComment}  {}

"\n"+    {}

[ \t]+  {}

"package" { return symbol(Sym.PACKAGE); }

"abstract" { return symbol(Sym.ABSTRACT); }

"class" { return symbol(Sym.CLASS); }

"." { return symbol(Sym.DOT); }

"{" { return symbol(Sym.LBRACE); }

"}" { return symbol(Sym.RBRACE); }

"@" { return symbol(Sym.AT); }

"(" { return symbol(Sym.LPAREN); }

")" { return symbol(Sym.RPAREN); }

"," { return symbol(Sym.COMMA); }

"[" { return symbol(Sym.LBRACK); }

"]" { return symbol(Sym.RBRACK); }

"⇒" | "=>"  { return symbol(Sym.RDARROW); }

"←" | "<-"  { return symbol(Sym.LARROW); }

":" { return symbol(Sym.COLON); }

";" { return symbol(Sym.SEMICOLON); }

"=" { return symbol(Sym.EQ); }

"0" | [1-9][0-9]* { return symbol(Sym.INT); }

"'" [[:letter:]_][[:letter:]0-9_]* { return symbol(Sym.SYMBOL); }

"'" ( [^'\n] | "\\n" | "\\r" | "\\t" | "\\\\" ) "'" { return symbol(Sym.CHAR); }

[[:letter:]$_][[:letter:]0-9$_]* |
    "`" [^`\n]* "`" |
    [[]!#%&*+/:<=>?@\\\^|~+\p{Sm}\p{So}-]+ { return symbol(Sym.ID); }

\" [^\"\n]* \" { return symbol(Sym.STRING); }

\"\"\" { yybegin(MLS); }

<MLS> \" | \"\" | [^\"] {}

<MLS> \"\"\" { yybegin(YYINITIAL); return symbol(Sym.MSTRING); }

[^] { throw new RuntimeException(String.format("\n%s\n%d,%d", unit, yyline + 1, yycolumn + 1)); }

