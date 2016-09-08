package my.ssh.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

public class VideoUtils {

	private static final Logger logger = Logger.getLogger(VideoUtils.class);
	
	private static final String TEMP_PATH = System
			.getProperty("java.io.tmpdir");

	public static byte[] toVideoCapture(InputStream vis, String fileName) throws IOException {
		logger.info("开始截图，文件名为：" + fileName);
		String videoPath = TEMP_PATH + fileName;
		String picturePath = TEMP_PATH + fileName.substring(0, fileName.lastIndexOf(".")) + ".jpg";
		
		File videoFile = null;
		File pictureFile = null;
		
		try {
			videoFile = new File(videoPath);

			if(logger.isDebugEnabled())
				logger.debug("保存视频到本地："+videoPath);
			FileUtils.copyInputStreamToFile(vis, videoFile);
			
			if(logger.isDebugEnabled())
				logger.debug("视频截图到文件："+picturePath);
			execCut(videoPath, picturePath);
			if(logger.isDebugEnabled())
				logger.debug("视频截图成功！");
			pictureFile = new File(picturePath);
			byte[] pictureBytes = FileUtils.readFileToByteArray(pictureFile);
			return pictureBytes;
		} catch (IOException e) {
			throw e;
		} finally {
			vis.close();
			if(null != videoFile && videoFile.exists())
				videoFile.delete();
			if(null != pictureFile && pictureFile.exists())
				pictureFile.delete();
		}
	}

	private static void execCut(String videoPath, String screenshotPath) {
		if(logger.isDebugEnabled())
			logger.debug("ffmpeg -ss 00:00:03 -i " + videoPath + " -f image2 -y " + screenshotPath);
		RuntimeUtils.exec("ffmpeg -ss 00:00:03 -i " + videoPath + " -f image2 -y " + screenshotPath);
	}
	
//	public static void main(String[] args) {
//		String videoPath = "C:/Users/admin/Desktop/streams/test.flv";
//		File videoFile = new File(videoPath);
//		try {
//			FileInputStream fis = new FileInputStream(videoFile);
//			VideoUtils.toVideoCapture(fis, videoFile.getName());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

}
