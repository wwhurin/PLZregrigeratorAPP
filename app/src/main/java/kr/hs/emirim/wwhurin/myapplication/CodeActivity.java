package kr.hs.emirim.wwhurin.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import static android.os.Build.ID;

public class CodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        //Drawer drawer = new DrawerBuilder().withActivity(this).withToolbar(toolbar).build();
        setSupportActionBar(toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
