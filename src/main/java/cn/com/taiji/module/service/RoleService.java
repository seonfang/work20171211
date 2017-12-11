package cn.com.taiji.module.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import cn.com.taiji.module.domain.Role;
import cn.com.taiji.module.dto.RoleDto;
import cn.com.taiji.module.repository.RoleRepository;

/**
 * 
 * 类名称：RoleService   
 * 类描述：   
 * 创建人：seon   
 * 创建时间：2017年12月8日 下午6:50:49 
 * @version
 */
@Service
public class RoleService {

	@Autowired
	RoleRepository roleRepository;
	
	/**
	 * 
	 * @Description: 增加 修改 Role
	 * @param roleDto void  
	 * @throws
	 * @author seon
	 * @date 2017年12月10日
	 */
	public void addRole(RoleDto roleDto) {
		Role role = new Role();
		 BeanUtils.copyProperties(roleDto, role);
		//根据传进来的id进行获取数据判断
		 if(roleRepository.findOne(role.getroleid()) ==null) {
			 //添加
			 SecurityContext context = SecurityContextHolder.getContext();
				if (null!=context.getAuthentication()&&!context.getAuthentication().isAuthenticated()) {
					UserDetails userDetails = (UserDetails) SecurityContextHolder
							.getContext().getAuthentication().getPrincipal();
					role.setCreater(null == userDetails ? "" : userDetails.getUsername());
				}else {
					role.setCreater("游客");
				}
			//role.setId(UUID.randomUUID().toString().replaceAll("-", ""));
			role.setStatus(1);
			 role.setCreatetime(Calendar.getInstance().getTime());
			 roleRepository.saveAndFlush(role);
		 }else {
			 //修改
			 SecurityContext context = SecurityContextHolder.getContext();
				if (null!=context.getAuthentication()&&!context.getAuthentication().isAuthenticated()) {
					UserDetails userDetails = (UserDetails) SecurityContextHolder
							.getContext().getAuthentication().getPrincipal();
					role.setUpdatater(null == userDetails ? "" : userDetails.getUsername());
				}else {
					role.setUpdatater("游客");
				}
			 role.setUpdatetime(Calendar.getInstance().getTime());
			 roleRepository.saveAndFlush(role);
		 }
	}
	/**
	  * 根据roleid逻辑删除 Role数据 ：修改status 
	 * 状态 ： 逻辑删除为0  未逻辑删除为1
	 * @Description: 
	 * @param roleid void  
	 * @throws
	 * @author seon
	 * @date 2017年12月10日
	 */
	public void deleteRole(int roleid) {
		roleRepository.deleteByRoleid(roleid);
//		roleRepository.delete(roleid);
	}
	
	/**
	 * 查询所有Role的信息 ，即查询status状态为1的数据
	 * @Description: 方法说明
	 * @return List<Role>  
	 * @throws
	 * @author seon
	 * @date 2017年12月10日
	 */
	public List<Role> findAllRole() {
		List<Role> list = new ArrayList<>();
		list = roleRepository.findAllAlive();
		return list;
	}
	
	/**
	 * 根据 roleid查询一条role信息
	 * @Description: 方法说明
	 * @param roleid
	 * @return RoleDto  
	 * @throws
	 * @author seon
	 * @date 2017年12月11日
	 */
	public RoleDto findRole(int roleid) {
		Role  role = new Role();
		role = roleRepository.findOne(roleid);
		RoleDto roleDto = new RoleDto();
		 BeanUtils.copyProperties(role, roleDto);
		return roleDto;
	}
	
}
