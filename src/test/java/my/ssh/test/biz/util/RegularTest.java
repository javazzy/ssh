package my.ssh.test.biz.util;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin on 2016/9/1.
 */
public class RegularTest {

    @Test
    public void testRegular(){
        String s = "AK47的重量为173kg";
        Pattern p = Pattern.compile("重量[^\\d]*(\\d+)(kg|千克)");
        Matcher m = p.matcher(s);
        if(m.find()){
            System.out.println(m.group(1));
        }
    }

}
