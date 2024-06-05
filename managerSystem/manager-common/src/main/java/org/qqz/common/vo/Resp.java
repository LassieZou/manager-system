package org.qqz.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: jie.qian
 * @date: 2019-09-16 9:08
 */
@Data
public class Resp<T> implements Serializable {
    private String retCode;//返回码
    private String retMsg;//返回信息
    private T data;//携带数据

    public Resp() {
    }

    public Resp(String retCode, String retMsg, T data) {
        this.retCode = retCode;
        this.retMsg = retMsg;
        this.data = data;
    }

    /**
     * 传入数据成功
     *
     * @param data
     */
    public static <T> Resp success(T data) {
        Resp resp = new Resp();
        resp.retCode = "00";
        resp.retMsg = "success";
        resp.data = data;
        return resp;
    }

    public static Resp fail(String msg) {
        Resp resp = new Resp();
        resp.retCode = "ff";
        resp.retMsg = msg;
        return resp;
    }

}
