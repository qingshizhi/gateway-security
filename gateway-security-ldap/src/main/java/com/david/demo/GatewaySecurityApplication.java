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

--��¼
var postData = '{"username":"quanquan001","password":"HRM_TEST166!Aa61438"}';
var oAjax = null;
try{
����oAjax = new XMLHttpRequest();
}catch(e){
����oAjax = new ActiveXObject("Microsoft.XMLHTTP");
};
//post��ʽ���ļ�
oAjax.open('post','http://localhost:6013/api/login',true);
//post���get��ʽ�ύ���˸����
oAjax.setRequestHeader("Content-type","application/json");
//post��������
oAjax.send(postData);
oAjax.onreadystatechange = function(){
����//��״̬Ϊ4��ʱ��ִ�����²���
����if(oAjax.readyState == 4){
��������try{
������������alert(oAjax.responseText);
��������}catch(e){
������������alert('����ʵ�ҳ�������');
��������};
����};
};

--�˱�
var postData = {};
var oAjax = null;
try{
����oAjax = new XMLHttpRequest();
}catch(e){
����oAjax = new ActiveXObject("Microsoft.XMLHTTP");
};
//post��ʽ���ļ�
oAjax.open('post','http://localhost:6013/api/logout',true);
//post���get��ʽ�ύ���˸����
oAjax.setRequestHeader("Content-type","application/x-www-form-urlencoded");
//post��������
oAjax.send(postData);
oAjax.onreadystatechange = function(){
����//��״̬Ϊ4��ʱ��ִ�����²���
����if(oAjax.readyState == 4){
��������try{
������������alert(oAjax.responseText);
��������}catch(e){
������������alert('����ʵ�ҳ�������');
��������};
����};
};

**/