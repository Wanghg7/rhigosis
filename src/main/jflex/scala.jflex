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

MultilineComment = "/*" ( [^*] | "*"+ [^*/] )* "*"+ "/"
SinglelineComment = "//" .* "\n"

%x MLS

%%

{MultilineComment}  { return new Symbol(Sym.MCOMMENT); }

{SinglelineComment}  { return new Symbol(Sym.SCOMMENT); }

"\n"+    { return new Symbol(Sym.NEWLINE); }

[ \t]+  {}

"package" { return new Symbol(Sym.PACKAGE); }

"." { return new Symbol(Sym.DOT); }

"{" { return new Symbol(Sym.LBRACE); }

"}" { return new Symbol(Sym.RBRACE); }

"@" { return new Symbol(Sym.AT); }

"(" { return new Symbol(Sym.LPAREN); }

")" { return new Symbol(Sym.RPAREN); }

"," { return new Symbol(Sym.COMMA); }

"[" { return new Symbol(Sym.LBRAC); }

"]" { return new Symbol(Sym.RBRAC); }

"⇒" | "=>"  { return new Symbol(Sym.RDARROW); }

"←" | "<-"  { return new Symbol(Sym.LARROW); }

":" { return new Symbol(Sym.COLON); }

";" { return new Symbol(Sym.SEMICOLON); }

"=" { return new Symbol(Sym.EQ); }

"0" | [1-9][0-9]* { return new Symbol(Sym.INT); }

"'" [[:letter:]_][[:letter:]0-9_]* { return new Symbol(Sym.SYMBOL); }

"'" ( [^'\n] | "\\n" | "\\r" | "\\t" ) "'" { return new Symbol(Sym.CHAR); }

[[:letter:]_][[:letter:]0-9_]* |
    "`" [^`\n]* "`" |
    [[]!#%&*+/:<=>?@\\\^|~+-]+ { return new Symbol(Sym.ID); }

\" [^\"\n]* \" { return new Symbol(Sym.STRING); }

\"\"\" { yybegin(MLS); }

<MLS> \" | \"\" | [^\"] {}

<MLS> \"\"\" { yybegin(YYINITIAL); return new Symbol(Sym.MSTRING); }

[^] { throw new RuntimeException(String.format("\n%s\n%d,%d", source, yyline + 1, yycolumn + 1)); }

