package my.ssh.util;

import java.io.File;
import java.net.URL;

/**
 * Created by admin on 2016/8/30.
 */
public class PathUtils {

    /**
     * @return 临时目录
     */
    private static String getTempDir(String dir) {
        return System.getProperty("java.io.tmpdir");
    }

    /**
     * @return 获取WebRoot目录
     */
    public static String getWebRootDir() {
        return WebUtils.getServletContext().getRealPath("");
    }

    /**
     * @return 获取WEB-INFO目录
     */
    public static String getWebInfDir() {
        return getWebRootDir()+"/WEB-INF";
    }

    /**
     * @return 获取classes目录
     */
    public static String getClassesDir() {
        return getClassesDirFile().getFile();
    }

    private static URL getClassesDirFile(){
        return Thread.currentThread().getContextClassLoader().getResource("");
    }
}
