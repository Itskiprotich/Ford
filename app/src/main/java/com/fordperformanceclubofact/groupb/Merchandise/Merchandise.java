package com.fordperformanceclubofact.groupb.Merchandise;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fordperformanceclubofact.groupb.Contact.Upload;
import com.fordperformanceclubofact.groupb.Database.Database;
import com.fordperformanceclubofact.groupb.R;
import com.fordperformanceclubofact.groupb.Terms.Conditions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Merchandise extends AppCompatActivity {


    private Toolbar toolbar;

    private GridView listView;

    AlertDialog al;

    Database db;


    FirebaseDatabase database;

    DatabaseReference reference;

    AdapterClass adapterClass;

    ArrayList<UploadM> arrayList = new ArrayList<>();

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchandise);
        db = new Database(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Merchandise ");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (GridView) findViewById(R.id.gridview);

        adapterClass = new AdapterClass(getApplicationContext(), arrayList);

        listView.setAdapter(adapterClass);

        addData();
    }
    private void addData() {

        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Loading,please wait...");

        progressDialog.show();

        database = FirebaseDatabase.getInstance();

        reference = database.getReference("Merchandise");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                progressDialog.dismiss();

                arrayList.clear();

                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {

                    UploadM upload = new UploadM();

                    String name = (String) messageSnapshot.child("name").getValue();

                    String price = (String) messageSnapshot.child("price").getValue();

                    String image = (String) messageSnapshot.child("image").getValue();

                    upload.setName(name);

                    upload.setPrice(""+price);

                    upload.setImage(image);

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

        ArrayList<UploadM> status;

        public AdapterClass(Context context, ArrayList<UploadM> status) {

            super(context, R.layout.merchandise, R.id.bb, status);

            this.status = status;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.merchandise, parent, false);

            ImageView imageView = (ImageView) view.findViewById(R.id.aa);

            final TextView textView = (TextView) view.findViewById(R.id.bb);

            TextView aa = (TextView) view.findViewById(R.id.cc);

            UploadM upload = status.get(position);

            textView.setText(upload.getName());

            aa.setText(upload.getPrice());

            String image = upload.getImage();

            Picasso.with(getContext()).load(image).into(imageView);

            TextView paypall = (TextView) view.findViewById(R.id.dd);

            final String a = aa.getText().toString();


            paypall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    username(a);
                }
            });



            return view;
        }
    }

    private class MyAdapter extends ArrayAdapter {

        Context context;

        String[] values;

        int[] imageArray;

        String[] price;


        public MyAdapter(Context context, String[] values, int[] images, String[] price) {
            super(context, R.layout.merchandise, R.id.bb, values);

            this.context = context;

            this.values = values;

            this.imageArray = images;

            this.price = price;


        }

        @NonNull
        @Override
        public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = inflater.inflate(R.layout.merchandise, parent, false);

            TextView textView = (TextView) row.findViewById(R.id.bb);

            textView.setText(values[position]);

            TextView cash = (TextView) row.findViewById(R.id.cc);

            cash.setText(price[position]);

            ImageView imageView = (ImageView) row.findViewById(R.id.aa);

            imageView.setImageResource(imageArray[position]);

            TextView paypall = (TextView) row.findViewById(R.id.dd);

            final String a = cash.getText().toString();


            paypall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    username(a);
                }
            });

            return row;
        }
    }

    private void username(String a) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater lay = LayoutInflater.from(this);

        final View viewdata = lay.inflate(R.layout.log, null);

        final AutoCompleteTextView AA = (AutoCompleteTextView) viewdata.findViewById(R.id.aa);

        final AutoCompleteTextView BB = (AutoCompleteTextView) viewdata.findViewById(R.id.bb);

        Button Cancel = (Button) viewdata.findViewById(R.id.terms);

        Button Login = (Button) viewdata.findViewById(R.id.login);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String user = AA.getText().toString();

                String pass = BB.getText().toString();

                if (user.isEmpty()) {
                    Toast.makeText(Merchandise.this, "Enter Username", Toast.LENGTH_SHORT).show();
                } else if (pass.isEmpty()) {

                    Toast.makeText(Merchandise.this, "Enter Password", Toast.LENGTH_SHORT).show();
                } else {
                    openPaypall(user, pass);
                }
            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                al.dismiss();

            }
        });


        builder.setView(viewdata);

        al = builder.create();

        al.show();

    }

    private void openPaypall(String user, String pass) {


        String id = "1";

        Cursor cursor = db.searchEmail();

        if (cursor != null && cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                String a = cursor.getString(0);

                String b = cursor.getString(1);

                String c = cursor.getString(2);

                if (a.equalsIgnoreCase(id)) {

                    if (user.equalsIgnoreCase(b) && pass.equalsIgnoreCase(c)) {


                        Intent intent = new Intent(Merchandise.this, Conditions.class);

                        Bundle bundle = new Bundle();

                        bundle.putString("email_key", "Paypall");

                        bundle.putString("link_key", "https://www.paypal.com/signin?returnUri=https%3A%2F%2Fwww.paypal.com%2Fcgi-bin%2Fwebscr%3fcmd%3d_account");

                        intent.putExtras(bundle);

                        startActivity(intent);

                        al.dismiss();

                    } else {
                        Toast.makeText(this, "Incorrect Details", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}

