package edu.odu.mra;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import edu.odu.mra.models.CoPiDetails;
import edu.odu.mra.models.PiDetails;
import edu.odu.mra.models.SurveyDetails;
import edu.odu.mra.utility.InfoListAdapter;
import edu.odu.mra.utility.InfoPiAdapter;

public class InfoActivity extends AppCompatActivity {

    public SurveyDetails details;
    ListView listviewcopi;
    ListView listviewpi;
    InfoListAdapter infoListAdapteradapter;
    InfoPiAdapter infoPiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);


        TextView tv1 = (TextView) findViewById(R.id.startdate);
        TextView tv2 = (TextView) findViewById(R.id.enddate);

        listviewcopi = (ListView) findViewById(R.id.copilist);
        listviewpi=(ListView)findViewById(R.id.piListview);

        TextView title = (TextView) findViewById(R.id.additionalinfo);
        TableRow copitable=(TableRow)findViewById(R.id.copitablerow);
        //TextView tv9= (TextView) findViewById(R.id.irbnum);

        try {

            details = (SurveyDetails) getIntent().getSerializableExtra("surveyDetails");
            title.setText(details.getTitle());
            tv1.setText(details.getStartDate());
            tv2.setText(details.getEndDate());
           /* tv3.setText(details.getPi());
            tv4.setText(details.getPi_phone());
            tv5.setText(details.getPi_dept());*/
           /* if (details.getCo_pi_details().size() >= 1) {
                tv6.setText(details.getCo_pi_details().get(0).getCo_pi());
                tv7.setText(details.getCo_pi_details().get(0).getCo_pi_phone());
                tv8.setText(details.getCo_pi_details().get(0).getCo_pi_dept());
            }*/
            //tv9.setText("IRB number"+details.get)

            List<PiDetails> pi_details=details.getPi_details();
             infoPiAdapter=new InfoPiAdapter(InfoActivity.this,pi_details);
            listviewpi.setAdapter(infoPiAdapter);

            List<CoPiDetails> copiDetails = details.getCo_pi_details();
             infoListAdapteradapter = new InfoListAdapter(InfoActivity.this, copiDetails);
            listviewcopi.setAdapter(infoListAdapteradapter);

            listviewpi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    PiDetails piDetails=(PiDetails)infoPiAdapter.getItem(position);
                    Intent piIntent=new Intent(Intent.ACTION_CALL);
                    piIntent.setData(Uri.parse("pi_phone" + piDetails.getPi_phone()));
                    Toast.makeText(getApplicationContext(), "Clicked---" + piDetails.getPi_phone(), Toast.LENGTH_LONG).show();
                    startActivity(piIntent);
                    Intent email = new Intent(Intent.ACTION_SEND);
                    email.setData(Uri.parse("pi_email" + piDetails.getPi_email()));
                    startActivity(email);
                }
            });


            listviewcopi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    CoPiDetails cDetails = (CoPiDetails) infoListAdapteradapter.getItem(position);
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("co_pi_phone" + cDetails.getCo_pi_phone()));
                    startActivity(callIntent);
                    Intent emailcopi=  new Intent(Intent.ACTION_SEND);
                    emailcopi.setData(Uri.parse("co_pi_email" + cDetails.getCo_pi_email()));
                    startActivity(emailcopi);
                }
            });




            if(details.getCo_pi_details().isEmpty()){
                copitable.setVisibility(View.INVISIBLE);
                copitable.setVisibility(View.GONE);
            }
            else{
                copitable.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        showActionBar();
    }

  /* @Override
    public void onListItemClick(ListView list, View v, int position, long id) {
        PiDetails pDetails = (PiDetails) infoPiAdapter.getItem(position);
        Toast.makeText(getApplicationContext(),"Clicked---"+pDetails.getPi_phone(), Toast.LENGTH_LONG).show();
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("pi_phone" + pDetails.getPi_phone()));
        startActivity(callIntent);
    }*/

    private void showActionBar() {
        ActionBar actionBar = getSupportActionBar();
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
        actionBar.setTitle("Survey Info");
    }

        @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
            String cameback = "CameBack";
            Intent intent = new Intent(InfoActivity.this, SurveyInfoActivity.class);
            intent.putExtra("surveyDetails", details);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            System.out.println("back" + cameback);
            startActivity(intent);

    }



}
