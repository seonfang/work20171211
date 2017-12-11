package cn.com.taiji.module.web;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

	@Autowired
	
	
	@GetMapping("/touserlist")
	public String showUserList() {
		return "userList";
	}
	
	@RequestMapping(value="/getuserlist",method=RequestMethod.POST)
	public String getUserList() {
		
		return "userList";
	}
}
