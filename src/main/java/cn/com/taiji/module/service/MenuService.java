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

import cn.com.taiji.module.domain.Menu;
import cn.com.taiji.module.domain.Menu;
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
}
