package cn.com.taiji.module.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import cn.com.taiji.module.domain.Role;
import cn.com.taiji.module.domain.User;
import cn.com.taiji.module.dto.RoleDto;
import cn.com.taiji.module.dto.UserDto;
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
					role.setUpdatater(null == userDetails ? "" :userDetails.getUsername());
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
	
	/**
	 * 
	 * @Description: 分页查询Role信息
	 * @param page
	 * @param pageSize
	 * @param orderMaps
	 * @param filters
	 * @return Map  
	 * @throws
	 * @author seon
	 * @date 2017年12月11日
	 */
	public Map getPage(int page,int pageSize,
			HashMap<String,String> orderMaps,HashMap<String,String> filters) {
		Page<Role> pageContent;
		if (pageSize < 0)
			pageSize = 0;
		if (pageSize > 100)
			pageSize = 100;

		List<Order> orders = new ArrayList<Order>();
		if (orderMaps != null) {
			for (String key : orderMaps.keySet()) {
				if ("DESC".equalsIgnoreCase(orderMaps.get(key))) {
					orders.add(new Order(Direction.DESC, key));
				} else {
					orders.add(new Order(Direction.ASC, key));
				}
			}
			
		}
		PageRequest pageable;
		if (orders.size() > 0) {
			pageable = new PageRequest(page, pageSize, new Sort(orders));
		} else {
			pageable = new PageRequest(page, pageSize);
		}

		if (filters != null&&!"".equals(filters)) {
			Specification<Role> spec = new Specification<Role>() {
				@Override
				public Predicate toPredicate(Root<Role> root,
						CriteriaQuery<?> query, CriteriaBuilder cb) {
					List<Predicate> pl = new ArrayList<Predicate>();
					for (String key : filters.keySet()) {
						String value = filters.get(key);
						if ("enabled".equalsIgnoreCase(key)) {
							if ("true".equalsIgnoreCase(value)) {
								pl.add(cb.equal(root.get(key), true));
							} else if ("false".equalsIgnoreCase(value)) {
								pl.add(cb.equal(root.get(key), false));
							}
						} else if ("code".equalsIgnoreCase(key)) {
							if (value.length() > 0)
								pl.add(cb.like(root.<String> get(key), value + "%"));
						} else if ("name".equalsIgnoreCase(key)) {
							if (value.length() > 0)
								pl.add(cb.like(root.get(key), value));
						}
					}
					return cb.and(pl.toArray(new Predicate[0]));
				}
			};
			pageContent = roleRepository.findAll(spec, pageable);
		} else {
			pageContent = roleRepository.findAll(pageable);
		}
// 		System.out.println("PageContent :"+pageContent);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", pageContent.getTotalElements());
		map.put("roles", rolePage2Dto(pageContent));
		return map;
	}

	/**
	 * 
	 * @Description: 将Role集合转为RoleDto集合
	 * @param pageContent
	 * @return List<RoleDto>  
	 * @throws  调用不到此方法
	 * @author seon
	 * @date 2017年12月11日
	 */
	private List<RoleDto> rolePage2Dto(Page<Role> pageContent) {
		List<Role> roleList = pageContent.getContent();
		List<RoleDto> userDtoList = new ArrayList<RoleDto>();
		for (Role role : roleList) {
			userDtoList.add(roleToRoleDto(role));
		}
		return userDtoList;
	}
	/**
	 * 
	 * @Description: 方法说明
	 * @param role
	 * @return RoleDto  将Role对象转换为RoleDto对象
	 * @throws
	 * @author seon
	 * @date 2017年12月11日
	 */
	private RoleDto roleToRoleDto(Role role) {
		RoleDto roleDto = new RoleDto();
		BeanUtils.copyProperties(role, roleDto);
		return roleDto;
	}
	
}
