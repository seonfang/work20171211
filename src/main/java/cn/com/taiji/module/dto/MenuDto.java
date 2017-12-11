package cn.com.taiji.module.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cn.com.taiji.module.domain.Role;

public class MenuDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int menuid;

	private String creater;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createtime;

	private String menudesc;

	private String menuname;

	private String mlevel;

	private int status;

	private String updater;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatetime;

	private String url;

	//bi-directional many-to-many association to Role
	@ManyToMany(mappedBy="menus")
	private List<Role> roles;

	public MenuDto() {
	}

	public int getMenuid() {
		return this.menuid;
	}

	public void setMenuid(int menuid) {
		this.menuid = menuid;
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

	public String getMenudesc() {
		return this.menudesc;
	}

	public void setMenudesc(String menudesc) {
		this.menudesc = menudesc;
	}

	public String getMenuname() {
		return this.menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	public String getMlevel() {
		return this.mlevel;
	}

	public void setMlevel(String mlevel) {
		this.mlevel = mlevel;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getUpdater() {
		return this.updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public Date getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}