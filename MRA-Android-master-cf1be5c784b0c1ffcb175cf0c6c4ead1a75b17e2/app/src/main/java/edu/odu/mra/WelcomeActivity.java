package edu.odu.mra;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.codehaus.jackson.map.ObjectMapper;

import edu.odu.mra.constants.MRAApplication;
import edu.odu.mra.models.LoginResponse;

/**
 * Created by kahmed on 11/8/2015.
 */
public class WelcomeActivity extends AppCompatActivity {

    RelativeLayout settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);
        String response;
        LoginResponse loginResponse=null;

        final MRAApplication application =(MRAApplication) getApplicationContext();

        Bundle extras=getIntent().getExtras();
        if(extras!=null)
        {
            try {
                if(extras.getString("uname")==null) {
                    response = extras.getString("userdetails");
                    ObjectMapper mapper = new ObjectMapper();
                    loginResponse = mapper.readValue(response, LoginResponse.class);
                    if(loginResponse!=null)
                    {
                        TextView name=(TextView)findViewById(R.id.tvWelcomeName);
                        name.setText(application.getName());
                        //name.setText(loginResponse.getUser().getFirstname() + " " + loginResponse.getUser().getLastname());
                        System.out.println("Response from nameapplication: " + application.getName());
                        TextView email=(TextView)findViewById(R.id.tvWelcomeEmail);
                        email.setText(application.getEmail());
                        System.out.println("Response from emailapplication: " + application.getEmail());
                    }
                }
                else
                {
                    TextView name=(TextView)findViewById(R.id.tvWelcomeName);
                    name.setText(extras.getString("uname"));
                    //name.setText(loginResponse.getUser().getFirstname() + " " + loginResponse.getUser().getLastname());
                    System.out.println("Response from nameapplication: " + application.getName());
                    TextView email=(TextView)findViewById(R.id.tvWelcomeEmail);
                    email.setText(extras.getString("email"));
                    System.out.println("Response from emailapplication: " + application.getEmail());
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }


       final  RelativeLayout rlSurveys = (RelativeLayout) findViewById(R.id.rlWelcomeViewSurveys);
        rlSurveys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent.putExtra("userdetails",loginResponse.getUser().getFirstname());
              startActivity(new Intent(WelcomeActivity.this, SurveysActivity.class));
                /*Intent intent = new Intent(WelcomeActivity.this, SurveysActivity.class);
                WelcomeActivity.this.startActivity(intent);*/
            }
        });

        settings=(RelativeLayout)findViewById(R.id.rlWelcomeSettings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
            }
        });

        RelativeLayout feedlayout= (RelativeLayout) findViewById(R.id.rlFeedback);
        TextView feedback= (TextView) findViewById(R.id.tvFeedback);
        feedlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this,FeedbackActivity.class));
            }
        });
        showActionBar();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        setContentView(R.layout.welcome_screen);
        String response;
        LoginResponse loginResponse=null;

        final MRAApplication application =(MRAApplication) getApplicationContext();

        Bundle extras=getIntent().getExtras();
        if(extras!=null)
        {
            try {
                response = extras.getString("userdetails");
                ObjectMapper mapper = new ObjectMapper();
                loginResponse = mapper.readValue(response, LoginResponse.class);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        if(loginResponse!=null)
        {
            TextView name=(TextView)findViewById(R.id.tvWelcomeName);
            name.setText(application.getName());
            //name.setText(loginResponse.getUser().getFirstname() + " " + loginResponse.getUser().getLastname());
            System.out.println("Response from nameapplication: " + application.getName());
            TextView email=(TextView)findViewById(R.id.tvWelcomeEmail);
            email.setText(application.getEmail());
            System.out.println("Response from emailapplication: " + application.getEmail());

        }


        final  RelativeLayout rlSurveys = (RelativeLayout) findViewById(R.id.rlWelcomeViewSurveys);
        rlSurveys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //intent.putExtra("userdetails",loginResponse.getUser().getFirstname());
                Intent intent = new Intent(WelcomeActivity.this, SurveysActivity.class);
                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                //WelcomeActivity.this.startActivity(intent);
            }
        });

        settings=(RelativeLayout)findViewById(R.id.rlWelcomeSettings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
            }
        });

        RelativeLayout feedlayout= (RelativeLayout) findViewById(R.id.rlFeedback);
        TextView feedback= (TextView) findViewById(R.id.tvFeedback);
        feedlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this,FeedbackActivity.class));
            }
        });
        showActionBar();
    }
   /* @Override
    public void finishActivity(int requestCode) {
        super.finishActivity(requestCode);
    }*/

    @Override
    public void onBackPressed() {
       AlertDialog.Builder builder = new AlertDialog.Builder(this);
       builder.setTitle("Confirm");
       builder.setMessage("Do you want to exit the app? ");
       builder.setPositiveButton("Stay", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {

               //Toast.makeText(WelcomeActivity.this, "", Toast.LENGTH_LONG);
           }
       });
       builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               ActivityCompat.finishAffinity(WelcomeActivity.this);
               android.os.Process.killProcess(android.os.Process.myPid());
           }
       });
       builder.show();
   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        /*Drawable drawable = menu.findItem(R.drawable.exit).getIcon();
        if (drawable != null) {
            drawable.mutate();
            drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        }*/
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
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
        actionBar.setCustomView(getLayoutInflater().inflate(R.layout.action_bar, null),
                new ActionBar.LayoutParams(
                        ActionBar.LayoutParams.WRAP_CONTENT,
                        ActionBar.LayoutParams.MATCH_PARENT,
                        Gravity.CENTER
                )
        );
        actionBar.setTitle("Survey Info");
    }
}
