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
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.odu.mra.constants.WebServices;
import edu.odu.mra.models.RegisterResponse;
import edu.odu.mra.utility.JsonWebServiceCaller;

public class RegistrationActivity extends AppCompatActivity {

    Button submit;
    EditText firstName;
    EditText lastName;
    EditText midasid;
    Spinner dobspinner;
    TextView pwd;
    TextView age;
    TextView education;
    Spinner gender;
    Spinner maritalstatus;
    Spinner racespinner;
    Spinner educationspinner;
    private Map<String,String> genderMap=new HashMap<String,String>();
    private Map<String,String> raceMap=new HashMap<String,String>();
    private Map<String,String>maritalstatusMap= new HashMap<String,String>();
    private Map<String,String>educationMap=new HashMap<String,String>();
    private Map<String,String>ageMap=new HashMap<String,String>();

    Calendar cal=Calendar.getInstance();
    Date currentyear=cal.getTime();
    private int BirthAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        genderMap.put("Select", "SELECT");
        genderMap.put("Male", "MALE");
        genderMap.put("Female", "FEMA");
        genderMap.put("Not Listed","NOT LISTED");
        genderMap.put("No Response","NO RESPONSE");

        raceMap.put("Select","SELECT");
        raceMap.put("Hispanic/Latino","HISP");
        raceMap.put("American Indian/Eskimo","AIAN");
        raceMap.put("Asian/Pacific Islander","ASIAN");
        raceMap.put("Black/African American","BLACK");
        raceMap.put("Native Hawaiian/Other Pacific Islander","HAWA");
        raceMap.put("Caucasian","WHITE");
        raceMap.put("Multiracial","MURA");
        raceMap.put("No Response","NR");

        maritalstatusMap.put("Select","SELECT");
        maritalstatusMap.put("Single","SING");
        maritalstatusMap.put("Married","MARR");
        maritalstatusMap.put("Divorced","DIVO");
        maritalstatusMap.put("Separated","SEPA");
        maritalstatusMap.put("Widowed","WIDO");
        maritalstatusMap.put("No Response","NR");

        educationMap.put("Select","SELECT");
        educationMap.put("Doctoral","DOC");
        educationMap.put("Masters","MAS");
        educationMap.put("Bachelors","BAC");
        educationMap.put("Associate","ASO");
        educationMap.put("High School Diploma","HSD");
        educationMap.put("GED","GED");
        educationMap.put("No Response","NR");

         ageMap.put("Select","SELECT");

        submit=(Button)findViewById(R.id.regSubmit);
        firstName=(EditText)findViewById(R.id.firstName);
        lastName=(EditText)findViewById(R.id.lastName);
        midasid=(EditText)findViewById(R.id.midasIDReg);
        pwd= (TextView) findViewById(R.id.regPassword);
        age= (TextView) findViewById(R.id.ageTitle);
        education= (TextView) findViewById(R.id.EducationalLevelTitle);

        gender= (Spinner) findViewById(R.id.genderSpinner);
        maritalstatus= (Spinner) findViewById(R.id.maritalDropdown);
        dobspinner=(Spinner)findViewById(R.id.ageSpinner);
        racespinner=(Spinner)findViewById(R.id.raceDropdown);
        educationspinner= (Spinner) findViewById(R.id.educationDropdown);


        final List<String> years=new ArrayList<String>();
        years.add("Select");
        years.add("No Response");
        for(int i=1998;i>1950;i--)
        {
            years.add(""+i);
        }
        int currage=cal.get(Calendar.YEAR)-18;
        System.out.println("Current age : " + currage);

