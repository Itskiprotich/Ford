package com.fordperformanceclubofact.groupb.Fragments;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.Toast;

import com.fordperformanceclubofact.groupb.Membership.Membership;
import com.fordperformanceclubofact.groupb.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class Notify extends Fragment {    private Button button;

    private CheckedTextView AA,BB;

    public Notify() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_notify, container, false);
        button = (Button) view.findViewById(R.id.note);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), "Captured..", Toast.LENGTH_SHORT).show();
            }
        });
        AA=(CheckedTextView)view.findViewById(R.id.aa);

        BB=(CheckedTextView)view.findViewById(R.id.bb);

        AA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AA.toggle();
            }
        });
        BB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BB.toggle();
            }
        });
        return view;
    }


}
