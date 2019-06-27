package com.example.shoppinghelper;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;

import java.util.List;

public class DetectorDispatch {

    public FirebaseVisionBarcodeDetector detector;
    public FirebaseVisionImage image;

    public void runDetector(String photoPath) {
        FirebaseVisionBarcodeDetectorOptions options =
                new FirebaseVisionBarcodeDetectorOptions.Builder()
                        .setBarcodeFormats(
                                FirebaseVisionBarcode.FORMAT_UPC_A,
                                FirebaseVisionBarcode.FORMAT_UPC_E,
                                FirebaseVisionBarcode.FORMAT_EAN_8,
                                FirebaseVisionBarcode.FORMAT_EAN_13,
                                FirebaseVisionBarcode.FORMAT_QR_CODE,
                                FirebaseVisionBarcode.FORMAT_AZTEC)
                        .build();
        Log.d("barkod", "1 proslo");
        image = FirebaseVisionImage.fromBitmap(BitmapFactory.decodeFile(photoPath));
//        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(testImage);
        detector = FirebaseVision.getInstance()
                .getVisionBarcodeDetector(options);
        Log.d("barkod", "2 proslo");
    }
}
