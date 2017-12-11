package cn.com.taiji.module.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import cn.com.taiji.module.domain.Role;

/**
 *  对role的 增加 修改 删除（逻辑删除） 查询单条（根据 roleid） 查询所有（即逻辑存在的）
 * 类名称：RoleRepository 
 * 类描述： 建立接口进行使用JPA
 * 创建人：seon 创建时间：2017年12月8日 下午6:32:24
 * 
 * @version
 */
public interface RoleRepository extends JpaRepository<Role, Integer>,JpaSpecificationExecutor<Role>,PagingAndSortingRepository<Role,Integer> {

	/**
	 * 
	 * @Description: 查询 ：根据roseid进行查询单条数据
	 * @date 2017年12月10日
	 */
	Role findByRoleid(int roleid);
	
	/**
	 * 增加 直接使用SaveAndFlush
	 */
	
	/**
	 * 根据roleid删除Role用户
	 * 		即将status字段状态改为0
	 */
	@Transactional 
	@Modifying
	@Query("UPDATE Role r SET r.status = 0 WHERE r.roleid=?1")
	int  deleteByRoleid(int roleid);
	
	/**
	 * 查询所有role信息 
	 * 即查询字段status 为"1"的数据
	 */
	@Transactional 
	@Query("SELECT r FROM Role r WHERE r.status = 1")
	List<Role> findAllAlive();
}
