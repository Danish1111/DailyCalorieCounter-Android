package com.example.abc.dailycaloriecounter.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.abc.dailycaloriecounter.BuildConfig;
import com.example.abc.dailycaloriecounter.R;
import com.example.abc.dailycaloriecounter.activity.ClarifaiResponseActivity;
import com.example.abc.dailycaloriecounter.activity.MainActivity;
import com.example.abc.dailycaloriecounter.core.DailyCalorieCounterApplication;

import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class FragmentCamera extends Fragment {
    List<ClarifaiOutput<Concept>> myPridictionResult;
    public static final int CAMERA_REQUEST = 103;
    public static String CAPTURED_IMAGE_PATH;
    private  boolean isVisible = false;
    public static final int REQUEST_OPEN_ACTIVITY = 1000;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        textView.setText("" + myPridictionResult.get(0).data().get(0).name() + " ," + myPridictionResult.get(0).data().get(1).name() + " ," + myPridictionResult.get(0).data().get(2).name() + " , " + myPridictionResult.get(0).data().get(2).name());
     /*   try {
            captureImage(FragmentCamera.this, CAMERA_REQUEST);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

/*
    private void myMethod(File file) {
        try {
            final List<ClarifaiOutput<Concept>> predictionResults =
                    client.getDefaultModels().foodModel() // You can also do client.getModelByID("id") to get your custom models
                            .predict()
                            .withInputs(
                                    ClarifaiInput.forImage(file))
                            .executeSync()
                            .get();
            myPridictionResult = predictionResults;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/
/*
    public static void captureImage(Fragment fragment, int requestCode) throws IOException {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(DailyCalorieCounterApplication.getInstance().getPackageManager()) != null) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, getCaptureImageOutputUri());
            } else {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "_";
                File storageDir = Environment.getExternalStorageDirectory();
                File image = File.createTempFile(
                        imageFileName,  *//* prefix *//*
                        ".jpeg",         *//* suffix *//*
                        storageDir      *//* directory *//*
                );
                CAPTURED_IMAGE_PATH = image.getAbsolutePath();
            }
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            fragment.startActivityForResult(intent, requestCode);
        } else {
            Toast.makeText(DailyCalorieCounterApplication.getInstance(), "No Camera", Toast.LENGTH_LONG).show();
        }
    }

    public static Uri getCaptureImageOutputUri() throws IOException {
        Uri photoURI = FileProvider.getUriForFile(DailyCalorieCounterApplication.getInstance(),
                BuildConfig.APPLICATION_ID + ".cache",
                createImageFile());
        return photoURI;
    }

    private static File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStorageDirectory();
        File image = File.createTempFile(
                imageFileName,  *//* prefix *//*
                ".jpeg",         *//* suffix *//*
                storageDir      *//* directory *//*
        );

        String mCurrentPhotoPath = "file://" + image.getAbsolutePath();
        CAPTURED_IMAGE_PATH = mCurrentPhotoPath;
        return image;
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            final File outPutFile;
            if (requestCode == CAMERA_REQUEST) {
                String imagePath = CAPTURED_IMAGE_PATH.replace("file:///", "");
                try {
                    outPutFile = new File(/*"storage/emulated/0/JPEG_20180331_151438_922004775.jpeg"*/imagePath);
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                        setupImage(outPutFile, data);
                    }
                    Intent intent = new Intent(getActivity(), ClarifaiResponseActivity.class);
                    intent.putExtra("file",outPutFile);
                    startActivityForResult(intent, REQUEST_OPEN_ACTIVITY);
                    /* new Thread(new Runnable() {
                        public void run() {
                            myMethod(outPutFile);
                        }
                    }).start();*/


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else if(requestCode == REQUEST_OPEN_ACTIVITY){
                ((MainActivity) getActivity()).switchTab(1);
            }
            } else {
            if (requestCode == CAMERA_REQUEST) {
                try {
                    File file = new File(CAPTURED_IMAGE_PATH);
                    if (file.exists()) {
                        file.delete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            ((MainActivity) getActivity()).switchTab(1);

        }

    }

    public static Bitmap setupImage(File outputFileUri, Intent data) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;     // SAMPLE_SIZE = 2

        Bitmap tempBitmap = null;
        Bitmap bm = null;
        try {
            tempBitmap = (Bitmap) data.getExtras().get("data");
            bm = tempBitmap;


            FileOutputStream out = new FileOutputStream(outputFileUri.getPath());
            tempBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            bm = otherImageProcessing(outputFileUri, options);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bm;
    }

    private static Bitmap otherImageProcessing(File outputFileUri, BitmapFactory.Options options) {
        Bitmap bm = null;

        try {
            FileInputStream fis = new FileInputStream(outputFileUri.getPath());
            BufferedInputStream bis = new BufferedInputStream(fis);
            bm = BitmapFactory.decodeStream(bis, null, options);

            // cleaning up
            fis.close();
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bm;
    }

    public static void captureImage(Fragment fragment, int requestCode) throws IOException {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(DailyCalorieCounterApplication.getInstance().getPackageManager()) != null) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, getCaptureImageOutputUri());
            } else {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "_";
                File storageDir = Environment.getExternalStorageDirectory();
                File image = File.createTempFile(
                        imageFileName,  /* prefix */
                        ".jpeg",         /* suffix */
                        storageDir      /* directory */
                );
                CAPTURED_IMAGE_PATH = image.getAbsolutePath();
            }
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            fragment.startActivityForResult(intent, requestCode);
        } else {
            Toast.makeText(DailyCalorieCounterApplication.getInstance(), "No Camera", Toast.LENGTH_LONG).show();
        }
    }


    public static Uri getCaptureImageOutputUri() throws IOException {
        Uri photoURI = FileProvider.getUriForFile(DailyCalorieCounterApplication.getInstance(),
                BuildConfig.APPLICATION_ID + ".cache",
                createImageFile());
        return photoURI;
    }

    private static File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStorageDirectory();
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpeg",         /* suffix */
                storageDir      /* directory */
        );

        String mCurrentPhotoPath = "file://" + image.getAbsolutePath();
        CAPTURED_IMAGE_PATH = mCurrentPhotoPath;
        return image;
    }

    public static void captureImage(Activity fragment) throws IOException {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(DailyCalorieCounterApplication.getInstance().getPackageManager()) != null) {
//            ContentValues values = new ContentValues(1);
//            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
//            Uri fileUri = StarNovaApplication.getInstance().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, getCaptureImageOutputUri());
            } else {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "_";
                File storageDir = Environment.getExternalStorageDirectory();
                File image = File.createTempFile(
                        imageFileName,  /* prefix */
                        ".jpeg",         /* suffix */
                        storageDir      /* directory */
                );
                CAPTURED_IMAGE_PATH = image.getAbsolutePath();
            }
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            fragment.startActivityForResult(intent, CAMERA_REQUEST);
        } else {
            Toast.makeText(DailyCalorieCounterApplication.getInstance(), "No Camera", Toast.LENGTH_LONG).show();
        }
    }




    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isVisibleToUser) {
            try {
                captureImage(FragmentCamera.this,CAMERA_REQUEST);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }



}
