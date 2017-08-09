package edu.odu.mra;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.odu.mra.models.SurveyDetails;

/**
 * Created by kahmed on 11/8/2015.
 */
public class SurveyInfoActivity extends AppCompatActivity{

    public SurveyDetails details;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey_info);


        TextView title=(TextView)findViewById(R.id.tvSurveyInfoTitle);
        TextView surveyStartDate=(TextView)findViewById(R.id.tvSurveyInfoStartDate);
        TextView surveyEndDate=(TextView)findViewById(R.id.tvSurveyInfoEndDateTitle);
        TextView summary=(TextView)findViewById(R.id.tvSurveyInfoSummary);
        TextView freq=(TextView)findViewById(R.id.tvSurveyInfoNotificationFrequency);

        Button btnEnroll = (Button) findViewById(R.id.btnSurveyInfoEnroll);
        Button btnSurveyOptOut = (Button) findViewById(R.id.btnSurveyOptOut);
        Button btnInfo= (Button) findViewById(R.id.btnSurveyInfo);

            try {
                details =(SurveyDetails)getIntent().getSerializableExtra("surveyDetails");
                title.setText(details.getTitle());
                surveyStartDate.setText("Survey Start Date : "+details.getStartDate());
                surveyEndDate.setText("Survey End Date : "+details.getEndDate());
                summary.setText("Summary: "+ details.getDescription());
                freq.setText("Notification Frequency: "+details.getFrequency());


                if(details.getEnrolled().toLowerCase().equals("yes"))
                {
                    btnEnroll.setVisibility(View.INVISIBLE);
                    btnSurveyOptOut.setVisibility(View.VISIBLE);
                }
                else
                {
                    btnEnroll.setVisibility(View.VISIBLE);
                    btnSurveyOptOut.setVisibility(View.GONE);
                }

                System.out.println("Title:" + details.getTitle());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


        btnEnroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SurveyInfoActivity.this, ConsentActivity.class);
                intent.putExtra("url", details.getConsent_form_url());
                intent.putExtra("surveyid", details.getId());
                //intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });


        btnSurveyOptOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SurveyInfoActivity.this, OptOutActivity.class);
                intent.putExtra("surveyid", details.getId());
                //intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SurveyInfoActivity.this, InfoActivity.class);
                intent.putExtra("surveyDetails", details);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                System.out.println("details" + details.getStartDate());
                startActivity(intent);

            }
        });

        showActionBar();
    }

    private void showActionBar() {
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#003258")));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        actionBar.setCustomView(getLayoutInflater().inflate(R.layout.actionbar_mra, null),
                new ActionBar.LayoutParams(
                        ActionBar.LayoutParams.WRAP_CONTENT,
                        ActionBar.LayoutParams.MATCH_PARENT,
                        Gravity.CENTER

                )
        );
        actionBar.setTitle("Surveys");
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        //Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
//        String cameback = "CameBack";
//        Intent intent = new Intent(SurveyInfoActivity.this, SurveysActivity.class);
//        intent.putExtra("Comingback", cameback);
//        System.out.println("back" + cameback);
//        startActivity(intent);
//
//    }

}
