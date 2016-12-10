package sweng.groupproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by damian on 04/12/16.
 */

public class MainPage extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.main_page);
        Button createProj = (Button) findViewById(R.id.create_project_button);
        Button scanner = (Button) findViewById(R.id.scan);
        createProj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent create = new Intent(MainPage.this, CreateProject.class);
                startActivity(create);
            }
        });
        scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
