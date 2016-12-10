package sweng.groupproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
 * Created by damian on 05/12/16.
 */

public class CreateProject extends AppCompatActivity {
    ArrayList<String> mydata = new ArrayList<String>();
    ArrayList<String> metaData = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.create_project);
        metaData.add("NAME");
        metaData.add("START");
        metaData.add("END");
        Button create = (Button) findViewById(R.id.createProjectButton);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText name = (EditText) findViewById(R.id.projectName);
                EditText start = (EditText) findViewById(R.id.startDateBox);
                EditText end = (EditText) findViewById(R.id.endDateBox);
                mydata.add((name.getText()).toString());
                mydata.add((start.getText()).toString());
                mydata.add((end.getText()).toString());
                AddProject x = new AddProject();
                x.execute();
            }
        });
    }
    private class AddProject extends AsyncTask<String, String, String>{
        @Override
        protected String doInBackground(String... string){
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
                Log.d("loginDetails", "Meta ="+metaData.get(0) + " data = "+ mydata.get(0));
                Log.d("loginDetails", "Meta ="+metaData.get(1) + " data = "+ mydata.get(1));
                Log.d("loginDetails", "Meta ="+metaData.get(2) + " data = "+ mydata.get(2));
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
    }
}
