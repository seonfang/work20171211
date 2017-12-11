package cn.com.taiji.module.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the roles database table.
 * 角色表 
 */
@Entity
@Table(name="roles")
@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r")
public class Role implements Serializable {
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
	@ManyToMany(fetch =FetchType.EAGER) 
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

	public Role() {
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

	public Role(int roleid, String creater, Date createtime, String roledesc, String rolename, int status,
			String updatater, Date updatetime, List<Menu> menus, List<User> users) {
		super();
		this.roleid = roleid;
		this.creater = creater;
		this.createtime = createtime;
		this.roledesc = roledesc;
		this.rolename = rolename;
		this.status = status;
		this.updatater = updatater;
		this.updatetime = updatetime;
		this.menus = menus;
		this.users = users;
	}

	@Override
	public String toString() {
		return "Role [roleid=" + roleid + ", creater=" + creater + ", createtime=" + createtime + ", roledesc="
				+ roledesc + ", rolename=" + rolename + ", status=" + status + ", updatater=" + updatater
				+ ", updatetime=" + updatetime + "]";
	}

	
}