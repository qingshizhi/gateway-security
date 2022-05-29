package com.david.demo.common.vo;

import java.util.Date;

/**
 * 传送vo
 * 
 * @author lenovo
 *
 */
public class UserVo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 电话号码
	 */
	private String phone;

	/** 用户代码 */
	private String userCode;
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

	/** 登录来源 imwx-保险商城微信端 ,smwx-积分商城微信端 ,claimwx-理赔微信前端 ,impf-保险商城后台管理系统 ,smpf-积分商城后台管理系统 ,claimpf-积分商城后台管理系统 ,subpf-内容后台管理系统*/
	private String source;

	/**
	 * 是否登录成功
	 */
	private boolean isLogin = false;
	
	/**
	 * 登陆类型
	 * 1-登陆成功
	 * 2-用户名密码错误
	 * 3-用户账号还未生效
	 * 4-账户已过期
	 */
	private String loginFlaseType;

	/**
	 * 返回信息
	 */
	private String returnMessage;

	/**
	 * 左侧权限菜单
	 */
	private String menuHtml;

	/**
	 * 验证码
	 */
	private String validCode;
	
	/** 登录验证随机码*/
	private String randomnum;

	/**
	 * 登录IP
	 */
	private String loginIp;

	/** 登录类型 1-正常登录 2-自动登录 */
	private String loginType = "1";

	private String uuid;

	private String openId;

	private String appId;
	
	private String checkCode;
	
	/** 是否显示升级公告 1:需显示 0:已显示（不显示），默认不显示*/
	private String isShowUp;
	
	/** 密码是否即将过期 默认否  0-未过期 1-即将过期 2-已过期*/
	private String isPassExpire = "0";
	
	/** 用户过期时间*/
	private Date validEndTime;
	
	/** token*/
	private String token;
	
	/**
	 * 是否有多个门店操作权限
	 */
	private boolean isMoreCom = false;
	
	/**是否有操作权限1*/
	private boolean isPower1 = false;
	
	/**是否有操作权限2*/
	private boolean isPower2 = false;
	
	/** 请求成功标志*/
	private boolean sendFlag = true;
	
	/** 失败提示 请求成功标志为false时提示出errorMessage*/
	private String errorMessage;
	
	/** 返回信息*/
	private String message;
	
	/** 返回编码*/
	private String code;
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSendFlag() {
		return sendFlag;
	}

	public void setSendFlag(boolean sendFlag) {
		this.sendFlag = sendFlag;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserCname() {
		return userCname;
	}

	public void setUserCname(String userCname) {
		this.userCname = userCname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSource() {
		return source;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public void setSource(String source) {
		this.source = source;
	}

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	public String getLoginFlaseType() {
		return loginFlaseType;
	}

	public void setLoginFlaseType(String loginFlaseType) {
		this.loginFlaseType = loginFlaseType;
	}

	public String getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}

	public String getMenuHtml() {
		return menuHtml;
	}

	public void setMenuHtml(String menuHtml) {
		this.menuHtml = menuHtml;
	}
	
	public String getValidCode() {
		return validCode;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}

	public String getRandomnum() {
		return randomnum;
	}

	public void setRandomnum(String randomnum) {
		this.randomnum = randomnum;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public String getIsShowUp() {
		return isShowUp;
	}

	public void setIsShowUp(String isShowUp) {
		this.isShowUp = isShowUp;
	}

	public String getIsPassExpire() {
		return isPassExpire;
	}

	public void setIsPassExpire(String isPassExpire) {
		this.isPassExpire = isPassExpire;
	}

	public Date getValidEndTime() {
		return validEndTime;
	}

	public void setValidEndTime(Date validEndTime) {
		this.validEndTime = validEndTime;
	}

	public boolean isMoreCom() {
		return isMoreCom;
	}

	public void setMoreCom(boolean isMoreCom) {
		this.isMoreCom = isMoreCom;
	}

	public boolean isPower1() {
		return isPower1;
	}

	public void setPower1(boolean isPower1) {
		this.isPower1 = isPower1;
	}

	public boolean isPower2() {
		return isPower2;
	}

	public void setPower2(boolean isPower2) {
		this.isPower2 = isPower2;
	}

}
