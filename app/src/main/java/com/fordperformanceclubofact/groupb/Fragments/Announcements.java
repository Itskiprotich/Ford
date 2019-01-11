package com.fordperformanceclubofact.groupb.Fragments;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fordperformanceclubofact.groupb.MainActivity;
import com.fordperformanceclubofact.groupb.Membership.Membership;
import com.fordperformanceclubofact.groupb.Merchandise.Merchandise;
import com.fordperformanceclubofact.groupb.Model.Anounce;
import com.fordperformanceclubofact.groupb.R;
import com.fordperformanceclubofact.groupb.Updates.RecentUpdates;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class Announcements extends Fragment {

    private Button button;

    private EditText editText;

    FirebaseDatabase database;

    DatabaseReference reference;

    public Announcements() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_announcements, container, false);

        button = (Button) view.findViewById(R.id.send);

        editText = (EditText) view.findViewById(R.id.aa);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });
        return view;
    }

    private void check() {
        String message = editText.getText().toString();

        if (message.isEmpty()) {

            Toast.makeText(getContext(), "enter Announcement", Toast.LENGTH_SHORT).show();
        } else {
            upload(message);
        }
    }

    private void upload(final String message) {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());

        progressDialog.setIndeterminate(true);

        progressDialog.setMessage("Sending Announcement...");

        progressDialog.show();

        new android.os.Handler().postDelayed(

                new Runnable() {

                    public void run() {

                        wow(message);

                        progressDialog.dismiss();

                    }
                }, 3000);


    }

    private void wow(final String message) {

        Anounce upload = new Anounce(message);

        database = FirebaseDatabase.getInstance();

        reference = database.getReference("Announcements");

        //adding an upload to firebase database
        String uploadId = reference.push().getKey();

        reference.child(uploadId).setValue(upload);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Toast.makeText(getContext(), "Announcements Sent Successfully", Toast.LENGTH_SHORT).show();

                notifyUser(message);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(getContext(), "Failed..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void notifyUser(String message) {

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getContext())
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("Ford Enterprises")
                        .setAutoCancel(true)
                        .setContentText(message);

        Intent notificationIntent = new Intent(getContext(), RecentUpdates.class);

        PendingIntent contentIntent = PendingIntent.getActivity(getContext(), 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

}
