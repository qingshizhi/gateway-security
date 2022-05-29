package com.david.demo.common.vo;



public class ReturnVo implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private String result = "Success";
	private String message;
	private Object data;
	private String token;
	private String code;
	private String msg;
	private UserVo userVo;
	private Integer count;
	private boolean sendFlag=false;
	public ReturnVo(){

	}

	public ReturnVo(String code,String msg,Object data){
		this.code=code;
		this.msg=msg;
		this.data = data;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public static ReturnVo  error(String code, String msg){
		return new ReturnVo( code,msg , null);
	}
	public static ReturnVo  success(Object data){
		ReturnVo returnVo= new ReturnVo( "200","请求成功" , data);
		returnVo.setSendFlag(true);
		return returnVo;
	}

	public UserVo getUserVo() {
		return userVo;
	}
	public void setUserVo(UserVo userVo) {
		this.userVo = userVo;
	}
	public boolean isSendFlag() {
		return sendFlag;
	}
	public void setSendFlag(boolean sendFlag) {
		this.sendFlag = sendFlag;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ReturnVo{" +
				"result='" + result + '\'' +
				", message='" + message + '\'' +
				", data=" + data +
				", token='" + token + '\'' +
				", code='" + code + '\'' +
				", msg='" + msg + '\'' +
				", userVo=" + userVo +
				", sendFlag=" + sendFlag +
				'}';
	}
}
