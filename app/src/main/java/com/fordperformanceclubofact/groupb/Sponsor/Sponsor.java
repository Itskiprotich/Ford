package com.fordperformanceclubofact.groupb.Sponsor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.fordperformanceclubofact.groupb.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Sponsor extends AppCompatActivity {

    private Toolbar toolbar;

    private ListView listView;

    private ProgressDialog progressDialog;

    AlertDialog al;

    FirebaseDatabase database;

    DatabaseReference reference;

    AdapterClass adapterClass;

    ArrayList<SponsorList> arrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

//        getSupportActionBar().setTitle("Sponsors");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.list);

        adapterClass = new AdapterClass(getApplicationContext(), arrayList);

        listView.setAdapter(adapterClass);

        addData();
    }

    private void addData() {

        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Loading,please wait...");

        progressDialog.show();

        database = FirebaseDatabase.getInstance();

        reference = database.getReference("Sponsors");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                progressDialog.dismiss();

                arrayList.clear();

                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {

                    SponsorList upload = new SponsorList();

                    String name = (String) messageSnapshot.child("name").getValue();

                    String supply = (String) messageSnapshot.child("supply").getValue();

                    String phone = (String) messageSnapshot.child("phone").getValue();

                    String rate = (String) messageSnapshot.child("rate").getValue();

                    upload.setName(name);

                    upload.setSupply(supply);

                    upload.setPhone(phone);

                    upload.setRate(rate);

                    arrayList.add(upload);

                    adapterClass.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private class AdapterClass extends ArrayAdapter {

        ArrayList<SponsorList> status;

        public AdapterClass(Context context, ArrayList<SponsorList> status) {

            super(context, R.layout.sponsors, R.id.bb, status);

            this.status = status;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.sponsors, parent, false);

            SponsorList upload = status.get(position);

            final Button textView = (Button) view.findViewById(R.id.bb);

            textView.setText(upload.getName());

            final String name = upload.getName();

            final String su = upload.getSupply();

            final String phone = upload.getPhone();

            final String rate = upload.getRate();

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    openName(name, su, phone, rate);
                }
            });


            return view;
        }
    }

    private void openName(String name, String su, String phone, String rate) {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(false);

        builder.setTitle(name);

        builder.setMessage(su + "\n" + phone + "\n" + rate);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });

        builder.create().show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }


}
