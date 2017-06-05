package my.ssh.util;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by admin on 2016/8/17.
 */
public class VideoFrames {

    final BlockingQueue<Map<String,String>> frameQueue = new LinkedBlockingQueue<>();

    private static final Logger logger = Logger.getLogger(VideoFrames.class);
//    private final List<Map<String,String>> framesInfo = new ArrayList<Map<String, String>>();

    public VideoFrames(String filePath,final FrameListener listener) throws Exception {

        long start = System.currentTimeMillis();

        StringBuilder command = new StringBuilder();
        command.append("ffprobe -show_frames -select_streams v -i ").append(filePath);

        final List isEndList = new ArrayList(1);
        if(null != listener){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (isEndList.size() == 0 || !frameQueue.isEmpty()) {
                            Map<String,String> frame = frameQueue.take();
                            if(frame.containsKey("pict_type"))
                                listener.call(frame);
                        }
                    } catch (InterruptedException e) {
                        logger.warn(e.getMessage(),e);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        RuntimeUtils.exec(command.toString(), new RuntimeUtils.Message() {
            Map<String,String> frame;
            Map<String,String> lastFrame;

            public void write(String msg) {
                if(logger.isDebugEnabled())
                    logger.debug(msg);

                if("[FRAME]".equals(msg)){
                    frame = new HashMap<>();
                    return;
                }
                if("[/FRAME]".equals(msg)){
                    if("video".equals(frame.get("media_type"))) {
                        if("I".equals(frame.get("pict_type"))){
                            if(null != lastFrame){
//                                framesInfo.add(lastFrame);
                                frameQueue.add(lastFrame);
                            }
//                            framesInfo.add(frame);
                            frameQueue.add(frame);
                        }else{
                            lastFrame = frame;
                        }
                    }
                    frame = null;
                    return;
                }
                if(null != frame){
                    if(msg.startsWith("media_type") || msg.startsWith("pkt_dts_time") || msg.startsWith("pict_type")) {
                        String[] kv = msg.split("=");
                        frame.put(kv[0], kv[1]);
                    }
                }
            }

        });
        isEndList.add("");
        frameQueue.add(new HashMap());
//        long end = System.currentTimeMillis();
//        out.println("获取关键帧用时："+((end-start)/1000)+"秒");
    }

//    public List<Map<String, String>> getFramesInfo() {
//        return framesInfo;
//    }
    public interface FrameListener {
        void call(Map<String, String> frame) throws Exception;
    }
}
