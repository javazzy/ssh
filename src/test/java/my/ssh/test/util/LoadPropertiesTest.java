package my.ssh.test.util;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by zzy on 16/3/31.
 */
public class LoadPropertiesTest {

    @Test
    public void testLoadProperties(){
        try {
            Properties p = new Properties();

            p.load(new FileInputStream(Thread.currentThread().getContextClassLoader().getResource("").getFile()+"jdbc\\jdbc-default.properties"));
            System.out.println(p.getProperty("generator.table"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testLoadProperties1(){
        try {
            Properties p = new Properties();

            p.load(this.getClass().getResourceAsStream("/jdbc/jdbc-default.properties"));
            System.out.println(p.getProperty("generator.table"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
