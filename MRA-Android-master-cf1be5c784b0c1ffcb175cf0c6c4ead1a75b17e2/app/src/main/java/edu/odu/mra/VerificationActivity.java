package edu.odu.mra;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.codehaus.jackson.map.ObjectMapper;

import edu.odu.mra.models.LoginResponse;

public class VerificationActivity extends AppCompatActivity {

    TextView tview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        String response;
        LoginResponse loginResponse=null;
        tview=(TextView)findViewById(R.id.verficationText);

        Bundle extras=getIntent().getExtras();
        if(extras!=null)
        {
            try {
                response = extras.getString("userdetails");
                ObjectMapper mapper = new ObjectMapper();
                loginResponse = mapper.readValue(response, LoginResponse.class);
                tview.setText(loginResponse.getMsg());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        if(loginResponse!=null)
        {

        }
    }
}
