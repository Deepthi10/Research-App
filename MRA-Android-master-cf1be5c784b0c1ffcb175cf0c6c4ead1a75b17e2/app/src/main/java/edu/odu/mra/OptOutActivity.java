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

import com.google.android.gms.common.api.GoogleApiClient;

import org.codehaus.jackson.map.ObjectMapper;

import edu.odu.mra.constants.MRAApplication;
import edu.odu.mra.constants.WebServices;
import edu.odu.mra.models.EnrollResponse;
import edu.odu.mra.utility.JsonWebServiceCaller;

/**
 * Created by kahmed on 11/8/2015.
 */
public class OptOutActivity extends AppCompatActivity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opt_out);
        showActionBar();
        Button btnOptOut = (Button) findViewById(R.id.btnOptOutSurvey);
        btnOptOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OptOutActivity.this);
                builder.setMessage("You have successfully withdrawn from this Survey. You will no longer receive push notifications for this survey!")
                        .setTitle("Alert");
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(OptOutActivity.this, "", Toast.LENGTH_SHORT).show();
                        new MatchingTask().execute(getIntent().getExtras().getString("surveyid"));
                        startActivity(new Intent(OptOutActivity.this, SurveysActivity.class));
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
        String cameback = "CameBack";
        Intent intent = new Intent(OptOutActivity.this, SurveysActivity.class);
        intent.putExtra("Comingback", cameback);
        System.out.println("back" + cameback);
        startActivity(intent);

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



    class MatchingTask extends AsyncTask<String, Integer, String> {
        private ProgressDialog dialog = new ProgressDialog(OptOutActivity.this);

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Enrolling...");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            final MRAApplication application = (MRAApplication) getApplicationContext();
            String urlParameters = "survey_id=" + params[0] + "&email=" + application.getEmail();
            return JsonWebServiceCaller.call(WebServices.OPTOUT, urlParameters);
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                System.out.println("Response from Url: " + result);
                ObjectMapper mapper = new ObjectMapper();
                final EnrollResponse enrollResponse = mapper.readValue(result, EnrollResponse.class);
                if (enrollResponse.status.equals("1")) {
                    dialog.cancel();
                    //Toast.makeText(getApplicationContext(), enrollResponse.getMsg(), Toast.LENGTH_LONG).show();
                    startActivity(new Intent(OptOutActivity.this, SurveysActivity.class));

                } else {
                    dialog.cancel();
                    //Toast.makeText(getApplicationContext(), enrollResponse.getMsg(), Toast.LENGTH_LONG).show();
                }

            } catch (Exception exception) {
                exception.printStackTrace();
            }

        }

    }
}
