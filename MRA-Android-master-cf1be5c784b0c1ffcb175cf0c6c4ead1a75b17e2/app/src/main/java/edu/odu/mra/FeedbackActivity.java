package edu.odu.mra;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.codehaus.jackson.map.ObjectMapper;

import edu.odu.mra.constants.MRAApplication;
import edu.odu.mra.constants.WebServices;
import edu.odu.mra.models.RegisterResponse;
import edu.odu.mra.utility.JsonWebServiceCaller;

public class FeedbackActivity extends AppCompatActivity {

    EditText content;
    Button sbmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        content=(EditText)findViewById(R.id.feedBackText);
        sbmt=(Button)findViewById(R.id.fbackSbmt);

        sbmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MRAApplication application= (MRAApplication) getApplicationContext();
                new MatchingTask().execute(content.getText().toString(), application.getEmail());
                AlertDialog.Builder builder = new AlertDialog.Builder(FeedbackActivity.this);
                builder.setTitle("Success");
                builder.setMessage("Feedback is recorded");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Toast.makeText(MainActivity.this, "", Toast.LENGTH_LONG);
                        Intent intent2 =new Intent(FeedbackActivity.this, WelcomeActivity.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent2);
                    }
                });
                builder.show();
                Toast.makeText(getApplicationContext(),"Thanks for submitting the feedback", Toast.LENGTH_LONG).show();
            }
        });
        showActionBar();
    }

    private void showActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#003258")));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        View view=getLayoutInflater().inflate(R.layout.action_bar, null);
        TextView tv=(TextView)view.findViewById(R.id.tvActionBarTitle);
        tv.setText("MRA - Feedback");
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
        final MRAApplication application =(MRAApplication) getApplicationContext();
        Intent intent = new Intent(FeedbackActivity.this, WelcomeActivity.class);
        intent.putExtra("email",application.getEmail());
        intent.putExtra("uname", application.getName());
        startActivity(intent);

    }

    class MatchingTask extends AsyncTask<String, Integer, String> {

        private ProgressDialog dialog=new ProgressDialog(FeedbackActivity.this);

        @Override
        protected void onPreExecute() {
            dialog.setMessage("loading...");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String urlParameters = "feedback="+params[0]+"&email="+params[1];
            System.out.println("Request Feedback..."+urlParameters);
            return JsonWebServiceCaller.call(WebServices.FEEDBACK, urlParameters);
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                dialog.cancel();
                ObjectMapper objectMapper=new ObjectMapper();
                RegisterResponse response=objectMapper.readValue(result,RegisterResponse.class);
                //Toast.makeText(getApplicationContext(),response.getMsg(),Toast.LENGTH_LONG).show();
                sbmt.setEnabled(false);

            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }

        }

    }
}