package edu.odu.mra.models;

/**
 * Created by kkallepalli on 2/17/2016.
 */

public class User
{
    public String email;
    public String firstname;;
    public String lastname;
    public String demoUpdate;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDemoUpdate() {
        return demoUpdate;
    }

    public void setDemoUpdate(String demoUpdate) {
        this.demoUpdate = demoUpdate;
    }
}