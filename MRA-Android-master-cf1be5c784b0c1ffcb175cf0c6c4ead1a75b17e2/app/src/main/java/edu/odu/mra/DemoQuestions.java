package edu.odu.mra;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;

public class DemoQuestions extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_questions);
        WebView wv= (WebView) findViewById(R.id.demoQuest);
        wv.loadUrl("http://www.odu.edu");
       /* Button button= (Button) findViewById(R.id.mradone);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(DemoQuestions.this, "", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DemoQuestions.this, SurveysActivity.class));
            }
        });*/
        showActionBar();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainn, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mradone:
                startActivity(new Intent(DemoQuestions.this, SurveysActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void showActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#003258")));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(getLayoutInflater().inflate(R.layout.actionbar_mradone, null),
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
       /* super.onBackPressed();
        // Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();
        String cameback = "CameBack";
        Intent intent = new Intent(DemoQuestions.this, SurveysActivity.class);
        intent.putExtra("Comingback", cameback);
        System.out.println("back" + cameback);
        startActivity(intent);
*/
    }


}
