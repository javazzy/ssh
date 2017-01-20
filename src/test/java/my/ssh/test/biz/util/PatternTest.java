package my.ssh.test.biz.util;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zzy on 16/9/25.
 */
public class PatternTest {

    @Test
    public void testPattern(){
        Pattern p = Pattern.compile("[A-Z][a-z]+");
        Matcher matcher = p.matcher("DicStatus");
        if(matcher.find()) {
            String modualName = matcher.group(0).toLowerCase();
            System.out.println(modualName);
        }
    }

}
