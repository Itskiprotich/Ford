package com.fordperformanceclubofact.groupb.Membership;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fordperformanceclubofact.groupb.Contact.AllContact;
import com.fordperformanceclubofact.groupb.Database.Database;
import com.fordperformanceclubofact.groupb.Manage.Manager;
import com.fordperformanceclubofact.groupb.Merchandise.Merchandise;
import com.fordperformanceclubofact.groupb.R;
import com.fordperformanceclubofact.groupb.Sponsor.Sponsor;
import com.fordperformanceclubofact.groupb.Terms.Conditions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class Membership extends AppCompatActivity {

    private Toolbar toolbar;

    private Button button;

    private TextView textView;

    FloatingActionButton floatingActionButton;

    AlertDialog al;

    String email;

    Database database;

    CircleImageView circleImageView;

    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 71;

    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership);
        database = new Database(this);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        circleImageView=(CircleImageView)findViewById(R.id.aa);

        email = getIntent().getStringExtra("email_key");

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(email);

        getSupportActionBar().setSubtitle("Active\tExpiry Feb 2019");

        button = (Button) findViewById(R.id.contact);

        textView = (TextView) findViewById(R.id.renew);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editUser();
            }
        });

    }

    private void editUser() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                circleImageView.setImageBitmap(bitmap);

                unpdate();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void unpdate() {
        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);

            progressDialog.setTitle("Uploading...");

            progressDialog.show();

            StorageReference ref = mStorageRef.child(UUID.randomUUID().toString());

            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            progressDialog.dismiss();

                            Toast.makeText(Membership.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Membership.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());

                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.aa) {

            Intent intent = new Intent(Membership.this, Merchandise.class);

            startActivity(intent);

            return true;

        } else if (id == R.id.bb) {

            Intent intent = new Intent(Membership.this, Sponsor.class);

            startActivity(intent);

            return true;

        } else if (id == R.id.cc) {


            username();

            return true;

        }
        else if (id == R.id.dd) {


            Updates();

            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void Updates() {



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
                    Toast.makeText(Membership.this, "Enter Username", Toast.LENGTH_SHORT).show();
                } else if (pass.isEmpty()) {

                    Toast.makeText(Membership.this, "Enter Password", Toast.LENGTH_SHORT).show();
                } else {
                    openLog(user, pass);
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

    private void openLog(String user, String pass) {

        String id = "1";

        Cursor cursor = database.searchEmail();

        if (cursor != null && cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                String a = cursor.getString(0);

                String b = cursor.getString(1);

                String c = cursor.getString(2);

                if (a.equalsIgnoreCase(id)) {

                    if (user.equalsIgnoreCase(b) && pass.equalsIgnoreCase(c)) {

                        Intent intent=new Intent(Membership.this, Manager.class);

                        Bundle bundle = new Bundle();

                        bundle.putInt("email_key",2);

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

    private void username() {

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
                    Toast.makeText(Membership.this, "Enter Username", Toast.LENGTH_SHORT).show();
                } else if (pass.isEmpty()) {

                    Toast.makeText(Membership.this, "Enter Password", Toast.LENGTH_SHORT).show();
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

        Cursor cursor = database.searchEmail();

        if (cursor != null && cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                String a = cursor.getString(0);

                String b = cursor.getString(1);

                String c = cursor.getString(2);

                if (a.equalsIgnoreCase(id)) {

                    if (user.equalsIgnoreCase(b) && pass.equalsIgnoreCase(c)) {


                        Intent intent = new Intent(Membership.this, Manager.class);

                        Bundle bundle = new Bundle();

                        bundle.putInt("email_key",0);

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

    public void Members(View view) {

        Intent intent = new Intent(Membership.this, AllContact.class);

        startActivity(intent);
    }

    public void Renew(View view) {

        Intent intent = new Intent(Membership.this, Conditions.class);

        Bundle bundle = new Bundle();

        bundle.putString("email_key", "Renewal");

        bundle.putString("link_key", "http://www.fordperformance.com/");

        intent.putExtras(bundle);

        startActivity(intent);

    }
}
