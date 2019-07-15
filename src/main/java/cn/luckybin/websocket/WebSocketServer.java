package cn.luckybin.websocket;

import cn.luckybin.file_system.FileSystem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@ServerEndpoint("/websocket/{username}/{fileName}")
@Component
@Slf4j
public class WebSocketServer {


    //初始用户数量,如果不是static则无法共享(每新加入一个用户,则会生成一个该类的实例)
    private static Map<String, Session> userSessionMap = new ConcurrentHashMap<>(5);

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public static void onOpen(
            Session session,
            @PathParam("username") String username,
            @PathParam("fileName") String fileName) {
        userSessionMap.put(username, session);
        log.info("用户:[{}]已上线,文件名:[{}]", username, fileName);
        //获取当前用户指定的文件的内容
        boolean containsRes = FileSystem.FileMap.containsKey(fileName);
        String content = "";
        if (containsRes) {
            content = FileSystem.FileMap.get(fileName);
        } else {
            //读取文件内容,并放入容器中
            try {
                FileSystem.getContentFromFile(fileName);
                content = FileSystem.FileMap.get(fileName);
            } catch (FileNotFoundException e) {
                content = "文件不存在";
            }
        }
        if (null == content) {
            content = "文件不存在";
        }

        try {
            sendMessage(session, content);
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public static void onClose(@PathParam("username") String username, Session session) {
        userSessionMap.remove(username);
        log.info("用户:[{}]已下线", username);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public static void onMessage(@PathParam("username") String username, @PathParam("fileName") String fileName, String message) {
        FileSystem.FileMap.put(fileName, message);
//        log.info("[{}]:[{}]", username, message);
        //群发消息
        send2AllUser(message);
    }

    /**
     * @param error
     */
    @OnError
    public static void onError(@PathParam("username") String username, Throwable error) {
        log.error("用户:[{}]的连接发生错误", username);
        userSessionMap.remove(username);
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public static void sendMessage(Session session, String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }


    /**
     * 群发自定义消息
     */
    public static void send2AllUser(String message) {
//        log.info("向所有用户推送消息:[{}]", message);
        for (Map.Entry<String, Session> sessionEntry :
                userSessionMap.entrySet()) {
            try {
                sessionEntry.getValue().getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
                userSessionMap.remove(sessionEntry.getKey());
            }
        }
    }
}

