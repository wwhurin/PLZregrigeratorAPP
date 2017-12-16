package kr.hs.emirim.wwhurin.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ShowRecommendActivity extends AppCompatActivity {

    private static String ID;

    TextView nameT, ing1T, ing2T, ing3T, timeT, howT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_recommend);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        //Drawer drawer = new DrawerBuilder().withActivity(this).withToolbar(toolbar).build();
        setSupportActionBar(toolbar);

        nameT=(TextView) findViewById(R.id.name);
        ing1T=(TextView) findViewById(R.id.ing1);
        ing2T=(TextView) findViewById(R.id.ing2);
        ing3T=(TextView) findViewById(R.id.ing3);
        timeT=(TextView) findViewById(R.id.time);
        howT=(TextView) findViewById(R.id.how);


        Intent intent=getIntent();
        ID=intent.getExtras().getString("id");
        String menuname=intent.getExtras().getString("menuname");
        String ing1=intent.getExtras().getString("img1");
        String ing2=intent.getExtras().getString("img2");
        String ing3=intent.getExtras().getString("img3");
        String time=intent.getExtras().getString("time");
        String how=intent.getExtras().getString("time");

        nameT.setText(menuname);
        ing1T.setText(ing1);
        ing2T.setText(ing2);
        ing3T.setText(ing3);
        timeT.setText(time);
        howT.setText(how);

        Button buttoncancle = (Button)findViewById(R.id.y2);
        buttoncancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShowRecommendActivity.this, ReconmmedActivity.class);
                intent.putExtra("id", ID);
                startActivity(intent);
                finish();
            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.first:
                Toast.makeText(this, "D-1",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), InputActivity.class);
                intent.putExtra("id", ID);
                startActivity(intent);
                break;
            case R.id.second:
                Toast.makeText(this, "메뉴목록",Toast.LENGTH_SHORT).show();
                intent = new Intent(getApplicationContext(), Menu1Activity.class);
                intent.putExtra("id", ID);
                startActivity(intent);
                finish();
                break;
            case R.id.third:
                Toast.makeText(this, "추천 메뉴",Toast.LENGTH_SHORT).show();
                intent = new Intent(getApplicationContext(), ReconmmedActivity.class);
                intent.putExtra("id", ID);
                startActivity(intent);
                finish();
                break;
            case R.id.fourth:
                Toast.makeText(this, "음식관리 TIP",Toast.LENGTH_SHORT).show();
                intent = new Intent(getApplicationContext(), TIPActivity.class);
                intent.putExtra("id", ID);
                startActivity(intent);
                finish();
                break;
            case R.id.fifth:
                Toast.makeText(this, "계정연동",Toast.LENGTH_SHORT).show();
                intent = new Intent(getApplicationContext(), CodeActivity.class);
                intent.putExtra("id", ID);
                startActivity(intent);
                finish();
                break;
        }
        return true;
    }
}
