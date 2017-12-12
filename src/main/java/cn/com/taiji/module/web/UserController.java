package cn.com.taiji.module.web;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.util.HashMap;
import java.util.Map;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.taiji.module.service.UserService;

@Controller
public class UserController {

	@Autowired
	UserService userService;
	
	@GetMapping(value="touserlist")
	public String Login() {
		return "userList";
	}
	
	@ResponseBody
	@RequestMapping(value="/getuserlist",method=RequestMethod.POST)
	public Map getUserList(/*int limit, int offset, String departmentname, String statu */) {
//		model.addAttribute(userService.getPage(1, 2, null, null));
	
//		System.out.println("limit: "+limit+" offset: "+offset);
		System.out.println( ">>>>>>>>>>>>>>>");
		return userService.getPage(0, 4,null, null);
	}
}
