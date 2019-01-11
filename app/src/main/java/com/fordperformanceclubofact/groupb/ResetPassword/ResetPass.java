package com.fordperformanceclubofact.groupb.ResetPassword;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.fordperformanceclubofact.groupb.Database.Database;
import com.fordperformanceclubofact.groupb.Front.FrontPage;
import com.fordperformanceclubofact.groupb.R;

public class ResetPass extends AppCompatActivity {

    private Toolbar toolbar;

    private Button Loginnow;

    private AutoCompleteTextView Username,Password;

    String user,pass;

    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        database=new Database(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Reset Password");

        Loginnow=(Button)findViewById(R.id.login);
        Username=(AutoCompleteTextView)findViewById(R.id.aa);
        Password=(AutoCompleteTextView)findViewById(R.id.bb);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    public void Reset(View view) {
        user=Username.getText().toString();

        pass=Password.getText().toString();

        if (user.isEmpty()){

            Username.setError("Enter Password");

            Username.requestFocus();
        }else if(pass.isEmpty()){

            Password.setError("Enter Confirm Password");

            Password.requestFocus();
        }else {
//            login here
            if (user.equalsIgnoreCase(pass)) {

                saveTosqLite(user);
            }else{
                Snackbar.make(Username, "Password does not match", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        }
    }

    private void saveTosqLite(String user) {

        String id="1";

        boolean isInserted = database.updateUser(user,id);

        if (isInserted == true) {

            Snackbar.make(Username, "Password Update Successful", Snackbar.LENGTH_LONG).setAction("Action", null).show();

            clearThem();

        } else {

            Snackbar.make(Username, "Failed to Update Password", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }

    }

    private void clearThem() {


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
        }

    }

    private void welcome(String username) {

        Intent jeff = new Intent(this, FrontPage.class);

        Bundle bundle = new Bundle();

        bundle.putString("email_key", username);

        jeff.putExtras(bundle);

        startActivity(jeff);
    }
}
