package edu.odu.mra.models;

import java.io.Serializable;

/**
 * Created by Kevin Racheal on 5/18/2016.
 */
public class CoPiDetails implements Serializable{

    private String co_pi;
    private String co_pi_email;
    private String co_pi_phone;
    private String co_pi_dept;

    public String getCo_pi() {
        return co_pi;
    }

    public void setCo_pi(String co_pi) {
        this.co_pi = co_pi;
    }

    public String getCo_pi_email() {
        return co_pi_email;
    }

    public void setCo_pi_email(String co_pi_email) {
        this.co_pi_email = co_pi_email;
    }

    public String getCo_pi_phone() {
        return co_pi_phone;
    }

    public void setCo_pi_phone(String co_pi_phone) {
        this.co_pi_phone = co_pi_phone;
    }

    public String getCo_pi_dept() {
        return co_pi_dept;
    }

    public void setCo_pi_dept(String co_pi_dept) {
        this.co_pi_dept = co_pi_dept;
    }

}
