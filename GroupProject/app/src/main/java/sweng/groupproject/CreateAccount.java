package sweng.groupproject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by damian on 29/11/16.
 */

public class CreateAccount extends AppCompatActivity {
    ArrayList<String> mydata = new ArrayList<String>();
    ArrayList<String> metaData = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
        EditText name = (EditText) findViewById(R.id.newUserName);
        EditText pass = (EditText) findViewById(R.id.firstPassword);
        EditText confrimPass = (EditText) findViewById(R.id.secondPassword);
        metaData.add("NAME");
        metaData.add("PASSWORD");
        metaData.add("CONFIRM_PASSWORD");
        mydata.add((name.getText()).toString());
        mydata.add((pass.getText()).toString());
        mydata.add(confrimPass.toString());
        CreateInsert x = new CreateInsert();
        x.execute();
    }
    public class CreateInsert extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings){
            StringBuilder send = new StringBuilder();
            StringBuilder result = new StringBuilder();
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL("http://99461724.ngrok.io/login.php");
                String details = null;
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                details = URLEncoder.encode(metaData.get(0), "utf-8")
                        + "=" + URLEncoder.encode(mydata.get(0), "utf-8");
                details += "&" + URLEncoder.encode(metaData.get(1), "utf-8")
                        + "=" + URLEncoder.encode(mydata.get(1), "utf-8");
                details += "&" + URLEncoder.encode(metaData.get(2), "utf-8")
                        + "=" + URLEncoder.encode(mydata.get(2), "utf-8");
                send.append(details);
                Log.d("loginDeatils", "Meta ="+metaData.get(0) + "data = "+ mydata.get(0) );
                Log.d("loginDeatils", "Meta ="+metaData.get(1) + "data = "+ mydata.get(1) );
               // Log.d("loginDeatils", "Meta ="+metaData.get(2) + "data = "+ mydata.get(2) );
                OutputStreamWriter opStream = new OutputStreamWriter(urlConnection.getOutputStream());//outputstream writer
                opStream.write(send.toString());
                opStream.flush();
                if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    Log.d("Tag", "Sucess");
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader read = new BufferedReader(new InputStreamReader(in));
                    String line;
                    while((line = read.readLine()) !=null){
                        result.append(line);
                    }
                }
                else{
                    Log.d("Tag1" , "failed");
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if(urlConnection !=null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result){
            Intent changePage = new Intent(CreateAccount.this, MainActivity.class);
            startActivity(changePage);
        }
    }
}
