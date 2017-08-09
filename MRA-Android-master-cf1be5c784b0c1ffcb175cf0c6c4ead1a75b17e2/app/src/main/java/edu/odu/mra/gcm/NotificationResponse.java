package edu.odu.mra.gcm;

import edu.odu.mra.gcm.NotificationDetail;

/**
 * Created by Kevin Racheal on 5/10/2016.
 */
public class NotificationResponse {
    public NotificationDetail[]registration_ids;
    public NotificationDetail.Notification notification;
    public NotificationDetail.Data data;

    public NotificationDetail[] getRegistration_ids() {
        return registration_ids;
    }

    public void setRegistration_ids(NotificationDetail[] registration_ids) {
        this.registration_ids = registration_ids;
    }

    public NotificationDetail.Notification getNotification() {
        return notification;
    }

    public void setNotification(NotificationDetail.Notification notification) {
        this.notification = notification;
    }

    public NotificationDetail.Data getData() {
        return data;
    }

    public void setData(NotificationDetail.Data data) {
        this.data = data;
    }
}

