package edu.odu.mra;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import edu.odu.mra.constants.WebServices;
import edu.odu.mra.utility.JsonWebServiceCaller;

public class ForgotPwdActivity extends AppCompatActivity {

    EditText emailid;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pwd);
        emailid = (EditText) findViewById(R.id.emailfrgtpwd);
        submit = (Button) findViewById(R.id.buttonemailid);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(emailid.getText().toString().trim())) {
                    emailid.setError("Please enter the id");
                } else {
                    new ForgotpwdTask().execute(emailid.getText().toString());
                    submit.setEnabled(false);
                    submit.setClickable(false);
                    Intent intent = new Intent(ForgotPwdActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });

        showActionBar();
    }


    class ForgotpwdTask extends AsyncTask<String, Integer, String> {
        private ProgressDialog dialog = new ProgressDialog(ForgotPwdActivity.this);

        @Override
        protected void onPreExecute() {
            dialog.setMessage("loading matches...");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String urlParameters = "email=" + params[0];
            return JsonWebServiceCaller.call(WebServices.FORGETPASSWORD, urlParameters);
        }

        @Override
        protected void onPostExecute(String result) {

            try{
                dialog.cancel();
                System.out.println("Response from url"+result);


            }
            catch (Exception exception){
                exception.printStackTrace();
            }

        }

    }

    private void showActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#003258")));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        View view=getLayoutInflater().inflate(R.layout.action_bar, null);
        TextView tv=(TextView)view.findViewById(R.id.tvActionBarTitle);
        tv.setText("MRA");
        actionBar.setCustomView(view,
                new ActionBar.LayoutParams(
                        ActionBar.LayoutParams.WRAP_CONTENT,
                        ActionBar.LayoutParams.MATCH_PARENT,
                        Gravity.CENTER
                )
        );
    }
    @Override
   public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ForgotPwdActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

   }


}
