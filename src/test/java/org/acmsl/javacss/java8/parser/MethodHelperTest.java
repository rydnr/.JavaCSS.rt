package org.acmsl.javacss.java8.parser;

import java.text.MessageFormat;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class MethodHelperTest {
    @Test public void can_count_the_number_of_methods()
        throws Exception {

        for (int i = 0; i < 10; i++) {
            countMethodTest(i);
        }
    }
    
    protected void countMethodTest(int methodCount)
        throws Exception {
        STString inputTemplate =
            new STString(
                "public class MyClass {\n"
              + "<methods>\n"
                + "}\n");

        STString methodTemplate =
            "    public int method<counter>(String value) { return <counter>; }\n";

        StringBuilder methods = new StringBuilder();
        
        for (int i = 0; i < methodCount; i++) {
            String method = new MessageFormat(methodTemplate).format(new Object[] { Integer.valueOf(i) });
            methods.append(method);
        }

        String input = new MessageFormat(inputTemplate).format(new Object[] { methods.toString() });
        
        Assert.assertEquals(methodCount, new MethodHelper(input).countMethods());
    }
}
