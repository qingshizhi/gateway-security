package com.david.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableEurekaClient
@EnableRedisRepositories
public class GatewaySecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewaySecurityApplication.class, args);
	}

}



/**

http://localhost:6013/index/hello

http://localhost:6013/api/login?username=quanquan001&password=HRM_TEST166!Aa61438

--登录
var postData = '{"username":"quanquan001","password":"HRM_TEST166!Aa61438"}';
var oAjax = null;
try{
　　oAjax = new XMLHttpRequest();
}catch(e){
　　oAjax = new ActiveXObject("Microsoft.XMLHTTP");
};
//post方式打开文件
oAjax.open('post','http://localhost:6013/api/login',true);
//post相比get方式提交多了个这个
oAjax.setRequestHeader("Content-type","application/json");
//post发送数据
oAjax.send(postData);
oAjax.onreadystatechange = function(){
　　//当状态为4的时候，执行以下操作
　　if(oAjax.readyState == 4){
　　　　try{
　　　　　　alert(oAjax.responseText);
　　　　}catch(e){
　　　　　　alert('你访问的页面出错了');
　　　　};
　　};
};

--退保
var postData = {};
var oAjax = null;
try{
　　oAjax = new XMLHttpRequest();
}catch(e){
　　oAjax = new ActiveXObject("Microsoft.XMLHTTP");
};
//post方式打开文件
oAjax.open('post','http://localhost:6013/api/logout',true);
//post相比get方式提交多了个这个
oAjax.setRequestHeader("Content-type","application/x-www-form-urlencoded");
//post发送数据
oAjax.send(postData);
oAjax.onreadystatechange = function(){
　　//当状态为4的时候，执行以下操作
　　if(oAjax.readyState == 4){
　　　　try{
　　　　　　alert(oAjax.responseText);
　　　　}catch(e){
　　　　　　alert('你访问的页面出错了');
　　　　};
　　};
};

**/