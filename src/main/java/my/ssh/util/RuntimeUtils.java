package my.ssh.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by zzy on 2015/11/27.
 */
public class RuntimeUtils {

    private static final Logger logger = Logger.getLogger(RuntimeUtils.class);

    /**
     * 执行系统命令
     *
     * @return
     */
    public static void exec(String cmds) throws Exception {
        exec(cmds, null, null, null);
    }
    /**
     * 执行系统命令
     *
     * @return
     */
    public static int exec(String cmds, final Message msg) throws Exception {
        return exec(cmds,msg,null,null);
    }
    /**
     * 执行系统命令
     *
     * @return
     */
    public static int exec(String cmds, final Message msg, String[] envp) throws Exception {
        return exec(cmds,msg,envp,null);
    }
    /**
     * 执行系统命令
     *
     * @return
     */
    public static int exec(String cmds, final Message msg, String[] envp, File dir) throws Exception {

        final LinkedBlockingQueue<Character> queue = new LinkedBlockingQueue<>();
        final StringBuilder out = new StringBuilder();
        final List isEndList = new ArrayList(1);

//        logger.info("执行系统命令:"+cmds);
        System.out.println(cmds);
        Process process = Runtime.getRuntime().exec(cmds,envp,dir);

        if(null != msg){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (isEndList.size() == 0 || !queue.isEmpty()) {
                            out.append(queue.take());
                            if(out.indexOf("\n")>-1 || out.indexOf("\r")>-1) {
                                if(StringUtils.trim(out.toString()).length()>0)
                                    msg.write(StringUtils.trim(out.toString()));
                                out.replace(0,out.length(),"");
                            }
                        }
                    } catch (InterruptedException e) {
                        logger.warn(e.getMessage(),e);
                    }
                }
            }).start();
        }

        int exitValue = -1; // returned to caller when p is finished
        InputStream in = null;
        InputStream err = null;
        try {
            in = process.getInputStream();
            err = process.getErrorStream();
            boolean finished = false; // Set to true when p is finished
            while (!finished) {
                try {
                    while (in.available() > 0) {
                        // Print the output of our system call
                        Character c = new Character((char) in.read());
                        queue.add(c);
                    }
                    while (err.available() > 0) {
                        // Print the output of our system call
                        Character c = new Character((char) err.read());
                        queue.add(c);
                    }
                    // Ask the process for its exitValue. If the process
                    // is not finished, an IllegalThreadStateException
                    // is thrown. If it is finished, we fall through and
                    // the variable finished is set to true.
                    exitValue = process.exitValue();
                    finished = true;
                    isEndList.add("");
                    queue.add('\n');
                }catch (Exception e){
//                    Thread.sleep(50);
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if(null != in) in.close();
            if(null != err) err.close();
            process.destroy();
        }

        return exitValue;
    }

    public interface Message {
        void write(String msg);
    }

}
