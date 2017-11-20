package kr.hs.emirim.wwhurin.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Menu1Activity extends AppCompatActivity {

    private static final String TAG_JSON="webnautes";
    private static final String TAG_NAME = "name";
    private static final String TAG_INPUT ="inputdate";
    private static final String TAG_OUTPUT ="outdate";
    private static final String TAG_CONTENT ="content";

    private static  HashMap<String, String> hashMap;

    private static String ID;
    private static String Food_number;

    private String TAG="TAG";
    private TextView mTextViewResult;
    ArrayList<HashMap<String, String>> mArrayList;
    ListView mlistView;
    String mJsonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu1);


        Intent intent=getIntent();
        ID=intent.getExtras().getString("id");
        Log.d("!!!!!!!1112222: ", ID);

        mlistView = (ListView) findViewById(R.id.listview);
        mTextViewResult=(TextView) findViewById(R.id.textView_main_result);
        mArrayList = new ArrayList<>();

        Button menu1 = (Button) findViewById(R.id.button7) ;
        menu1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                Intent intent=new Intent(Menu1Activity.this, InsertActivity.class);
                intent.putExtra("id", ID);
                startActivity(intent);
                finish();
            }
        });//button1

        GetData task = new GetData();
        task.execute("http://wwhurin.dothome.co.kr/menu1.php");

        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view, int position, long id) {

                //클릭한 아이템의 문자열을 가져옴
                HashMap<String,String> map =(HashMap<String,String>)adapterView.getItemAtPosition(position);
                String name= map.get(TAG_NAME);
                Log.d("selected_item::::", name);

                Intent intent1 = new Intent(Menu1Activity.this, GetItemActivity.class);
                intent1.putExtra("NUMBER", Food_number);
                startActivity(intent1);
                finish();

            }
        });


    }


    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(Menu1Activity.this,
                    "Please Wait", null, true, true);
        }




        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters="id="+ID;


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                //httpURLConnection.connect();
                httpURLConnection.setRequestMethod("POST");
                //httpURLConnection.setRequestProperty("content-type", "application/json");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                } else {
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
            Log.d(TAG, "response  - " + result);

            if (result == null) {

                mTextViewResult.setText(errorString);
            } else {

                mJsonString = result;
                showResult();
            }
        }


        private void showResult() {
            try {
                JSONObject jsonObject = new JSONObject(mJsonString);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject item = jsonArray.getJSONObject(i);

                    String name = item.getString(TAG_NAME);
                    Food_number=item.getString("num");
                    Log.d("이름~~~~: ", name+""+Food_number);

                   hashMap = new HashMap<>();

                    hashMap.put(TAG_NAME, name);
                    hashMap.put("num", Food_number);
                   // hashMap.put(TAG_ADDRESS, address);

                    mArrayList.add(hashMap);
                }

                ListAdapter adapter = new SimpleAdapter(
                        Menu1Activity.this, mArrayList, R.layout.menu_item_list,
                        new String[]{TAG_NAME},
                        new int[]{R.id.textView_list_name}
                );

                mlistView.setAdapter(adapter);




            } catch (JSONException e) {

                Log.d(TAG, "showResult : ", e);
            }

        }
    }

}
