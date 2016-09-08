package my.ssh.util;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by zzy on 2015/11/27.
 */
public class RuntimeUtils {

    private static final Logger logger = Logger.getLogger(RuntimeUtils.class);

    /**
     * 从数据库导出项目数据
     *
     * @return
     */
    public static void exec(String cmds) {
        exec(cmds, null);
    }

    /**
     * 从数据库导出项目数据
     *
     * @return
     */
    public static int exec(String cmds, final Message msg) {

        final BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        final List isEndList = new ArrayList(1);

        Process process = null;
        try {
            logger.info("执行系统命令:"+cmds);
            process = Runtime.getRuntime().exec(cmds);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        boolean shouldClose = false;

        try {
            if(null != msg && msg.isAsync()){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            while (isEndList.size() == 0 || !queue.isEmpty()) {
                                msg.write(queue.take());
                            }
                            msg.end();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            InputStreamReader isr = new InputStreamReader(process.getInputStream());
            final BufferedReader br = new BufferedReader(isr);

            while (true) {
                String line = br.readLine();
                if(null != msg) {
                    if (null != line) {
                        if(msg.isAsync()) {
                            queue.put(line);
                        }else{
                            msg.write(line);
                        }
                    } else {
                        if(msg.isAsync()) {
                            isEndList.add(1);
                            queue.put("");
                        }
                    }
                }else{
                    if(null != line) System.out.print(line);
                }

                if (null == line) break;
            }

        } catch (IOException ioe) {
            shouldClose = true;
        } catch (InterruptedException e) {
            shouldClose = true;
        }
        if (shouldClose)
            process.destroy();
        try {
            return process.waitFor();
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        return 1;
    }

    public interface Message {
        void write(String msg);
        void end();
        boolean isAsync();
    }

    public static class DefaultMessage implements Message{

        boolean isAsync = false;
        public DefaultMessage(){}
        public DefaultMessage(boolean isAsync){
            this.isAsync = isAsync;
        }

        @Override
        public void write(String msg) {

        }

        @Override
        public void end() {

        }

        @Override
        public boolean isAsync() {
            return isAsync;
        }
    }

}
