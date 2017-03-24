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
        assertEquals(7, scanner.next()); // 'I'
        assertEquals(7, scanner.next()); // ' '
        assertEquals(7, scanner.next()); // 'l'
        assertEquals(7, scanner.next()); // 'o'
        assertEquals(7, scanner.next()); // 'v'
        assertEquals(7, scanner.next()); // 'e'
        assertEquals(7, scanner.next()); // ' '
        assertEquals(7, scanner.next()); // '🍎'
        assertEquals(7, scanner.next()); // '!'
        assertEquals(7, scanner.next()); // '!'
        assertEquals(SimpleCharByChar.YYEOF, scanner.next());
        //
        System.out.println("SimpleCharByChar Okay!");
    }
}
