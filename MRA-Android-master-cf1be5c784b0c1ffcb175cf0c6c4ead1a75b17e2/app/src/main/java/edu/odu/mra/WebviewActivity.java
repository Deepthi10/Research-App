package edu.odu.mra;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.odu.mra.models.Timestamp;

public class WebviewActivity extends AppCompatActivity {
    WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
        Date currentDateandTime=new Date();
        String ts=getIntent().getExtras().getString("timestamp");
        ObjectMapper mapper=new ObjectMapper();
        Timestamp timestamp= null;
        try {
            timestamp = (Timestamp)mapper.readValue(ts, Timestamp.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Response from timestamp webviewactivity..;....;.... : " + timestamp);
        Date d1= null;
        try {
            d1 = sdf.parse(timestamp.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long diff= (currentDateandTime.getTime()-d1.getTime())/(60*60*1000);

        if(diff>=1)
        {
            Toast.makeText(getApplicationContext(),"Notification expired!!",Toast.LENGTH_LONG).show();
        }
        else
        {
            wv = (WebView) findViewById(R.id.webViewmra);
            wv.setWebViewClient(new WebViewClient());
            String url=getIntent().getExtras().getString("url");
            wv.loadUrl(url);
        }

        System.out.println("Response from currenttime is..;....;.... : " + diff);
       showActionBar();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainn, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mradone:
                startActivity(new Intent(WebviewActivity.this, SurveysActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void showActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#003258")));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(getLayoutInflater().inflate(R.layout.actionbar_mradone, null),
                new ActionBar.LayoutParams(
                        ActionBar.LayoutParams.WRAP_CONTENT,
                        ActionBar.LayoutParams.MATCH_PARENT,
                        Gravity.CENTER
                )
        );
        actionBar.setTitle("Survey Info");
    }
}
