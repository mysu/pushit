package co.mysu.pushit.jserver.web.websocket;

import co.mysu.pushit.jserver.web.vo.PushItem;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Collections;
import java.util.List;

@Controller
public class PushWSController {
    @MessageMapping("/msgbus/init")
    @SendTo("/topic/msgbus")
    public List<PushItem> getMsgs(){
        return Collections.emptyList();
    }
}
