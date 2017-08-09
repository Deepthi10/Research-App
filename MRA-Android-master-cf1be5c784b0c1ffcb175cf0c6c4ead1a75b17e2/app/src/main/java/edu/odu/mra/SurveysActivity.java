package edu.odu.mra;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.codehaus.jackson.map.ObjectMapper;

import java.util.List;

import edu.odu.mra.constants.MRAApplication;
import edu.odu.mra.constants.WebServices;
import edu.odu.mra.models.SurveyDetails;
import edu.odu.mra.models.SurveyListResponse;
import edu.odu.mra.utility.JsonWebServiceCaller;
import edu.odu.mra.utility.SurveyListAdapter;

/**
 * Created by kahmed on 11/8/2015.
 */
public class SurveysActivity extends AppCompatActivity {

    ListView lvSurveyList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.surveys);
        lvSurveyList = (ListView) findViewById(R.id.lvSurveys);
        showActionBar();
        new MatchingTask().execute("");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
         moveTaskToBack(true);
        final MRAApplication application =(MRAApplication) getApplicationContext();
        Intent intent = new Intent(SurveysActivity.this, WelcomeActivity.class);
        intent.putExtra("email",application.getEmail());
        intent.putExtra("uname", application.getName());
        startActivity(intent);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                startActivity(new Intent(SurveysActivity.this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showActionBar() {
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
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
        actionBar.setTitle("Surveys");
    }



    class MatchingTask extends AsyncTask<String, Integer, String>  {
        private ProgressDialog dialog = new ProgressDialog(SurveysActivity.this);

        @Override
        protected void onPreExecute() {
            dialog.setMessage("loading surveys...");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            final MRAApplication application = (MRAApplication) getApplicationContext();
            String urlParameters = "email=" + application.getEmail();
            return JsonWebServiceCaller.call(WebServices.USERSURVEYS, urlParameters);

        }


        @Override
        protected void onPostExecute(String result) {
            try {
                System.out.println("Response from Url: " + result);
                ObjectMapper mapper = new ObjectMapper();
                final SurveyListResponse surveyListResponse = mapper.readValue(result, SurveyListResponse.class);
                List<SurveyDetails> surveyDetails = surveyListResponse.getSurveys();
                SurveyListAdapter adapter = new SurveyListAdapter(SurveysActivity.this, surveyDetails);
                lvSurveyList.setAdapter(adapter);
                dialog.cancel();
                if(lvSurveyList!=null) {
                    try {
                        lvSurveyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(SurveysActivity.this, SurveyInfoActivity.class);
                                intent.putExtra("surveyDetails", surveyListResponse.getSurveys().get(position));
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(intent);
                            }
                        });
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        Log.d(e.getLocalizedMessage(),"Error");
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Null Issue", Toast.LENGTH_LONG).show();
                }

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }


    }

}
