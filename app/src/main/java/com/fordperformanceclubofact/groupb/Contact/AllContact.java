package com.fordperformanceclubofact.groupb.Contact;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fordperformanceclubofact.groupb.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllContact extends AppCompatActivity {

    private ListView listView;

    FirebaseDatabase database;

    DatabaseReference reference;

    AdapterClass adapterClass;

    ArrayList<Upload> arrayList = new ArrayList<>();

    private Toolbar toolbar;

    private ImageView Email;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_contact);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Contacts ");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.list);

        adapterClass = new AdapterClass(getApplicationContext(), arrayList);

        listView.setAdapter(adapterClass);

        Email = (ImageView) findViewById(R.id.email);

        addData();


    }


    private void addData() {

        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Loading,please wait...");

        progressDialog.show();

        database = FirebaseDatabase.getInstance();

        reference = database.getReference("Contacts");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                progressDialog.dismiss();

                arrayList.clear();

                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {

                    Upload upload = new Upload();

                    String name = (String) messageSnapshot.child("name").getValue();

                    String email = (String) messageSnapshot.child("email").getValue();

                    String profile = (String) messageSnapshot.child("profile").getValue();

                    upload.setName(name);

                    upload.setEmail(email);

                    upload.setProfile(profile);

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

        ArrayList<Upload> status;

        public AdapterClass(Context context, ArrayList<Upload> status) {

            super(context, R.layout.contacts, R.id.bb, status);

            this.status = status;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.contacts, parent, false);

            CircleImageView imageView = (CircleImageView) view.findViewById(R.id.aa);

            final CheckedTextView textView = (CheckedTextView) view.findViewById(R.id.bb);

            TextView aa = (TextView) view.findViewById(R.id.cc);

            Upload upload = status.get(position);

            textView.setText(upload.getName());

            aa.setText(upload.getEmail());

            String image = upload.getProfile();

            Picasso.with(getContext()).load(image).into(imageView);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    textView.toggle();
                }
            });

            return view;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    public void EmailAddress(View view) {

        Intent intent = new Intent(Intent.ACTION_SEND);

        intent.setData(Uri.parse("email"));

        String[] s = {"keeprawteachjapheth@gmail.com", "Vanny@gmail.com"};

        intent.putExtra(Intent.EXTRA_EMAIL, s);

        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");

        intent.putExtra(Intent.EXTRA_TEXT, "Welcome members");

        intent.setType("message/rfc822");

        Intent chooser = Intent.createChooser(intent, "Launch Email");

        startActivity(chooser);

    }

}

