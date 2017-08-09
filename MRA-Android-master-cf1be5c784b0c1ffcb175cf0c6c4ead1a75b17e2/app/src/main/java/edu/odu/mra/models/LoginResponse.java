package edu.odu.mra.models;

/**
 * Created by kkallepalli on 2/17/2016.
 */
public class LoginResponse {

    public String status;
    public String msg;
    public User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
