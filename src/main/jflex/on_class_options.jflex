
/* package declaration required for some reason */
package wanghg.x;

import wanghg.fake.Readable;
import wanghg.fake.Writable;
import wanghg.fake.Baseball;

%%

%int

/* Each JFlex directive must sit at the beginning of a line and starts with the % character */

%public

/* not demoed: %final, %abstract */

/* related things: -d <directory>, Yylex.java */
%class On_Class_Options

%extends Baseball

%implements Readable, Writable

%ctorarg String s   // conflicts with the %standalone and %debug directives
%ctorarg int n

%init{
    value = "<" + s + "-" + n + ">";
%init}

/* not demoed: %initthrow */

%{
    private String value = "hello";

    public String read() {
        return value;
    }

    public boolean write(String str) {
        return false;
    }
%}

/* not demoed: %scanerror, for bug exception, default is java.lang.Error */

/* not demoed: %buffer, scanner buffer size, in bytes, default is 16384 */

/* not demoed: %include */

%apiprivate // totally encapsulation of methods and fields, except constructor and next_token

%%

[^] {}
