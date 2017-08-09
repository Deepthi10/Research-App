package edu.odu.mra.models;

/**
 * Created by kkallepalli on 3/16/2016.
 */
public class RegisterResponse {
    public String status;
    public String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
