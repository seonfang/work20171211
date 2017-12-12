package cn.com.taiji;


import java.util.HashMap;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import cn.com.taiji.module.domain.Role;
import cn.com.taiji.module.domain.User;
import cn.com.taiji.module.dto.RoleDto;
import cn.com.taiji.module.dto.UserDto;
import cn.com.taiji.module.repository.UserRepository;
import cn.com.taiji.module.service.RoleService;
import cn.com.taiji.module.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PermissionsApplicationTests {

	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository;
	@Test
	public void contextLoads() {
//		System.out.println("findAllUser :"+userService.findAllUser());//成功
//		System.out.println(userRepository.findAll());//成功
//		userService.deleteUser("123456789");//成功
//		userService.deleteUserByUserid(1);//成功
		for(int i = 500; i<550;i++) {
			User u = userRepository.findOne("123456789");
			UserDto userDto = new UserDto();
			 BeanUtils.copyProperties(u, userDto);
			 userDto.setUserid(i);
			 userDto.setUsername("小"+i);
			userDto.setId(UUID.randomUUID().toString().replaceAll("-", ""));//"84d27a9964c14372894547a5acc273a6");
			userDto.setStatus(1);
			userService.addUser(userDto);
			System.out.println("成功 "+i);
		}
		
/// 		System.out.println("Page: "+userService.getPage(1, 2,new HashMap<>(), new HashMap<>()));
//		System.out.println("FindAll : "+userRepository.findAll());
	}
	
	@Autowired
	RoleService roleService;
	
	@Test
	public void RS() {
//		Role r= new Role();
//		RoleDto rd = new RoleDto();
//		r.setroleid(123);
//		r.setrolename("周一");
//		r.setStatus(1);
//		BeanUtils.copyProperties(r, rd);
//		roleService.addRole(rd);
		
//		roleService.deleteRole(123);
//		System.out.println(roleService.findAllRole());
//		System.out.println(roleService.findRole(123));
 		System.out.println("Page: "+userService.getPage(1, 2,new HashMap<>(), new HashMap<>()));
//		System.out.println("Page: "+roleService.getPage(1, 2,new HashMap<>(), null));
	}

}
