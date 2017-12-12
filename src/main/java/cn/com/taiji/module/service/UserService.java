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

import cn.com.taiji.module.domain.User;
import cn.com.taiji.module.dto.UserDto;
import cn.com.taiji.module.repository.UserRepository;

/**
 * 
 * 类名称：UserService   
 * 类描述：   实现增加 ，修改， 逻辑删除（根据userid / id），查询所有（逻辑存在的）
 * 创建人：seon   
 * 创建时间：2017年12月8日 下午6:50:49 
 * @version
 */
@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	/**
	 * 
	 * @Description: 增加 修改 user数据
	 * @param userDto void  
	 * @throws
	 * @author seon
	 * @date 2017年12月8日
	 */
	public void addUser(UserDto userDto) {
		User user = new User();
		 BeanUtils.copyProperties(userDto, user);
		//根据传进来的id进行获取数据判断
		 if(userRepository.findOne(user.getId()) ==null) {
			 //添加
			 SecurityContext context = SecurityContextHolder.getContext();
				if (null!=context.getAuthentication()&&!context.getAuthentication().isAuthenticated()) {
					UserDetails userDetails = (UserDetails) SecurityContextHolder
							.getContext().getAuthentication().getPrincipal();
					user.setCreater(null == userDetails ? "游客" : userDetails.getUsername());
				}else {
					user.setCreater("游客");
				}
			//user.setId(UUID.randomUUID().toString().replaceAll("-", ""));
			user.setStatus(1);
			 user.setCreatetime(Calendar.getInstance().getTime());
			 userRepository.saveAndFlush(user);
		 }else {
			 //修改
			 SecurityContext context = SecurityContextHolder.getContext();
				if (null!=context.getAuthentication()&&!context.getAuthentication().isAuthenticated()) {
					UserDetails userDetails = (UserDetails) SecurityContextHolder
							.getContext().getAuthentication().getPrincipal();
					user.setUpdater(null == userDetails ? "" : userDetails.getUsername());
				}else {
					user.setUpdater("游客");
				}
			 user.setUpdatetime(Calendar.getInstance().getTime());
			 userRepository.saveAndFlush(user);
		 }
  		
		
		
		 
	}
	
	
	/**
	 * 根据id（UUID）逻辑删除 User数据 ：修改status 
	 * 状态 ： 逻辑删除为0  未逻辑删除为1
	 * @Description: 方法说明 void  
	 * @throws
	 * @author seon
	 * @date 2017年12月8日
	 */
	public void deleteUser(String id) {
//		User user = userRepository.findOne(id);
//		user.setStatus(0);
//		 userRepository.saveAndFlush(user);
		userRepository.deleteById(id);
	}
	
	
	/**
	 * 根据userid逻辑删除 User数据 ：修改字段status 
	 * 状态 ： 逻辑删除为0  未逻辑删除为1
	 * @Description: 方法说明 void  
	 * @throws
	 * @author seon
	 * @date 2017年12月8日
	 */
	public void deleteUserByUserid(int userid) {
//		User user = (User) userRepository.findByUserid(userid);
//		user.setStatus(0);
//		 userRepository.saveAndFlush(user);
		userRepository.deleteByUserid(userid);
	}
	
	/**
	 * 查询所有的user信息
	 * 即查询字段status 为1 的数据信息
	 * @Description: 方法说明
	 * @return List<User>  
	 * @throws
	 * @author seon
	 * @date 2017年12月8日
	 */
	public List<User> findAllUser() {
		List<User> list = new ArrayList<>();
		list = userRepository.findAllAlive();
		return list;
	}
	
	/**
	 * 
	 * @Description: 查询用户列表（分页）
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
		Page<User> pageContent;
		if (pageSize < 1)
			pageSize = 1;
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

		if (filters != null) {
			Specification<User> spec = new Specification<User>() {
				@Override
				public Predicate toPredicate(Root<User> root,
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
			pageContent = userRepository.findAll(spec, pageable);
		} else {
			pageContent = userRepository.findAll(pageable);
		}
// 		System.out.println("PageContent :"+pageContent);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", pageContent.getTotalElements());
		map.put("rows", accountPage2Dto(pageContent));
		return map;
	}

	/**
	 * 
	 * @Description: 将User集合转为UserDto集合
	 * @param pageContent
	 * @return List<UserDto>  
	 * @throws
	 * @author seon
	 * @date 2017年12月11日
	 */
	private List<UserDto> accountPage2Dto(Page<User> pageContent) {
		List<User> userList = pageContent.getContent();
		List<UserDto> userDtoList = new ArrayList<UserDto>();
		for (User user : userList) {
			
			userDtoList.add(userToUserDto(user));
		}
		return userDtoList;
	}
	/**
	 * 
	 * @Description: 方法说明
	 * @param user
	 * @return UserDto  将User对象转换为UserDto对象
	 * @throws
	 * @author seon
	 * @date 2017年12月11日
	 */
	private UserDto userToUserDto(User user) {
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(user, userDto);
		return userDto;
	}
}
