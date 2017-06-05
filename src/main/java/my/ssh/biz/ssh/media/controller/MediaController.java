package my.ssh.biz.ssh.media.controller;

/**
 * Created by zzy on 2016/4/6 0006.
 */

import my.ssh.biz.common.controller.BaseController;
import my.ssh.biz.common.service.BaseService;
import my.ssh.util.PathUtils;
import my.ssh.util.WebUtils;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import java.io.File;
import java.net.URLEncoder;

@Controller
@RequestMapping("/")
public class MediaController extends BaseController{

    private final Logger logger = Logger.getLogger(this.getClass());

//    @RequestMapping("/play/{fileName}")
//    @ResponseBody
//    public ResponseEntity<byte[]> play1(@PathVariable("fileName") String fileName) {
//        try {
//            String url = PathUtils.getWebRootDir() + "upload/";
//
//            File file = new File(url + fileName);
//            HttpHeaders headers = new HttpHeaders();
//            fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");//为了解决中文名称乱码问题
//            headers.setContentDispositionFormData("attachment", fileName);
//            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//            return new ResponseEntity(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
//        } catch (Exception e) {
//            catchException(e);
//        }
//        return null;
//    }


    @RequestMapping("/play/{fileName:.*}")
    public void play(@PathVariable("fileName") String fileName) {
        try {
            String url = PathUtils.getWebRootDir() + "upload/";
            File file = new File(url + fileName);
            WebUtils.getResponse().setContentType("multipart/form-data");
            WebUtils.getResponse().setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName));
            ServletOutputStream out = WebUtils.getResponse().getOutputStream();
            FileUtils.copyFile(file,out);
            out.flush();
            out.close();
        } catch (Exception e) {
            catchException(e);
        }
    }

    @Override
    public BaseService getService() {
        return null;
    }
}


