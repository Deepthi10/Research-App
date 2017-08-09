package edu.odu.mra;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import edu.odu.mra.constants.MRAApplication;
import edu.odu.mra.constants.WebServices;
import edu.odu.mra.models.DemoQuestionsResponse;
import edu.odu.mra.utility.JsonWebServiceCaller;

public class DemoUpdate extends AppCompatActivity {

    Button submitdemo;
    Button canceldemo;
    Spinner genderdemo;
    Spinner maritalstatusdemo;
    Spinner racespinnerdemo;
    Spinner educationspinnerdemo;
    TextView fnametitle;
    TextView lnametitle;
    TextView Email;
    EditText fname;
    EditText lname;
    EditText email;
    private Map<String,String> genderMapDemo=new HashMap<String,String>();
    private Map<String,String> raceMapDemo=new HashMap<String,String>();
    private Map<String,String>maritalstatusMapDemo= new HashMap<String,String>();
    private Map<String,String>educationMapDemo=new HashMap<String,String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_update);



        fnametitle=(TextView)findViewById(R.id.fNameTitledemo);
        lnametitle=(TextView)findViewById(R.id.lNameTitledemo);
        Email=(TextView)findViewById(R.id.emaildemoupadate);


        fname=(EditText)findViewById(R.id.firstNamedemo);
        lname=(EditText)findViewById(R.id.lastNamedemo);
        email=(EditText)findViewById(R.id.edemoupdate);



        genderMapDemo.put("Select", "SELECT");
        genderMapDemo.put("Male", "MALE");
        genderMapDemo.put("Female", "FEMA");
        genderMapDemo.put("Not Listed","NOT LISTED");
        genderMapDemo.put("No Response","NO RESPONSE");

        raceMapDemo.put("Select","SELECT");
        raceMapDemo.put("Hispanic/Latino","HISP");
        raceMapDemo.put("American Indian/Eskimo","AIAN");
        raceMapDemo.put("Asian/Pacific Islander","ASIAN");
        raceMapDemo.put("Black/African American","BLACK");
        raceMapDemo.put("Native Hawaiian/Other Pacific Islander","HAWA");
        raceMapDemo.put("Caucasian","WHITE");
        raceMapDemo.put("Multiracial","MURA");
        raceMapDemo.put("No Response","NR");

        educationMapDemo.put("Select","SELECT");
        educationMapDemo.put("Doctoral","DOC");
        educationMapDemo.put("Masters","MAS");
        educationMapDemo.put("Bachelors","BAC");
        educationMapDemo.put("Associate","ASO");
        educationMapDemo.put("High School Diploma","HSD");
        educationMapDemo.put("GED","GED");
        educationMapDemo.put("No Response","NR");

        maritalstatusMapDemo.put("Select","SELECT");
        maritalstatusMapDemo.put("Single","SING");
        maritalstatusMapDemo.put("Married","MARR");
        maritalstatusMapDemo.put("Divorced","DIVO");
        maritalstatusMapDemo.put("Separated","SEPA");
        maritalstatusMapDemo.put("Widowed","WIDO");
        maritalstatusMapDemo.put("No Response","NR");

        submitdemo=(Button)findViewById(R.id.Submitdemobutton);
        canceldemo=(Button)findViewById(R.id.canceldemobutton);
        genderdemo=(Spinner)findViewById(R.id.genderSpinnerdemo);
        maritalstatusdemo=(Spinner)findViewById(R.id.maritalDropdowndemo);
        racespinnerdemo=(Spinner)findViewById(R.id.raceDropdowndemo);
        educationspinnerdemo=(Spinner)findViewById(R.id.educationDropdowndemo);

       /* genderdemo=(Spinner)findViewById(R.id.genderSpinnerdemo);
        maritalstatusdemo=(Spinner)findViewById(R.id.marita) */

        canceldemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newintent= new Intent(DemoUpdate.this,WelcomeActivity.class);
                startActivity(newintent);
            }
        });

        submitdemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(fname.getText().toString().trim())) {
                    fname.setError("Please enter First Name");
                }
                else if(TextUtils.isEmpty(lname.getText().toString().trim())){
                   lname.setError("Please eneter last name");
                }
                else if(genderdemo.getSelectedItem().equals("Select")||maritalstatusdemo.getSelectedItem().equals("Select")||racespinnerdemo.getSelectedItem().equals("Select")
                        ||educationspinnerdemo.getSelectedItem().equals("Select")){
                    Toast.makeText(getApplicationContext(), "Please choose an option", Toast.LENGTH_LONG).show();
                }
                else  {
                    new DemoTask().execute(email.getText().toString(),fname.getText().toString(),lname.getText().toString(),genderdemo.getSelectedItem().toString(),
                            racespinnerdemo.getSelectedItem().toString(),maritalstatusdemo.getSelectedItem().toString(),educationspinnerdemo.getSelectedItem().toString());
                    Toast.makeText(getApplicationContext(), "Thanks for updating", Toast.LENGTH_LONG).show();
                    Intent in = new Intent(DemoUpdate.this, WelcomeActivity.class);
                    startActivity(in);
                }
            }
        });
    }

    class DemoTask extends AsyncTask<String, Integer, String> {
        private ProgressDialog dialog = new ProgressDialog(DemoUpdate.this);

        @Override
        protected void onPreExecute() {
            dialog.setMessage("loading surveys...");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            final MRAApplication application = (MRAApplication) getApplicationContext();
            String urlParameters = "email="+params[0]+"firstname="+params[1]+"&lastname="+params[2]+"&gender="+params[3]+"&race="+params[4]
                    +"&maritalstatus="+params[5]+"&education="+params[6];
            return JsonWebServiceCaller.call(WebServices.DEMOUPDATE, urlParameters);
        }


        @Override
        protected void onPostExecute(String result) {
            try {
                System.out.println("Response from Url: " + result);
                ObjectMapper objectMapper=new ObjectMapper();
               DemoQuestionsResponse demoQuestionsResponse=objectMapper.readValue(result,DemoQuestionsResponse.class);

                if (demoQuestionsResponse.getStatus().equals("1")){
                    System.out.println("Response from statuscode 1 : " + demoQuestionsResponse.getStatus());
                }
                else if(demoQuestionsResponse.getStatus().equals("2")){
                    dialog.cancel();
                    System.out.println("Response from statuscode 2 : " + demoQuestionsResponse.getStatus());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }


    }

}
