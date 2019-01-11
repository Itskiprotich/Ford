package com.fordperformanceclubofact.groupb;

import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fordperformanceclubofact.groupb.Database.Database;
import com.fordperformanceclubofact.groupb.Front.FrontPage;
import com.fordperformanceclubofact.groupb.Login.Login;

public class MainActivity extends AppCompatActivity {


    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database=new Database(this);


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */

                checkUser();
            }
        }, 2000);
    }

    private void checkUser() {


        String user = "1";

        Cursor cursor = database.searchEmail();

        if (cursor != null && cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                String d = cursor.getString(0);

                String username = cursor.getString(1);

                if (d.equalsIgnoreCase(user)){

                    welcome(username);
                }
            }
        }else{

            Intent intent=new Intent(MainActivity.this, Login.class);

            startActivity(intent);

            finish();

        }
    }

    private void welcome(String username) {

        Intent jeff = new Intent(this, FrontPage.class);

        Bundle bundle = new Bundle();

        bundle.putString("email_key", username);

        jeff.putExtras(bundle);

        startActivity(jeff);

        finish();
    }
}
