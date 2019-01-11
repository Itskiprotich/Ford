package com.fordperformanceclubofact.groupb.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fordperformanceclubofact.groupb.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsAll extends Fragment {

    private WebView webView;

    CircleImageView circleImageView;

    String url = "";

    AlertDialog al;

    Button Upload, RVSP;

    ImageView imageView;


    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 71;

    private StorageReference mStorageRef;


    String mapPath = "https://maps.google.com/?ll=37.0625,-95.677068&spn=29.301969,56.513672&t=h&z=4";

    public EventsAll() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_events_all, container, false);

        webView = (WebView) view.findViewById(R.id.web);

        imageView = (ImageView) view.findViewById(R.id.image);


        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl(mapPath);

        circleImageView = (CircleImageView) view.findViewById(R.id.click);

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openCalendar();
            }
        });
        RVSP = (Button) view.findViewById(R.id.aabb);

        Upload = (Button) view.findViewById(R.id.button2);

        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        RVSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openConfirm();
            }
        });

        return view;
    }

    private void openConfirm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setCancelable(false);

        builder.setTitle("Confirm");

        builder.setMessage("Your'e about to opt for live notifications");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(getContext(), "Live notifications will be send to you", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(getContext(), "You cancelled...!!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
    }

    private void uploadImage() {

//        Intent intent = new Intent();
//
//        intent.setType("image/*");
//
//        intent.setAction(Intent.ACTION_GET_CONTENT);

//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);

                imageView.setImageBitmap(bitmap);

                unpdate();

            } catch (IOException e) {

                e.printStackTrace();
            }

        }
    }


    private void unpdate() {

        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());

            progressDialog.setTitle("Uploading...");

            progressDialog.show();

            StorageReference ref = mStorageRef.child(UUID.randomUUID().toString());

            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            progressDialog.dismiss();

                            Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());

                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }

    }

    private void openCalendar() {

        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService((Context.LAYOUT_INFLATER_SERVICE));

        LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.datepicker, null, false);

        CalendarView calendarView = (CalendarView) linearLayout.getChildAt(0);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

            }
        });
        new AlertDialog.Builder(getContext())
                .setTitle("Event Calendar")
                .setMessage("View Events")
                .setView(linearLayout)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(getContext(), "Date captured, proceed to upload,,", Toast.LENGTH_SHORT).show();
                        openGalary();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();

    }

    private void openGalary() {

        Intent intent = new Intent();

        intent.setType("image/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

}
