package co.mysu.pushit.jserver.web.websocket;

import java.util.Arrays;
import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import co.mysu.pushit.jserver.web.vo.PushItem;
import co.mysu.pushit.jserver.web.vo.WSocketMessage;

@Controller
public class PushWSController {
    @MessageMapping("/msgbus/init")
    @SendTo("/topic/msgbus")
    public List<WSocketMessage> getMsgs(){
        return Arrays.asList(new PushItem("Hello, " +  System.currentTimeMillis()));
    }
}
