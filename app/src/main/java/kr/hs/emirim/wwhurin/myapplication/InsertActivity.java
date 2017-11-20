package kr.hs.emirim.wwhurin.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class InsertActivity extends AppCompatActivity {

    private final static String TAG="INSERTACT";

    private static EditText fname;
    private static EditText inputdate;
    private static EditText outdate;
    private static EditText content;
    private static String CODE;

    private TextView resultT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        resultT=(TextView)findViewById(R.id.resultT);

        fname=(EditText)findViewById(R.id.name);
        inputdate=(EditText)findViewById(R.id.inputdate);
        outdate=(EditText)findViewById(R.id.outdate);
        content=(EditText)findViewById(R.id.content);

        Intent intent=getIntent();
        CODE=intent.getExtras().getString("id");
        Log.d("!!!!!!!: ", CODE);


        Button buttonInsert = (Button)findViewById(R.id.button6);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = fname.getText().toString();
                String input = inputdate.getText().toString();
                String out = outdate.getText().toString();
                String con = content.getText().toString();

                InsertData task = new InsertData();
                task.execute(name,input, out, con);

            }
        });

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
            resultT.setText(result);//php에서 echo 해주는 내용 출력해준다
           /* if(result.equals("ok")){
                Log.d("od", "dddd");
                Intent intent=new Intent(LoginActivity.this, InputActivity.class);
                intent.putExtra("id", name);
                startActivity(intent);
                finish();
            }*/
            Log.d(TAG, "POST response  - " + result);
        }



        @Override
        protected String doInBackground(String... params) {

            String name = (String)params[0];
            String inputdate = (String)params[1];
            String outdate = (String)params[2];
            String content = (String)params[3];

            String serverURL = "http://wwhurin.dothome.co.kr/menu1IN.php";
            String postParameters = "name=" + name + "&inputdate=" + inputdate+"&outdate="+outdate+"&content="+content+"&id="+CODE;
            Log.d("~~~~~: ", CODE);


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
