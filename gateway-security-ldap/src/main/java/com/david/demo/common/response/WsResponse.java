package com.david.demo.common.response;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class WsResponse<T> {

    private MessageCode status;
    private List<String> messages;
    private T result;

    public WsResponse() {
        messages = new ArrayList<>();
    }

    public WsResponse(MessageCode status, T result) {
        messages = new ArrayList<>();
        this.status = status;
        this.result = result;
    }

    public MessageCode getStatus() {
        return status;
    }

    public void setStatus(MessageCode status) {
        this.status = status;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "error code:" + status + " result:" + result;
    }

    public static <T> WsResponse<T> failure(String msg) {
        WsResponse<T> resp = new WsResponse<T>();
        resp.status = MessageCode.COMMON_FAILURE;
        resp.getMessages().add(msg);
        return resp;
    }

    public static <T> WsResponse<T> failure(MessageCode messageCode) {
        WsResponse<T> resp = new WsResponse<T>();
        resp.status = messageCode;
        resp.getMessages().add(messageCode.getMsg());
        return resp;
    }

    public static <T> WsResponse<T> failure(MessageCode messageCode, String message) {
        WsResponse<T> resp = new WsResponse<T>();
        resp.status = messageCode;
        if(StringUtils.isNotBlank(messageCode.getMsg())){
        	resp.getMessages().add(messageCode.getMsg());
        }
        if (StringUtils.isNotBlank(message)) {
            resp.getMessages().add(message);
        }
        return resp;
    }

    public static <T> WsResponse<T> success() {
        WsResponse<T> resp = new WsResponse<T>();
        resp.status = MessageCode.COMMON_SUCCESS;
        resp.getMessages().add(MessageCode.COMMON_SUCCESS.getMsg());
        return resp;
    }

    public static <K> WsResponse<K> success(K t) {
        WsResponse<K> resp = new WsResponse<>();
        resp.status = MessageCode.COMMON_SUCCESS;
        resp.getMessages().add(MessageCode.COMMON_SUCCESS.getMsg());
        resp.result = t;

        return resp;
    }

    /**
     * 判断字符串是否已经是 WsResponse返回格式
     *
     * @param json
     * @return
     */
    public static boolean isWsResponseJson(String json) {
        if (json != null && json.indexOf("\"status\":") != -1
                && json.indexOf("\"messages\":") != -1
                && json.indexOf("\"result\":") != -1) {
            return true;
        } else {
            return false;
        }
    }
}
