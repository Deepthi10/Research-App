package edu.odu.mra.gcm;

import java.io.Serializable;

/**
 * Created by Kevin Racheal on 5/10/2016.
 */
public class NotificationDetail implements Serializable {
  public String[] registration_ids;

    public String[] getRegistration_ids() {
        return registration_ids;
    }

    public void setRegistration_ids(String[] registration_ids) {
        this.registration_ids = registration_ids;
    }

    public static class Notification {
        String body,title;

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class Data{
        String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

}
