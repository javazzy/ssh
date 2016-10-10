package my.ssh.biz.test.hello.controller;

/**
 * Created by zzy on 2016/4/6 0006.
 */

import com.alibaba.fastjson.JSON;
import my.ssh.biz.common.entity.Result;
import my.ssh.initializer.config.websocket.SystemWebSocketHandler;
import my.ssh.util.PathUtils;
import my.ssh.util.WebUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

@Controller
@RequestMapping("/")
public class WebSocketController {

    private final Logger logger = Logger.getLogger(this.getClass());
    private static SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        WebSocketController.messagingTemplate = messagingTemplate;
    }

    public static SimpMessagingTemplate getMessagingTemplate() {
        return messagingTemplate;
    }

    @MessageMapping("/send")
    public void message(String content,
                        @Header("from") String from,
                        @Header("to") String to,
                        @Header(name = "name", required = false) String name,
                        @Header(name = "time", required = false) Long time,
                        @Header(name = "type", required = false) String type,
                        @Headers Map<String, Object> _headers) throws Exception {
        time = Calendar.getInstance().getTime().getTime();

        Map<String, Object> headers = new HashMap<>(_headers);
        headers.put("from", from);
        headers.put("to", to);
        headers.put("time", time);

        if (StringUtils.isNotBlank(to)) {
            messagingTemplate.convertAndSendToUser(from, "/message", content, headers);
            messagingTemplate.convertAndSendToUser(to, "/message", content, headers);
        } else {
            messagingTemplate.convertAndSend("/topic/notice", content, headers);
        }
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

    @RequestMapping("/upload")
    @ResponseBody
    public void upload(MultipartHttpServletRequest request) throws Exception {
        try {
            List<MultipartFile> files = request.getFiles("file");

            long time = Calendar.getInstance().getTime().getTime();

            String from = request.getParameterMap().get("form")[0];
            String to = request.getParameterMap().get("to")[0];

            Map<String, Object> headers = new HashMap<>();
            headers.put("from", from);
            headers.put("to", to);
            headers.put("time", time);
            for (MultipartFile file : files) {
                String url = PathUtils.getWebRootDir() + "upload";
                File folder = new File(url);
                if (!folder.exists()) {
                    folder.mkdirs();
                }

                File saveFile = new File(url + "/" + file.getOriginalFilename());
                FileUtils.copyInputStreamToFile(file.getInputStream(), saveFile);
                headers.put("name", file.getOriginalFilename());
                if (StringUtils.isNotBlank(to)) {
                    messagingTemplate.convertAndSendToUser(from, "/files", JSON.toJSONString(headers));
                    messagingTemplate.convertAndSendToUser(to, "/files", JSON.toJSONString(headers));
                } else {
                    messagingTemplate.convertAndSend("/topic/files", JSON.toJSONString(headers));
                }
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
            String url = PathUtils.getWebRootDir() + "upload";

            File file = new File(url + "/" + fileName);
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
}