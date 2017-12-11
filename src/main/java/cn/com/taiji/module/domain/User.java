package cn.com.taiji.module.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String creater;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createtime;

	private int status;

	private String updater;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatetime;

	private int userage;

	@Temporal(TemporalType.DATE)
	private Date userbrith;

	private String usercategory;

	private String usercity;

	private String userdepartment;

	private String userdepartment_Second;

	@Column(name="userdept_level")
	private String userdeptLevel;

	@Column(name="userdept_name")
	private String userdeptName;

	@Column(name="userdept_no")
	private int userdeptNo;

	private String useredu;

	private String useremail;

	private int userid;

	@Temporal(TemporalType.DATE)
	private Date userintime;

	private String username;

	private String userpassword;

	private int userphone;

	@Lob
	private byte[] userphoto;

	private int usersex;

	//bi-directional many-to-many association to Role
	@ManyToMany
	@JoinTable(
		name="user_roles"
		, joinColumns={
			@JoinColumn(name="id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="roleid")
			}
		)
	private List<Role> roles;

	public User() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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

	public int getUserage() {
		return this.userage;
	}

	public void setUserage(int userage) {
		this.userage = userage;
	}

	public Date getUserbrith() {
		return this.userbrith;
	}

	public void setUserbrith(Date userbrith) {
		this.userbrith = userbrith;
	}

	public String getUsercategory() {
		return this.usercategory;
	}

	public void setUsercategory(String usercategory) {
		this.usercategory = usercategory;
	}

	public String getUsercity() {
		return this.usercity;
	}

	public void setUsercity(String usercity) {
		this.usercity = usercity;
	}

	public String getUserdepartment() {
		return this.userdepartment;
	}

	public void setUserdepartment(String userdepartment) {
		this.userdepartment = userdepartment;
	}

	public String getUserdepartment_Second() {
		return this.userdepartment_Second;
	}

	public void setUserdepartment_Second(String userdepartment_Second) {
		this.userdepartment_Second = userdepartment_Second;
	}

	public String getUserdeptLevel() {
		return this.userdeptLevel;
	}

	public void setUserdeptLevel(String userdeptLevel) {
		this.userdeptLevel = userdeptLevel;
	}

	public String getUserdeptName() {
		return this.userdeptName;
	}

	public void setUserdeptName(String userdeptName) {
		this.userdeptName = userdeptName;
	}

	public int getUserdeptNo() {
		return this.userdeptNo;
	}

	public void setUserdeptNo(int userdeptNo) {
		this.userdeptNo = userdeptNo;
	}

	public String getUseredu() {
		return this.useredu;
	}

	public void setUseredu(String useredu) {
		this.useredu = useredu;
	}

	public String getUseremail() {
		return this.useremail;
	}

	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}

	public int getUserid() {
		return this.userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public Date getUserintime() {
		return this.userintime;
	}

	public void setUserintime(Date userintime) {
		this.userintime = userintime;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserpassword() {
		return this.userpassword;
	}

	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}

	public int getUserphone() {
		return this.userphone;
	}

	public void setUserphone(int userphone) {
		this.userphone = userphone;
	}

	public byte[] getUserphoto() {
		return this.userphoto;
	}

	public void setUserphoto(byte[] userphoto) {
		this.userphoto = userphoto;
	}

	public int getUsersex() {
		return this.usersex;
	}

	public void setUsersex(int usersex) {
		this.usersex = usersex;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", creater=" + creater + ", createtime=" + createtime + ", status=" + status
				+ ", updater=" + updater + ", updatetime=" + updatetime + ", userage=" + userage + ", userbrith="
				+ userbrith + ", usercategory=" + usercategory + ", usercity=" + usercity + ", userdepartment="
				+ userdepartment + ", userdepartment_Second=" + userdepartment_Second + ", userdeptLevel="
				+ userdeptLevel + ", userdeptName=" + userdeptName + ", userdeptNo=" + userdeptNo + ", useredu="
				+ useredu + ", useremail=" + useremail + ", userid=" + userid + ", userintime=" + userintime
				+ ", username=" + username + ", userpassword=" + userpassword + ", userphone=" + userphone
				+ ", userphoto=" + Arrays.toString(userphoto) + ", usersex=" + usersex  + "]";
	}

	
}