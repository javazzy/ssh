package my.ssh.biz.test.hello.controller;

/**
 * Created by zzy on 2016/4/6 0006.
 */

import my.ssh.initializer.config.websocket.SystemWebSocketHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/")
public class WebSocketController {

    private final Logger logger = Logger.getLogger(this.getClass());
    private static SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        WebSocketController.messagingTemplate = messagingTemplate;
    }

    public static SimpMessagingTemplate getMessagingTemplate(){
        return messagingTemplate;
    }

    @MessageMapping("/send")
    public void charts(String message, @Header("from") String from, @Header("to") String to, @Header("time") Long time, @Headers Map<String,Object> _headers) throws Exception {
        Map<String,Object> headers = new HashMap<>(_headers);
        headers.put("from",from);
        headers.put("to",to);

        if(StringUtils.isNotBlank(to)) {
            messagingTemplate.convertAndSendToUser(from, "/message", message, headers);
            messagingTemplate.convertAndSendToUser(to, "/message", message, headers);
        }else{
            messagingTemplate.convertAndSend("/topic/notice", message, headers);
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
}