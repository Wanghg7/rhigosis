package wanghg;

import org.junit.Test;
import wanghg.fake.Baseball;
import wanghg.x.On_Class_Options;

import static org.junit.Assert.*;

/**
 * Created by wanghg on 24/3/2017.
 */
public class On_Class_OptionsTest {
    @Test
    public void doTest() {
        //
        On_Class_Options fakeLexer = new On_Class_Options(null, "love", 0);
        //
        assertNotNull(fakeLexer);
        //
        assertEquals("<love-0>", fakeLexer.read());
        assertFalse(fakeLexer.write("love"));
        assertEquals(new Baseball().hi(), fakeLexer.hi());
        //
        System.out.println("On_Class_OptionsTest Okay!");
    }
}