       /* if(age.getText().toString().equals(1998)||Integer.parseInt(age.getText().toString())<1998){
        Toast.makeText(getApplicationContext(),"year",Toast.LENGTH_LONG).show();
        }*/
        ArrayAdapter<String> ageAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,years);
        dobspinner.setAdapter(ageAdapter);
        dobspinner.setPrompt("Select");
        //dobspinner.setAdapter();

        System.out.println("Details..." + firstName.getText().toString());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(firstName.getText().toString().trim())) {
                    firstName.setError("Please enter First Name");
                } else if (TextUtils.isEmpty(lastName.getText().toString().trim())) {
                    lastName.setError("Please enter Last Name");
                } else if (TextUtils.isEmpty(midasid.getText().toString().trim())) {
                    midasid.setError("Please enter the id");
                } else if (TextUtils.isEmpty(pwd.getText().toString().trim())) {
                    lastName.setError("Please enter password");
                } else if (TextUtils.isEmpty(age.getText().toString().trim())) {
                    age.setError("Please enter age");
                }
                else if(dobspinner.getSelectedItem().equals("Select")||gender.getSelectedItem().equals("Select")||racespinner.getSelectedItem().equals("Select")||educationspinner.getSelectedItem().equals("Select")||maritalstatus.getSelectedItem().equals("Select")){
                       Toast.makeText(getApplicationContext(), "Please choose an option", Toast.LENGTH_LONG).show();

                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
                    builder.setTitle("Confirm");
                    builder.setMessage("Do you certify that you are at least 18 years old?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            System.out.println("Details..." + firstName.getText().toString() + lastName.getText().toString() + midasid.getText().toString() + pwd.getText().toString() + dobspinner.getSelectedItem().toString()
                                    + gender.getSelectedItem().toString() + racespinner.getSelectedItem().toString() + maritalstatus.getSelectedItem().toString() + educationspinner.getSelectedItem().toString());
                            new RegistrationTask().execute(firstName.getText().toString(), lastName.getText().toString(), midasid.getText().toString(), pwd.getText().toString(),
                                    dobspinner.getSelectedItem().toString(), gender.getSelectedItem().toString(), racespinner.getSelectedItem().toString(), maritalstatus.getSelectedItem().toString(),
                                    educationspinner.getSelectedItem().toString());
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                        }
                    });
                    builder.show();


                }
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
        tv.setText("MRA");
        actionBar.setCustomView(view,
                new ActionBar.LayoutParams(
                        ActionBar.LayoutParams.WRAP_CONTENT,
                        ActionBar.LayoutParams.MATCH_PARENT,
                        Gravity.CENTER
                )
        );
    }

    class RegistrationTask extends AsyncTask<String, Integer, String>
    {
        private ProgressDialog dialog=new ProgressDialog(RegistrationActivity.this);

        @Override
        protected void onPreExecute() {
            dialog.setMessage("loading matches...");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String urlParameters = "firstname="+params[0]+"&lastname="+params[1]+"&email="+params[2]+"&pwd="+params[3]+"&age="+params[4]+"&gender="+params[5]+"&race="+params[6]
                    +"&maritalstatus="+params[7]+"&education="+params[8];
            System.out.println("Request:" + urlParameters);
            return JsonWebServiceCaller.call(WebServices.REGISTER, urlParameters);

        }

        @Override
        protected void onPostExecute(String result) {
            try {
                System.out.println("Response from Url register : " + result);
                ObjectMapper mapper = new ObjectMapper();
                RegisterResponse registerResponse = mapper.readValue(result, RegisterResponse.class);

                   if(registerResponse.getStatus().equals("1"))
                    {   dialog.cancel();
                        System.out.println("Response from statuscode 1 : " + registerResponse.getStatus());
                        //Toast.makeText(getApplicationContext(), registerResponse.getMsg(), Toast.LENGTH_LONG).show();
                       // Toast.makeText(getApplicationContext(),"Proceed to check your email",Toast.LENGTH_LONG).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
                        builder.setTitle("Verify Your Email");
                        builder.setMessage("You will recieve an email, please follow the link in it and verify your email");
                        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Toast.makeText(MainActivity.this, "", Toast.LENGTH_LONG);
                                Intent intent3 =new Intent(RegistrationActivity.this, MainActivity.class);
                                intent3.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(intent3);
                            }
                        });
                        builder.show();

                    }
                    else if(registerResponse.getStatus().equals("2"))
                    {   dialog.cancel();
                        System.out.println("Response from statuscode 2 : " + registerResponse.getStatus());
                        //Toast.makeText(getApplicationContext(),registerResponse.getMsg(),Toast.LENGTH_LONG).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
                        builder.setTitle("User Already Exists!");
                        builder.setMessage("Reset your password");
                        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Toast.makeText(MainActivity.this, "", Toast.LENGTH_LONG);
                                Intent intent2 =new Intent(RegistrationActivity.this, MainActivity.class);
                                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(intent2);
                            }
                        });
                     builder.show();
                    }
                else if(registerResponse.getStatus().equals("0")) {
                       System.out.println("Response from statuscode 0 : " + registerResponse.getStatus());
                    dialog.cancel();
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
                    builder.setTitle("Information");
                    builder.setMessage("User Already Exists!");
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent1 =new Intent(RegistrationActivity.this, MainActivity.class);
                            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                            startActivity(intent1);
                        }
                    });
                    builder.show();
                }
                else{
                       dialog.cancel();
                       AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
                       builder.setTitle("Alert");
                       builder.setMessage("Something went wrong");
                       builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {
                               //Toast.makeText(getApplicationContext(),registerResponse.getMsg(),Toast.LENGTH_LONG).show();
                           }
                       });
                       builder.show();
                   }

            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }

        }

    }
}
