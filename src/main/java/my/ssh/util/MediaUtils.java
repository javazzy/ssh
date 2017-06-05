package my.ssh.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MediaUtils {

    private static final Logger logger = Logger.getLogger(MediaUtils.class);

    /**
     * 提取视频中的一张图片
     *
     * @param videoPath
     * @param imgPath
     * @param time
     * @throws Exception
     */
    public static void cutImg(String videoPath, String imgPath, String time) throws Exception {
        if (StringUtils.isBlank(time)) {
            time = "00:00:03";
        }
        String cmd = "ffmpeg -ss " + time + " -i " + videoPath + " -f image2 -y " + imgPath;
        RuntimeUtils.exec(cmd, new RuntimeUtils.Message() {
            @Override
            public void write(String msg) {
                logger.info(msg);
            }
        });
    }

    /**
     * 提取视频中所有图片
     *
     * @param videoPath
     * @param imgPath
     * @throws Exception
     */
    public static void extractAllImg(String videoPath, String imgPath) throws Exception {
        String cmd = "ffmpeg -i " + videoPath + " " + imgPath;
        RuntimeUtils.exec(cmd, new RuntimeUtils.Message() {
            @Override
            public void write(String msg) {
                logger.info(msg);
            }
        });
    }

    /**
     * 图片合并视频
     *
     * @param imgPath
     * @param videoPath
     * @throws Exception
     */
    public static void mergeImg(String imgPath, String videoPath) throws Exception {
        String cmd = "ffmpeg -f image2 -r 24 -i " + imgPath + " -vcodec h264 " + videoPath;
        RuntimeUtils.exec(cmd, new RuntimeUtils.Message() {
            @Override
            public void write(String msg) {
                logger.info(msg);
            }
        });
    }

    /**
     * 提取声音
     *
     * @param videoPath
     * @param audioPath
     * @throws Exception
     */
    public static void extractAudio(String videoPath, String audioPath) throws Exception {
        String cmd = "ffmpeg -i " + videoPath + " -vn -acodec copy " + audioPath;
        RuntimeUtils.exec(cmd, new RuntimeUtils.Message() {
            @Override
            public void write(String msg) {
                logger.info(msg);
            }
        });
    }

    /**
     * 剪裁视频
     *
     * @param videoPath
     * @param x
     * @param y
     * @param width
     * @param height
     * @throws Exception
     */
    public static void cutVedio(String videoPath, String toVideoPath, String width, String height, String x, String y) throws Exception {
        String cmd = "ffmpeg -i " + videoPath + " -strict -2 -vf crop=" + width + ":" + height + ":" + x + ":" + y + " -vcodec h264 " + toVideoPath;
        RuntimeUtils.exec(cmd, new RuntimeUtils.Message() {
            @Override
            public void write(String msg) {
                logger.info(msg);
            }
        });
    }

    /**
     * 视频格式转换
     *
     * @param videoPath
     * @param toVideoPath
     * @throws Exception
     */
    public static void convertVedio(String videoPath, String toVideoPath) throws Exception {
        convertVedio(videoPath, toVideoPath,null);
    }

    /**
     * 视频格式转换
     *
     * @param videoPath
     * @param toVideoPath
     * * @param message
     * @throws Exception
     */
    public static void convertVedio(String videoPath, String toVideoPath, RuntimeUtils.Message msg) throws Exception {
        // 创建一个List集合来保存转换视频文件的命令
        List<String> convert = new ArrayList<>();
//        convert.add(". /root/.bash_profile\n"); // 添加转换工具路径
        convert.add("ffmpeg"); // 添加转换工具路径
        convert.add("-i"); // 添加参数＂-i＂，该参数指定要转换的文件
        convert.add(videoPath); // 添加要转换格式的视频文件的路径
        convert.add("-q:v 0");//v：视频质量，0为最高
        convert.add("-q:a 0");//a：音频质量，0为最高
        convert.add("-strict -2");//严格的
        convert.add("-y"); // 添加参数＂-y＂，该参数指定将覆盖已存在的文件
        convert.add(toVideoPath);


        String[] envp = new String[]{"PATH=:/usr/local/sbin:/usr/local/bin:/sbin:/bin:/usr/sbin:/usr/bin", "LD_LIBRARY_PATH=/usr/local/ffmpeg_build/lib:/usr/local/lib"};

        RuntimeUtils.exec(StringUtils.join(convert, " "), msg, envp);
    }

    /**
     * 视频分割
     *
     * @param videoPath  原视频地址
     * @param iFrameStep 分割视频帧组个数
     * @throws Exception
     */
    public static void splitVideo(final String videoPath, String outputFolder, final int iFrameStep) throws Exception {
        File inputFile = new File(videoPath);
        new File(outputFolder).mkdirs();

        final String outPathFormat = outputFolder + File.separator + inputFile.getName().substring(0, inputFile.getName().lastIndexOf(".")) + "%06d" + inputFile.getName().substring(inputFile.getName().lastIndexOf("."));

        final int[] iFrameIndex = {-1};
        final int[] videoIndex = {0};
        final boolean[] skipFlag = {false};
        final String[] times = new String[2];

        VideoFrames videoFrames = new VideoFrames(videoPath, new VideoFrames.FrameListener() {
            @Override
            public void call(Map<String, String> frame) throws Exception {
                //视频帧所在的时间
                String time = frame.get("pkt_dts_time");
                //帧类型
                String type = frame.get("pict_type");

                //跳过一组帧
                if (skipFlag[0]) {
                    if ("I".equals(type)) {
                        //记录关键帧的时间为视频开始时间
                        times[0] = time;
                        skipFlag[0] = false;
                    }
                    return;
                }

                if ("I".equals(type)) {
                    iFrameIndex[0]++;
                    if (iFrameIndex[0] == 0) {//跳过前几组帧
                        skipFlag[0] = true;
                    } else if (iFrameIndex[0] % iFrameStep == 0) {//截取视频
                        String outFilePath = String.format(outPathFormat, videoIndex[0]++);
                        double splitTime = Float.parseFloat(times[1]) - Float.parseFloat(times[0]);
                        splitVideo(videoPath, outFilePath, times[0], String.valueOf(splitTime));
                        skipFlag[0] = true;
                    }
                } else {
                    times[1] = time;
                }
            }
        });
    }

    public static void splitVideo(String videoPath, String outFilePath, String startTime, String splitTime) throws Exception {
        File outFile = new File(outFilePath);
        if (!outFile.getParentFile().exists()) {
            outFile.getParentFile().mkdirs();
        }

        //"ffmpeg -i input.mp4 -ss **START_TIME** -t **STOP_TIME** -acodec copy -vcodec copy output.mp4";
        List<String> commond = new ArrayList<>();
        commond.add("ffmpeg -y");

        commond.add("-ss");
        commond.add(startTime);

        commond.add("-i");
        commond.add(videoPath);

        commond.add("-acodec copy -vcodec copy");

        commond.add("-t");
        commond.add(splitTime);

        commond.add(outFilePath);

        RuntimeUtils.exec(StringUtils.join(commond, " "));
    }

    /**
     * 合并视频
     *
     * @param outFilePath
     * @param fileFolder
     * @return
     */
    public static void concatVideo(String outFilePath, File fileFolder) throws Exception {
        File outFile = new File(outFilePath);
        if (!outFile.getParentFile().exists()) {
            outFile.getParentFile().mkdirs();
        }

        FileWriter fw = new FileWriter(outFilePath + ".txt");
        for (String fileName : fileFolder.list()) {
            fw.write("file '");
            fw.write(fileName);
            fw.write("'");
            fw.write("\n");
        }
        fw.close();

        //"ffmpeg -i input.mp4 -ss **START_TIME** -t **STOP_TIME** -acodec copy -vcodec copy output.mp4";
        StringBuilder c = new StringBuilder();
        c.append("ffmpeg -f concat -i ");
        c.append(outFilePath + ".txt");
        c.append(" -c copy ");
        c.append(outFilePath);

//        System.out.println("合并：" + c.toString());
        RuntimeUtils.exec(c.toString(), null, null, fileFolder);
    }

    public static String duration2time(String durationStr) {

        int duration = Double.valueOf(Math.ceil(Double.parseDouble(durationStr)*1000)).intValue();
        int hour = duration/(1000*60*60);
        int minute = duration%(1000*60*60)/(1000*60);
        int second = duration%(1000*60)/1000;
        int millisecond = duration%1000;

        return String.format("%02d:%02d:%02d.%03d",hour,minute,second,millisecond);
    }
}
