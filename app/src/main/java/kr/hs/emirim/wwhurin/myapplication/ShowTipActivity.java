package kr.hs.emirim.wwhurin.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class ShowTipActivity extends AppCompatActivity {

    TextView nameT;
    TextView howT, plusT;

    String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tip);

        nameT=(TextView)findViewById(R.id.name);
        howT=(TextView)findViewById(R.id.tip);
        plusT=(TextView)findViewById(R.id.ect);

        Intent intent=getIntent();
        ID=intent.getExtras().getString("id");
        String foodname=intent.getExtras().getString("foodname");
        String how=intent.getExtras().getString("HOW");
        String plus=intent.getExtras().getString("PLUS");

        Log.d("!!!!!!!1112222: ", ID);

        nameT.setText(foodname);
        howT.setText(how);
        plusT.setText(plus);
    }
}
