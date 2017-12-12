package cn.com.taiji.module.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.com.taiji.module.domain.Menu;
import cn.com.taiji.module.domain.User;
/**
 * 
 * 类名称：RoleDto   
 * 类描述：对于Role的dto层对象   
 * 创建人：seon   
 * 创建时间：2017年12月8日 下午6:33:01 
 * @version
 */
public class RoleDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int roleid;

	private String creater;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createtime;

	private String roledesc;

	private String rolename;

	private int status;

	private String updatater;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatetime;

	//bi-directional many-to-many association to Menu
	@ManyToMany
	@JoinTable(
		name="roles_menu"
		, joinColumns={
			@JoinColumn(name="roleid")
			}
		, inverseJoinColumns={
			@JoinColumn(name="menuid")
			}
		)
	private List<Menu> menus;

	//bi-directional many-to-many association to User
	@ManyToMany(mappedBy="roles")
	private List<User> users;

	public RoleDto() {
	}

	public int getroleid() {
		return this.roleid;
	}

	public void setroleid(int roleid) {
		this.roleid = roleid;
	}

	public String getCreater() {
		return this.creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getroledesc() {
		return this.roledesc;
	}

	public void setroledesc(String roledesc) {
		this.roledesc = roledesc;
	}

	public String getrolename() {
		return this.rolename;
	}

	public void setrolename(String rolename) {
		this.rolename = rolename;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getUpdatater() {
		return this.updatater;
	}

	public void setUpdatater(String updatater) {
		this.updatater = updatater;
	}

	public Date getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public List<Menu> getMenus() {
		return this.menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "RoleDto [roleid=" + roleid + ", creater=" + creater + ", createtime=" + createtime + ", roledesc="
				+ roledesc + ", rolename=" + rolename + ", status=" + status + ", updatater=" + updatater
				+ ", updatetime=" + updatetime + "]";
	}

}