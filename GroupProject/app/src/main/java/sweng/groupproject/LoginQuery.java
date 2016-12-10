package sweng.groupproject;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.Buffer;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.List;

import static android.support.v7.appcompat.R.id.text;

/**
 * Created by damian on 17/11/16.
 */

public class LoginQuery extends AppCompatActivity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    ArrayList<String> mydata = new ArrayList<String>();
    ArrayList<String> metaData = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final myQuery x = new myQuery();
        Button loginAcc = (Button) findViewById(R.id.LoginButton); //login account button
        loginAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText name = (EditText) findViewById(R.id.Username);
                EditText pass = (EditText) findViewById(R.id.AccPassword);
                String username = (name.getText()).toString();
                String password = (pass.getText()).toString();
                System.out.print(username);
                System.out.print(password);
                metaData.add("NAME");
                metaData.add("PASSWORD");
                mydata.add(username);
                mydata.add(password);
                x.execute();
            }
        });
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    public class myQuery extends AsyncTask<String, String, String>{
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
                send.append(details);
                Log.d("loginDetails", "Meta ="+metaData.get(0) + " data = "+ mydata.get(0) );
                Log.d("loginDetails", "Meta ="+metaData.get(1) + " data = "+ mydata.get(1) );
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
            Intent changePage = new Intent(LoginQuery.this, MainPage.class);
            startActivity(changePage);
        }
    }
}
