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

import cn.com.taiji.module.domain.Menu;
import cn.com.taiji.module.domain.Menu;
import cn.com.taiji.module.domain.Menu;
import cn.com.taiji.module.dto.MenuDto;
import cn.com.taiji.module.dto.MenuDto;
import cn.com.taiji.module.dto.MenuDto;
import cn.com.taiji.module.repository.MenuRepository;

/**
 * 
 * 类名称：MenuService   
 * 类描述：   
 * 创建人：seon   
 * 创建时间：2017年12月8日 下午6:50:49 
 * @version
 */
@Service
public class MenuService {

	@Autowired
	MenuRepository menuRepository;
	
	/** 
	 * 
	 * @Description: 增加 修改 Menu
	 * @param menuDto void  
	 * @throws
	 * @author seon
	 * @date 2017年12月10日
	 */
	public void addMenu(MenuDto menuDto) {
		Menu menu = new Menu();
		 BeanUtils.copyProperties(menuDto, menu);
		//根据传进来的id进行获取数据判断
		 if(menuRepository.findOne(menu.getMenuid()) ==null) {
			 //添加
			 SecurityContext context = SecurityContextHolder.getContext();
				if (null!=context.getAuthentication()&&!context.getAuthentication().isAuthenticated()) {
					UserDetails userDetails = (UserDetails) SecurityContextHolder
							.getContext().getAuthentication().getPrincipal();
					menu.setCreater(null == userDetails ? "" : userDetails.getUsername());
				}else {
					menu.setCreater("游客");
				}
			//menu.setId(UUID.randomUUID().toString().replaceAll("-", ""));
			menu.setStatus(1);
			 menu.setCreatetime(Calendar.getInstance().getTime());
			 menuRepository.saveAndFlush(menu);
		 }else {
			 //修改
			 SecurityContext context = SecurityContextHolder.getContext();
				if (null!=context.getAuthentication()&&!context.getAuthentication().isAuthenticated()) {
					UserDetails userDetails = (UserDetails) SecurityContextHolder
							.getContext().getAuthentication().getPrincipal();
					menu.setUpdater(null == userDetails ? "" : userDetails.getUsername());
				}else {
					menu.setUpdater("游客");
				}
			 menu.setUpdatetime(Calendar.getInstance().getTime());
			 menuRepository.saveAndFlush(menu);
		 }
	}
	
	/**
	 * 根据menuid逻辑删除 Menu数据 ：修改status 
	 * 状态 ： 逻辑删除为0  未逻辑删除为1 
	 * @Description: 方法说明
	 * @param menuid void  
	 * @throws
	 * @author seon
	 * @date 2017年12月10日
	 */
	public void deleteMenu(int menuid) {
		menuRepository.deleteByMenuid(menuid);
//		menuRepository.delete(menuid);
	}

	/**
	 * 查询所有Menu的信息 ，即查询status状态为1的数据
	 * @Description: 方法说明
	 * @return List<Menu>  
	 * @throws
	 * @author seon
	 * @date 2017年12月10日
	 */
	public List<Menu> selectAllMenu() {
		List<Menu> list = new ArrayList<>();
		list = menuRepository.findAllAlive();
		return list;
	}
	/**
	 * 
	 * @Description: 分页查询Menu信息
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
		Page<Menu> pageContent;
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
			Specification<Menu> spec = new Specification<Menu>() {
				@Override
				public Predicate toPredicate(Root<Menu> root,
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
			pageContent = menuRepository.findAll(spec, pageable);
		} else {
			pageContent = menuRepository.findAll(pageable);
		}
 		System.out.println("PageContent :"+pageContent);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", pageContent.getTotalElements());
		map.put("menus", accountPage2Dto(pageContent));
		return map;
	}

	/**
	 * 
	 * @Description: 将Menu集合转为MenuDto集合
	 * @param pageContent
	 * @return List<MenuDto>  
	 * @throws
	 * @author seon
	 * @date 2017年12月11日
	 */
	private List<MenuDto> accountPage2Dto(Page<Menu> pageContent) {
		List<Menu> menuList = pageContent.getContent();
		List<MenuDto> menuDtoList = new ArrayList<MenuDto>();
		for (Menu menu : menuList) {
			
			menuDtoList.add(menuToMenuDto(menu));
		}
		return menuDtoList;
	}
	/**
	 * 
	 * @Description: 方法说明
	 * @param menu
	 * @return MenuDto  将Menu对象转换为MenuDto对象
	 * @throws
	 * @author seon
	 * @date 2017年12月11日
	 */
	private MenuDto menuToMenuDto(Menu menu) {
		MenuDto menuDto = new MenuDto();
		BeanUtils.copyProperties(menu, menuDto);
		return menuDto;
	}
	
}
