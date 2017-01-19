package my.ssh.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by root on 2017/1/19 0019.
 */
public class PropertiesUtils {

    public static Properties loadProperties(String propFile) throws IOException {
        return loadProperties(propFile,null);
    }

    public static Properties loadProperties(String propFile,String defaultProFile) throws IOException {
        Properties jdbcProp = new Properties();
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(propFile);
        if(null == is){
            is = new FileInputStream(propFile);
        }
        if(null == is && null != defaultProFile){
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(defaultProFile);
        }
        jdbcProp.load(is);
        return jdbcProp;
    }
}
