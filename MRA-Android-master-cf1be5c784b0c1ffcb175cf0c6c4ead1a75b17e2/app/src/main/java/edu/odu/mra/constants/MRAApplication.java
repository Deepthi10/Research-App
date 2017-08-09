package edu.odu.mra.constants;

import android.app.Application;

/**
 * Created by kkallepalli on 3/18/2016.
 */
public class MRAApplication extends Application {

    private String email;
    private String name;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
