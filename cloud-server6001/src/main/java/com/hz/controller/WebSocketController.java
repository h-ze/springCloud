package com.hz.controller;

import com.hz.test.MyClient;
import com.common.entity.ResponseMessageWithoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.ContainerProvider;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;

import static com.common.entity.ResponseMessageWithoutException.errorResult;
import static com.common.entity.ResponseMessageWithoutException.successResult;


/**
 * 测试websocket连接
 */
@RestController
@RequestMapping("webSocket")
@Slf4j
public class WebSocketController {

    /*@Autowired
    SimpMessagingTemplate simpMessagingTemplate;*/

    @Value("${server.port}")
    private String serverPort;

    @PostMapping("/sendMessage")
    public ResponseMessageWithoutException<String> sendMessage(@RequestParam String message, @RequestParam String username, @RequestParam String token){
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            MyClient client = new MyClient();
            container.connectToServer(client, new URI("ws://localhost:"+serverPort+"/springboot/websocket/"+username+"?token="+token));
            client.send("客户端发送消息:" + message);
            return successResult(100000,"发送成功","测试websocket成功");
        }catch (Exception e){
            e.printStackTrace();
            return errorResult(100000,"测试websocket失败");
        }
    }

    /**
     * 主动给websocket发送消息
     * @param message 发送的消息
     * @param token 用户的token
     * @return
     * @throws IOException
     */
//    @PostMapping("/push")
//    public ResponseEntity<String> pushToWeb(@RequestParam String message, @RequestParam String token) throws IOException {
//        WebSocket.sendInfo(message,"zhangsan");
//        return ResponseEntity.ok("MSG SEND SUCCESS");
//    }

}
