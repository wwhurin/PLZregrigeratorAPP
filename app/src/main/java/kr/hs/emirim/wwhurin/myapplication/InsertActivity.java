package kr.hs.emirim.wwhurin.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class InsertActivity extends AppCompatActivity {

    private final static String TAG="INSERTACT";

    private static EditText fname;
    private static EditText ind1; private static EditText ind2; private static EditText ind3;
    private static EditText out1;  private static EditText out2;  private static EditText out3;
    private static EditText content;

    String ID;

    private TextView resultT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        //Drawer drawer = new DrawerBuilder().withActivity(this).withToolbar(toolbar).build();
        setSupportActionBar(toolbar);

        //resultT=(TextView)findViewById(R.id.resultT);

        fname=(EditText)findViewById(R.id.editText2);
        ind1=(EditText)findViewById(R.id.ind1); ind2=(EditText)findViewById(R.id.ind2); ind3=(EditText)findViewById(R.id.ind3);
        out1=(EditText)findViewById(R.id.out1); out2=(EditText)findViewById(R.id.out2); out3=(EditText)findViewById(R.id.out3);
        content=(EditText)findViewById(R.id.content);

        Intent intent=getIntent();
        ID=intent.getExtras().getString("id");
        Log.d("!!!!!!!: ", ID);


        Button buttonInsert = (Button)findViewById(R.id.yes);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = fname.getText().toString();
                String input = (ind1.getText().toString())+"-"+(ind2.getText().toString())+"-"+(ind3.getText().toString());
                String out = (out1.getText().toString())+"-"+(out2.getText().toString())+"-"+(out3.getText().toString());
                String con = content.getText().toString();

                InsertData task = new InsertData();
                task.execute(name,input, out, con);

            }
        });


        Button buttoncancle = (Button)findViewById(R.id.no);
        buttoncancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(InsertActivity.this, Menu1Activity.class);
                intent.putExtra("id", ID);
                startActivity(intent);
                finish();

            }
        });

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


    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(InsertActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {//echo 해준 값 나오는곳
            super.onPostExecute(result);

            progressDialog.dismiss();
//            resultT.setText(result);//php에서 echo 해주는 내용 출력해준다
           /* if(result.equals("ok")){
                Log.d("od", "dddd");
                Intent intent=new Intent(LoginActivity.this, InputActivity.class);
                intent.putExtra("id", name);
                startActivity(intent);
                finish();
            }*/

           if(result!=null){
               Intent goi;
               goi = new Intent(getApplicationContext(), InputActivity.class);
               goi.putExtra("id", ID);
               startActivity(goi);
               finish();
           }
            Log.d(TAG, "POST response  - " + result);
        }



        @Override
        protected String doInBackground(String... params) {

            String name = (String)params[0];
            String inputdate = (String)params[1];
            String outdate = (String)params[2];
            String content = (String)params[3];

            String serverURL = "http://wwhurin.dothome.co.kr/menu1IN.php";
            String postParameters = "name=" + name + "&inputdate=" + inputdate+"&outdate="+outdate+"&content="+content+"&id="+ID;
            Log.d("~~~~~: ", ID);


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                //httpURLConnection.setRequestProperty("content-type", "application/json");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }
}
