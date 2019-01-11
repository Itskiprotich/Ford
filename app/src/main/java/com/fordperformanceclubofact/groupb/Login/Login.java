package com.fordperformanceclubofact.groupb.Login;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.fordperformanceclubofact.groupb.Database.Database;
import com.fordperformanceclubofact.groupb.R;
import com.fordperformanceclubofact.groupb.ResetPassword.ResetPass;
import com.fordperformanceclubofact.groupb.Terms.Conditions;

public class Login extends AppCompatActivity {

    private ImageView Facebook,Twitter;

    private Button Terms,Loginnow;

    private AutoCompleteTextView Username,Password;

    String user,pass;

    Database database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        database=new Database(this);
        Facebook=(ImageView)findViewById(R.id.face);
        Twitter=(ImageView)findViewById(R.id.twit);
        Terms=(Button)findViewById(R.id.terms);
        Loginnow=(Button)findViewById(R.id.login);
        Username=(AutoCompleteTextView)findViewById(R.id.aa);
        Password=(AutoCompleteTextView)findViewById(R.id.bb);
    }

    public void Facebook(View view) {

        try {
            Intent jeff = new Intent(Intent.ACTION_SEND);

            jeff.setType("text/plain");

            String url = "Ford";

            jeff.putExtra(Intent.EXTRA_TEXT, url);

            jeff.setPackage("com.facebook.katana");

            startActivity(jeff);

        } catch (Exception e) {

            Toast.makeText(Login.this, "please Install Facebook App", Toast.LENGTH_SHORT).show();
        }
    }

    public void Twitter(View view) {

        try {
            Intent jeff = new Intent(Intent.ACTION_SEND);

            jeff.setType("text/plain");

            String url = "Ford";

            jeff.putExtra(Intent.EXTRA_TEXT, url);

            jeff.setPackage("advanced.twitter.android");

            startActivity(jeff);

        } catch (Exception e) {

            Toast.makeText(Login.this, "please Install Twitter App", Toast.LENGTH_SHORT).show();
        }
    }

    public void Terms(View view) {


        Intent intent=new Intent(Login.this, Conditions.class);

        Bundle bundle = new Bundle();

        bundle.putString("email_key", "Terms");

        bundle.putString("link_key", "http://www.fordperformance.com/");

        intent.putExtras(bundle);

        startActivity(intent);
    }

    public void Login(View view) {
        user=Username.getText().toString();

        pass=Password.getText().toString();

        if (user.isEmpty()){

            Username.setError("Enter Username");

            Username.requestFocus();
        }else if(pass.isEmpty()){

            Password.setError("Enter Password");

            Password.requestFocus();
        }else {
//            login here

            saveTosqLite(user,pass);
        }
    }

    private void saveTosqLite(String user, String pass) {

        boolean isInserted = database.addUser(user,pass);

        if (isInserted == true) {

            Snackbar.make(Username, "Account Creation successfull", Snackbar.LENGTH_LONG).setAction("Action", null).show();

            clearThem();

        } else {

            Snackbar.make(Username, "Failed", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }

    }

    private void clearThem() {

        Username.setText("");

        Password.setText("");

        Intent intent=new Intent(Login.this, ResetPass.class);

        startActivity(intent);

    }
}
