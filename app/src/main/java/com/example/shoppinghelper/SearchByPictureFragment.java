package com.example.shoppinghelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class SearchByPictureFragment extends Fragment {

    Bitmap bitmap;
    ImageView imageTaken;
    Uri photoURI;
    String rawValue;
    CameraDispatch Cam = new CameraDispatch();
    DetectorDispatch Det = new DetectorDispatch();
    Button findInDatabase;

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View Layout = inflater.inflate(R.layout.fragment_search_by_picture, null);

        setUpUi(Layout);

        return Layout;
    }

    private void setUpUi(View layout) {
        imageTaken = layout.findViewById(R.id.camera_image);


        Button photo_button = layout.findViewById(R.id.take_picture);
        photo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        findInDatabase = layout.findViewById(R.id.findInDatabase);
        findInDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDatabase();
            }
        });
    }

    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try
            {
                photoFile = Cam.createImageFile();
            }
            catch (IOException ex)
            {

            }
            if (photoFile != null)
            {
                photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 1);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getActivity();
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Toast.makeText(getActivity(), "Image saved", Toast.LENGTH_SHORT).show();
            bitmap = null;
            Cam.addImageToGallery(Cam.currentPhotoPath, getActivity());
            try
            {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), photoURI);
                imageTaken.setImageBitmap(bitmap);

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            Det.runDetector(Cam.currentPhotoPath);
            detect();
        }
    }

    public void detect() {

        Det.detector.detectInImage(Det.image)
                .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionBarcode>>() {
                    @Override
                    public void onSuccess(List<FirebaseVisionBarcode> barcodes) {
                        Log.d("barkod", "3 proslo");
                        if (barcodes.isEmpty())
                        {
                            rawValue = null;
                            Toast.makeText(getActivity(), "No barcodes found.", Toast.LENGTH_SHORT).show();
                            findInDatabase.setVisibility(View.GONE);
                        }
                        else
                        {
                            for (FirebaseVisionBarcode barcode: barcodes)
                            {
                                Toast.makeText(getActivity(), "Barcode successfully found!", Toast.LENGTH_SHORT).show();
                                rawValue = barcode.getRawValue();
                                Log.d("barkod", rawValue);
                                findInDatabase.setVisibility(View.VISIBLE);
                                goToDatabase();
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Barcode detection failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void goToDatabase() {
        if (rawValue != null) {
            Intent intent = new Intent(getActivity(), PricesActivity.class);
            intent.putExtra("barcode", rawValue);
            intent.putExtra("op", "picture");
            startActivity(intent);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
            }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
