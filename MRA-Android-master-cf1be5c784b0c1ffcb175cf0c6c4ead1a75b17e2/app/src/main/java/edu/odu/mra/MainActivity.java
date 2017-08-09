package edu.odu.mra;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

import edu.odu.mra.constants.MRAApplication;
import edu.odu.mra.constants.WebServices;
import edu.odu.mra.models.LoginResponse;
import edu.odu.mra.utility.JsonWebServiceCaller;

/**
 * Created by kahmed on 11/8/2015.
 */
public class MainActivity extends Activity {
    String project_number="514654416167";
    GoogleCloudMessaging gcm;
    String regid;

    Button mLoginBtn;
    EditText username;
    EditText password;
    Button register;
    TextView Forgotpwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoginBtn = (Button) findViewById(R.id.btnLogin);
        username = (EditText) findViewById(R.id.etUsername);
        password = (EditText) findViewById(R.id.etPassword);

        register=(Button)findViewById(R.id.btnRegister);
        Forgotpwd= (TextView) findViewById(R.id.textViewFrgtpwd);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),RegistrationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(username.getText().toString().trim())) {
                    username.setError("Please enter email");
                } else if (TextUtils.isEmpty(password.getText().toString().trim())) {
                    password.setError("Please enter password");
                } else {
                    getRegId();

                }
            }
        });

        Forgotpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in= new Intent(getApplicationContext(),ForgotPwdActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(in);
            }
        });

    }
    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Do you want to exit the app? ");
        builder.setPositiveButton("Stay", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

               // Toast.makeText(MainActivity.this, "", Toast.LENGTH_LONG);
            }
        });
        builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.finishAffinity(MainActivity.this);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        builder.show();
    }

    class MatchingTask extends AsyncTask<String, Integer, String>{

        private ProgressDialog dialog=new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            dialog.setMessage("loading...");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String urlParameters = "pwd="+params[1]+"&email="+params[0]+"&devicetoken="+params[2]+"&devicetype=android";
            System.out.println("Response..."+urlParameters);
            return JsonWebServiceCaller.call(WebServices.LOGIN, urlParameters);
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                System.out.println("Response from Url: " + result);
                ObjectMapper mapper = new ObjectMapper();
                LoginResponse loginResponse = mapper.readValue(result, LoginResponse.class);

                if (loginResponse.getStatus().equals("1")&&loginResponse.getUser().getDemoUpdate().equals("Yes")) {
                    Intent intentdemo = new Intent(MainActivity.this, DemoUpdate.class);
                    startActivity(intentdemo);
                }
                   else if (loginResponse.getStatus().equals("1") &&loginResponse.getUser().getDemoUpdate().equals("No")) {
                        Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                        final MRAApplication application = (MRAApplication) getApplicationContext();
                        application.setEmail(loginResponse.getUser().getEmail());
                        application.setName(loginResponse.getUser().getFirstname() + " " + loginResponse.getUser().getLastname());
                        System.out.println("Response from application: " + application.getName());
                        intent.putExtra("userdetails", result);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                    } else if (loginResponse.getStatus().equals("2")) {
                        dialog.cancel();
                        //Toast.makeText(getApplicationContext(), loginResponse.getMsg(), Toast.LENGTH_LONG).show();
                    } else if (loginResponse.getStatus().equals("3")) {
                        dialog.cancel();
                        // Toast.makeText(getApplicationContext(),loginResponse.getMsg(),Toast.LENGTH_LONG).show();
                    } else if (loginResponse.getStatus().equals("4")) {
                        dialog.cancel();
                        // Toast.makeText(getApplicationContext(),loginResponse.getMsg(),Toast.LENGTH_LONG).show();
                    }
                }

            catch (Exception exception)
            {
                exception.printStackTrace();
            }

        }

    }

    public void getRegId(){
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
                    }
                    regid = gcm.register(project_number);
                    msg = "Device registered, registration ID=" + regid;
                    System.out.println(msg);

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();

                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                new MatchingTask().execute(username.getText().toString().trim(), password.getText().toString(), regid);
               // Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
            }
        }.execute(null, null, null);
    }
}
