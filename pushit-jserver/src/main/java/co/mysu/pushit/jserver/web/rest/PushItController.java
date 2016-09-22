package co.mysu.pushit.jserver.web.rest;

import co.mysu.pushit.jserver.web.rest.vo.RestResponse;
import co.mysu.pushit.jserver.web.vo.PushItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import co.mysu.pushit.jserver.web.rest.vo.Hello;

import java.util.Arrays;
import java.util.Optional;

@RestController()
@RequestMapping("/push")
public class PushItController {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody RestResponse pushMsg(@RequestBody PushItem item){
        PushItem theItem = item;
        if(!item.getMsg().startsWith("http://")||!item.getMsg().startsWith("https://")){
            theItem = new PushItem("http://" +  item.getMsg());
        }

		messagingTemplate.convertAndSend("/topic/msgbus", Arrays.asList(theItem));
		return new RestResponse(true);
	}
}
