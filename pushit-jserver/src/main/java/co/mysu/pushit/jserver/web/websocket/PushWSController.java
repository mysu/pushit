package co.mysu.pushit.jserver.web.websocket;

import java.util.Arrays;
import java.util.List;

import co.mysu.pushit.jserver.web.rest.vo.PushIdentifier;
import co.mysu.pushit.jserver.web.vo.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import co.mysu.pushit.jserver.web.vo.PushItem;
import co.mysu.pushit.jserver.web.vo.WSocketMessage;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PushWSController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @MessageMapping("/msgbus/init/{id}")
    public void connect(@Payload PushIdentifier payload) {
        messagingTemplate.convertAndSend("/topic/msgbus/" + payload.getPushId(),
                                         new Connection(true));
    }
}
