package com.fordperformanceclubofact.groupb.Updates;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fordperformanceclubofact.groupb.Contact.AllContact;
import com.fordperformanceclubofact.groupb.Contact.Upload;
import com.fordperformanceclubofact.groupb.Model.Anounce;
import com.fordperformanceclubofact.groupb.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecentUpdates extends AppCompatActivity {

    private ListView listView;

    FirebaseDatabase database;

    DatabaseReference reference;

    AdapterClass adapterClass;

    ProgressDialog progressDialog;

    ArrayList<Anounce> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_updates);

        listView=(ListView)findViewById(R.id.list);


        adapterClass = new AdapterClass(getApplicationContext(), arrayList);

        listView.setAdapter(adapterClass);

        addData();
    }
    private void addData() {

        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Loading,please wait...");

        progressDialog.show();

        database = FirebaseDatabase.getInstance();

        reference = database.getReference("Announcements");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                progressDialog.dismiss();

                arrayList.clear();

                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {

                    Anounce upload = new Anounce();

                    String message = (String) messageSnapshot.child("message").getValue();

                    upload.setMessage(message);

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

        ArrayList<Anounce> status;

        public AdapterClass(Context context, ArrayList<Anounce> status) {

            super(context, R.layout.announce, R.id.bb, status);

            this.status = status;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.announce, parent, false);

            TextView aa = (TextView) view.findViewById(R.id.bb);

            Anounce anounce=status.get(position);

            aa.setText(anounce.getMessage().toString());


            return view;
        }
    }

    @Override
    public void onBackPressed() {

        finish();

        super.onBackPressed();
    }
}
