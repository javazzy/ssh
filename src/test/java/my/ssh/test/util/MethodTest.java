package my.ssh.test.util;

import my.ssh.biz.ssh.sys.entity.SysUser;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * Created by root on 2016/9/25 0025.
 */
public class MethodTest {
    @Test
    public void testMethod() throws NoSuchMethodException {
        Class c = SysUser.class;
        Method m = c.getDeclaredMethod("setId",Integer.class);
        System.out.println(m);
    }
}
