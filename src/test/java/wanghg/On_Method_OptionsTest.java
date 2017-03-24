package wanghg;

import org.junit.Test;
import wanghg.x.SimpleCharByChar;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static org.junit.Assert.*;

/**
 * Created by wanghg on 24/3/2017.
 */
public class On_Method_OptionsTest {
    @Test
    public void doTest() throws IOException {
        Reader reader = new StringReader("I love 🍎!!");
        //
        SimpleCharByChar scanner = new SimpleCharByChar(reader);
        //
        assertNotNull(scanner);
        //
        assertEquals(7, scanner.yylex()); // 'I'
        assertEquals(7, scanner.yylex()); // ' '
        assertEquals(7, scanner.yylex()); // 'l'
        assertEquals(7, scanner.yylex()); // 'o'
        assertEquals(7, scanner.yylex()); // 'v'
        assertEquals(7, scanner.yylex()); // 'e'
        assertEquals(7, scanner.yylex()); // ' '
        assertEquals(7, scanner.yylex()); // '🍎'
        assertEquals(7, scanner.yylex()); // '!'
        assertEquals(7, scanner.yylex()); // '!'
        assertEquals(SimpleCharByChar.YYEOF, scanner.yylex());
        //
        System.out.println("SimpleCharByChar Okay!");
    }
}
