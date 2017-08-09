package edu.odu.mra.models;

import java.io.Serializable;

/**
 * Created by Kevin Racheal on 5/20/2016.
 */
public class PiDetails implements Serializable {
    private String pi;
    private String pi_email;
    private String pi_phone;
    private String pi_dept;

    public String getPi() {
        return pi;
    }

    public void setPi(String pi) {
        this.pi = pi;
    }

    public String getPi_email() {
        return pi_email;
    }

    public void setPi_email(String pi_email) {
        this.pi_email = pi_email;
    }

    public String getPi_phone() {
        return pi_phone;
    }

    public void setPi_phone(String pi_phone) {
        this.pi_phone = pi_phone;
    }

    public String getPi_dept() {
        return pi_dept;
    }

    public void setPi_dept(String pi_dept) {
        this.pi_dept = pi_dept;
    }
}
