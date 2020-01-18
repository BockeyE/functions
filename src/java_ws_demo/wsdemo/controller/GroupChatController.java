package java_ws_demo.wsdemo.controller;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


@Component
@ServerEndpoint("/groupChat/{sid}/{username}")
public class GroupChatController {

    // 保存 组id->组成员 的映射关系
    private static ConcurrentHashMap<String, List<Session>> groupMemberInfoMap = new ConcurrentHashMap<>();

    // 收到消息调用的方法，群成员发送消息
    @OnMessage
    public void onMessage(@PathParam("sid") String sid,
                          @PathParam("username") String username, String message) {
        List<Session> sessionList = groupMemberInfoMap.get(sid);
        // 先一个群组内的成员发送消息
        sessionList.forEach(item -> {
            try {
                String text = username + ": " + message;
                item.getBasicRemote().sendText(text);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    // 建立连接调用的方法，群成员加入
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        List<Session> sessionList = groupMemberInfoMap.get(sid);
        if (sessionList == null) {
            sessionList = new ArrayList<>();
            groupMemberInfoMap.put(sid,sessionList);
        }
        sessionList.add(session);

    }

    // 关闭连接调用的方法，群成员退出
    @OnClose
    public void onClose(Session session, @PathParam("sid") String sid) {
        List<Session> sessionList = groupMemberInfoMap.get(sid);
        sessionList.remove(session);
    }

    // 传输消息错误调用的方法
    @OnError
    public void OnError(Throwable error) {
    }
}
