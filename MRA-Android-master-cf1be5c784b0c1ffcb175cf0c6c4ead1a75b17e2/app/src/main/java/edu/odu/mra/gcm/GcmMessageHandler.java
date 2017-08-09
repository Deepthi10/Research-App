package edu.odu.mra.gcm;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.codehaus.jackson.map.ObjectMapper;

import edu.odu.mra.R;
import edu.odu.mra.WebviewActivity;

/**
 * Created by kkallepalli on 2/14/2016.
 */
public class GcmMessageHandler extends IntentService {

    String mes;
    private Handler handler;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    String serverTime;
    public GcmMessageHandler() {
        super("GcmMessageHandler");
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        handler = new Handler();
    }
    public static final String TAG = "GCMNotificationIntentService";
    @Override
    protected void onHandleIntent(Intent intent) {
       Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
       String messageType = gcm.getMessageType(intent);
        try{
            mes = extras.getString("url");


            //Toast.makeText(getApplicationContext(), mes, Toast.LENGTH_LONG).show();

            ObjectMapper mapper= new ObjectMapper();

            if(extras.getString("title")!=null) {
                NotificationCompat.Builder mbuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_play_dark)
                        .setContentTitle(extras.getString("title"))
                        .setContentText(extras.getString("body"));

                Intent intent1 = new Intent(GcmMessageHandler.this, WebviewActivity.class);
                intent1.putExtra("url", extras.getString("url"));
                intent1.putExtra("title", extras.getString("title"));
                intent1.putExtra("body", extras.getString("body"));
                intent1.putExtra("timestamp", extras.getString("timestamp"));
                System.out.println("Response from timestamp..;....;.... : " + extras.getString("timestamp"));
       // String timestamp={"date":"2016-05-20 12:11:18.000000","timezone":"America\/New_York","timezone_type":3};

                int requestID = (int) System.currentTimeMillis();
                long when=System.currentTimeMillis();
                int icon=R.drawable.ic_play_dark;

                PendingIntent pendingIntent = PendingIntent.getActivity(this,requestID, intent1, 0);
                mbuilder.setContentIntent(pendingIntent);

                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notification=mbuilder.build();
                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                // mId allows you to update the notification later on.
                mNotificationManager.notify(12325, notification);

                //Notification notification = new Notification(icon, mes, when);

            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
      //showToast();
       System.out.println("GCM Received : (" + messageType + ")  " + extras.toString());

       GcmBroadcastReceiver.completeWakefulIntent(intent);



    }

    public void showToast(){
        handler.post(new Runnable() {
            public void run() {
                System.out.println("Handler show Toast");

                Toast.makeText(getApplicationContext(), mes, Toast.LENGTH_LONG).show();
            }
        });

    }


}
