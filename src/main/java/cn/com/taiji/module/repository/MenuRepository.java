package cn.com.taiji.module.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.com.taiji.module.domain.Menu;
import cn.com.taiji.module.domain.Role;

/**
 * 
 * 类名称：MenuRepository 
 * 类描述： 建立接口进行使用JPA 
 * 创建人：seon 
 * 创建时间：2017年12月8日 下午6:32:24
 * 
 * @version
 */
public interface MenuRepository extends JpaRepository<Menu, Integer>,JpaSpecificationExecutor<Menu>,PagingAndSortingRepository<Menu,Integer> {

	/**
	 * 
	 * @Description: 查询 ：根据menuid进行查询单条数据
	 * @date 2017年12月10日
	 */
	Menu findByMenuid(int menuid);

	/**
	 * 增加 直接使用SaveAndFlush
	 */

	/**
	 * 根据menuid删除Menu用户 即将status字段状态改为0
	 */
	@Transactional 
	@Query("UPDATE Menu m SET m.status = 0 WHERE m.menuid=?1")
	int deleteByMenuid(int menuid);

	/**
	 * 查询所有menu信息 即查询字段status 为"1"的数据
	 */
	@Transactional 
	@Query("SELECT m FROM Menu m WHERE m.status = 1")
	List<Menu> findAllAlive();
	
	
}
