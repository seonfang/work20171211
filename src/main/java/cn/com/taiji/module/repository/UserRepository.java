package cn.com.taiji.module.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import cn.com.taiji.module.domain.User;

/**
 * 
 * 类名称：UserRepository 
 * 类描述： 建立接口进行使用JPA
 * 创建人：seon 创建时间：2017年12月8日 下午6:32:24
 * @Transactional 
 * 
 * 					注解用于提交事务，若没有带上这句，会报事务异常提示。
 * 					用于public的方法上
 * @version
 */

public interface UserRepository extends JpaRepository<User, String>,JpaSpecificationExecutor<User>,PagingAndSortingRepository<User,String> {
		/**
		 * 根据userid进行查询
		 * @Description: 方法说明
		 * @param userid
		 * @return List<User>  
		 * @throws
		 * @author seon
		 * @date 2017年12月8日
		 */
		List<User> findByUserid(int userid);
		
		/**
		 * 根据id（UUID）删除User用户
		 * 即将status字段状态改为0
		 */
		@Transactional 
		@Modifying
		@Query("UPDATE User u SET u.status = 0 WHERE u.id=?1")
		int  deleteById(String id);

		/**
		 * 根据userid 删除User用户
		 * 即将status字段状态改为0
		 */
 		@Transactional 
 		@Modifying
 		@Query("UPDATE User u SET u.status = 0 WHERE u.userid=?1")
 		int  deleteByUserid(int userid);
		
		/**
		 * 查询所有user信息 
		 * 即查询字段status 为"1"的数据
		 */
		@Transactional 
		@Query("SELECT u FROM User u WHERE u.status = 1")
		List<User> findAllAlive();
}
