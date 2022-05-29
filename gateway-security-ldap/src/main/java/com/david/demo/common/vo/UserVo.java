package com.david.demo.common.vo;



public class UserVo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 电话号码
	 */
	private String phone;

	/**
	 * 性别 1-男 2-女
	 */
	private String sex;
	/**
	 * 用户名称
	 */
	private String username;
	
	/**
	 * 用户中文名称
	 */
	private String userCname;

	/** 登录密码 */
	private String password;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserCname() {
		return userCname;
	}

	public void setUserCname(String userCname) {
		this.userCname = userCname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


   
	

}
