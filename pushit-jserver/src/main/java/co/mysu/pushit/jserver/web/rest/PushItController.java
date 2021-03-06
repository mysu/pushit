package co.mysu.pushit.jserver.web.rest;

import co.mysu.pushit.jserver.web.rest.vo.PushIdentifier;
import co.mysu.pushit.jserver.web.rest.vo.RestResponse;
import co.mysu.pushit.jserver.web.vo.PushItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController()
@RequestMapping("/rest/push")
public class PushItController {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@RequestMapping(method = POST, consumes = {"application/x-www-form-urlencoded;charset=UTF-8"})
	public @ResponseBody RestResponse pushMsg(PushItem item){
		messagingTemplate.convertAndSend("/topic/msgbus/" +  item.getPushId(), Arrays.asList(item));
		return new RestResponse(true);
	}

	@RequestMapping(method = GET)
	public @ResponseBody RestResponse getPushIdentifier(HttpServletRequest req){
		
		return new PushIdentifier(UUID.randomUUID().toString(), req.getRequestURL().toString());
	}

}
