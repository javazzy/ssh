package my.ssh.biz.test.hello.controller;

/**
 * Created by zzy on 2016/4/6 0006.
 */

import com.alibaba.fastjson.JSON;
import my.ssh.biz.common.entity.Result;
import my.ssh.biz.test.hello.entity.Message;
import my.ssh.initializer.config.websocket.SystemWebSocketHandler;
import my.ssh.util.BeanUtils;
import my.ssh.util.PathUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.util.*;

@Controller
@RequestMapping("/")
public class WebSocketController {

    private final Logger logger = Logger.getLogger(this.getClass());
    private static SimpMessagingTemplate messagingTemplate;
    private final int SPLIT_SIZE = 10000;

    @Autowired
    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        WebSocketController.messagingTemplate = messagingTemplate;
    }

    public static SimpMessagingTemplate getMessagingTemplate() {
        return messagingTemplate;
    }

    @RequestMapping("/users")
    @ResponseBody
    public Set<String> users() throws Exception {
        try {
            return SystemWebSocketHandler.getAllUser();
        } catch (Exception e) {
            if (null != e.getCause())
                logger.error(e.getCause().getMessage(), e.getCause());
            else
                logger.error(e.getMessage(), e);
        }
        return null;
    }

    @RequestMapping("/message")
    @ResponseBody
    public Object message(Message message) throws Exception {
        return this.sendMessage(message);
    }

    @RequestMapping("/upload")
    @ResponseBody
    public void upload(MultipartHttpServletRequest request,Message message) throws Exception {
        try {
            for (MultipartFile file : request.getFiles("file")) {
                String url = PathUtils.getWebRootDir() + "upload";
                File folder = new File(url);
                if (!folder.exists()) {
                    folder.mkdirs();
                }

                File saveFile = new File(url + "/" + file.getOriginalFilename());
                FileUtils.copyInputStreamToFile(file.getInputStream(), saveFile);
                message.setContent(file.getOriginalFilename());
                sendMessage(message);
            }
        } catch (Exception e) {
            if (null != e.getCause())
                logger.error(e.getCause().getMessage(), e.getCause());
            else
                logger.error(e.getMessage(), e);
        }
    }

    @RequestMapping("/download")
    @ResponseBody
    public ResponseEntity<byte[]> download(String fileName) throws Exception {
        try {
            String url = PathUtils.getWebRootDir() + "upload/";

            File file = new File(url + fileName);
            HttpHeaders headers = new HttpHeaders();
            fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");//为了解决中文名称乱码问题
            headers.setContentDispositionFormData("attachment", fileName);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
        } catch (Exception e) {
            if (null != e.getCause())
                logger.error(e.getCause().getMessage(), e.getCause());
            else
                logger.error(e.getMessage(), e);
        }
        return null;
    }

    public Object sendMessage(Message message) {
        try {
            long time = Calendar.getInstance().getTime().getTime();
            int packageIndex = 0;
            int contentLength = message.getContent().length();

            Map headers = new HashMap();
            headers.put("from", message.getFrom());
            headers.put("to", message.getTo());
            headers.put("type", message.getType());
            headers.put("time", time);
            headers.put("taskId", message.getFrom() + "-" + message.getTo() + "-" + time);
            headers.put("packageTotal", contentLength / SPLIT_SIZE + (contentLength % SPLIT_SIZE == 0 ? 0 : 1));

            StringBuilder allContent = new StringBuilder(message.getContent());
            for (int i = 0; i < contentLength; i += SPLIT_SIZE) {
                headers.put("packageIndex", packageIndex++);

                String subContent;
                if (contentLength - i < SPLIT_SIZE) {
                    subContent = allContent.substring(i, contentLength);
                }else{
                    subContent = allContent.substring(i, i + SPLIT_SIZE);
                }

                if (StringUtils.isNotBlank(message.getTo())) {
                    messagingTemplate.convertAndSendToUser(message.getFrom(), "/message", subContent, headers);
                    messagingTemplate.convertAndSendToUser(message.getTo(), "/message", subContent, headers);
                } else {
                    messagingTemplate.convertAndSend("/topic/notice", subContent, headers);
                }

                //拉长发送间隔，否则将失去websocket链接
                Thread.sleep(50);
            }
            return new Result(true);
        } catch (Exception e) {
            if (null != e.getCause()) {
                logger.error(e.getCause().getMessage(), e.getCause());
                return new Result(false, e.getCause().getMessage());
            } else {
                logger.error(e.getMessage(), e);
                return new Result(false, e.getMessage());
            }
        }
    }

}


