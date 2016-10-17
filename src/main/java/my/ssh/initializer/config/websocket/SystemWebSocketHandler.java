package my.ssh.initializer.config.websocket;

import my.ssh.biz.test.hello.controller.WebSocketController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.messaging.SubProtocolWebSocketHandler;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

public class SystemWebSocketHandler extends SubProtocolWebSocketHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemWebSocketHandler.class);

    private static Map<String,WebSocketSession> sessions = new Hashtable<>();

    public SystemWebSocketHandler(MessageChannel clientInboundChannel, SubscribableChannel clientOutboundChannel) {
        super(clientInboundChannel, clientOutboundChannel);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        String username = session.getPrincipal().getName();
        LOGGER.info("用户“"+username+"”上线");
        sessions.put(username,session);
        sendAddUser(username);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        String username = session.getPrincipal().getName();
        LOGGER.info("用户“"+username+"”下线");
        sessions.remove(username);
        sendRemoveUser(username);
        super.afterConnectionClosed(session, closeStatus);
    }

    public static Set<String> getAllUser(){
        return sessions.keySet();
    }
    public static void sendAddUser(String username){
        WebSocketController.getMessagingTemplate().convertAndSend("/topic/addUser",username);
    }
    public static void sendRemoveUser(String username){
        WebSocketController.getMessagingTemplate().convertAndSend("/topic/removeUser",username);
    }
}