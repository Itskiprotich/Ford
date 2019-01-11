package com.fordperformanceclubofact.groupb.Front;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fordperformanceclubofact.groupb.Membership.Membership;
import com.fordperformanceclubofact.groupb.R;

public class FrontPage extends AppCompatActivity {


    String email;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);
        email = getIntent().getStringExtra("email_key");

        button = (Button) findViewById(R.id.username);

        button.setText("Proceed As " + email);
    }

    public void Proceed(View view) {

        Intent intent=new Intent(this, Membership.class);

        Bundle bundle = new Bundle();

        bundle.putString("email_key", email);

        intent.putExtras(bundle);

        startActivity(intent);

        finish();
    }
}
