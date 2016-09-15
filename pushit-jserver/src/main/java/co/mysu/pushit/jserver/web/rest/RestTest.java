package co.mysu.pushit.jserver.web.rest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.mysu.pushit.jserver.web.rest.vo.Hello;

@RestController
public class RestTest {
	@RequestMapping("/test")
	public String test(){
		return "test " + System.currentTimeMillis();
	}
	
	@RequestMapping("/test2")
	public @ResponseBody Hello test2(){
		return new Hello("Miguel", "Hello Miguel");
	}
}
