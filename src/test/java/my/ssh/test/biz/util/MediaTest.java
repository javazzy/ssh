package my.ssh.test.biz.util;

import my.ssh.util.MediaUtils;
import org.junit.Test;

/**
 * Created by root on 2017/4/17 0017.
 */
public class MediaTest {

    @Test
    public void media() throws Exception {
        String fromVideo = "D:\\Home\\Desktop\\ghsy1.avi";
        String toVideo = "E:\\IdeaProjects\\ssh\\build\\classes\\artifacts\\ssh\\exploded\\ssh-1.0.war\\upload\\ghsy.mp4";
        MediaUtils.convertVedio(fromVideo,toVideo);
    }

}
