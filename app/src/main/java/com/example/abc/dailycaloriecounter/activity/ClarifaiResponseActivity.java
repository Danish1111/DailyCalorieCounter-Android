package com.example.abc.dailycaloriecounter.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.example.abc.dailycaloriecounter.R;
import com.example.abc.dailycaloriecounter.adapter.ClarifaiResponseAdapter;
import com.example.abc.dailycaloriecounter.core.DailyCalorieCounterApplication;
import com.example.abc.dailycaloriecounter.model.ClarifaiResponse;
import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcDialog;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.dto.input.ClarifaiImage;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class ClarifaiResponseActivity extends AppCompatActivity {
    private ClarifaiClient client;
    private File file;
    private List<ClarifaiOutput<Concept>> myPridictionResult;
    SimpleArcDialog mDialog;
    private ImageView ivFood;
    private RecyclerView recyclerView;
    private ClarifaiResponseAdapter clarifaiResponseAdapter;
    private List<ClarifaiResponse> clarifaiResponseList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clarifai_activity_layout);
        file = (File) getIntent().getSerializableExtra("file");
        ivFood = (ImageView) findViewById(R.id.iv_food);
        client = new ClarifaiBuilder("bc32617ca45b4967be231c8547d52a68")
                .buildSync();
        clarifaiResponseList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.rv_food_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        clarifaiResponseAdapter = new ClarifaiResponseAdapter(clarifaiResponseList);
        recyclerView.setAdapter(clarifaiResponseAdapter);
        mDialog = new SimpleArcDialog(this);
        mDialog.setConfiguration(new ArcConfiguration(this));
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();

        setImage();
        myMethod(file);


       /* new Thread(new Runnable() {
            public void run() {
            }
        }).start();*/
    }

    private void setImage() {
        Picasso.with(ivFood.getContext())
                .load(file)
                .config(Bitmap.Config.RGB_565)
                .fit().centerCrop()
                .into(ivFood);
    }

    private void myMethod(final File file) {
        new AsyncTask<Void, Void, clarifai2.api.ClarifaiResponse<List<ClarifaiOutput<Concept>>>>() {
            @Override protected clarifai2.api.ClarifaiResponse<List<ClarifaiOutput<Concept>>> doInBackground(Void... params) {
                return client.getModelByID("Foods-Indian").executeSync().get().asConceptModel().predict()
                        .withInputs(ClarifaiInput.forImage(file))
                        .executeSync();
            }

            @Override protected void onPostExecute(clarifai2.api.ClarifaiResponse<List<ClarifaiOutput<Concept>>> response) {
                if (!response.isSuccessful()) {
                    mDialog.cancel();
                    return;
                }
                final List<ClarifaiOutput<Concept>> predictions = response.get();
                if (predictions!=null && !predictions.isEmpty()) {
                    mDialog.cancel();
                    setList(predictions);
                    return;
                }else {
                    mDialog.cancel();

                }

            }

        }.execute();


/*
        try {
            final List<ClarifaiOutput<Concept>> predictionResults =
                    client.getDefaultModels().foodModel() // You can also do client.getModelByID("id") to get your custom models
                            .predict()
                            .withInputs(
                                    ClarifaiInput.forImage(file))
                            .executeSync()
                            .get();
            myPridictionResult = predictionResults;
            mDialog.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
       /* runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setList();
            }
        });*/
    }

    private void setList(List<ClarifaiOutput<Concept>> myPridictionResult) {
        if (myPridictionResult != null) {
            if (myPridictionResult.get(0) != null) {
                if (myPridictionResult.get(0).data().size() > 0) {
                    clarifaiResponseList.clear();
                    for (int i = 0; i < myPridictionResult.get(0).data().size(); i++) {
                        ClarifaiResponse clarifaiResponse = new ClarifaiResponse();
                        clarifaiResponse.setName(myPridictionResult.get(0).data().get(i).name());
                        clarifaiResponse.setValue(myPridictionResult.get(0).data().get(i).value());
                        clarifaiResponseList.add(clarifaiResponse);
                    }
                    clarifaiResponseAdapter.notifyDataSetChanged();

                }
            }
        }
    }


    public static boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) DailyCalorieCounterApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        /*NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo bluetooth = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_BLUETOOTH);
        NetworkInfo wimax = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIMAX);*/
        return activeNetwork != null && activeNetwork.isAvailable();
        /*if (wifi == null && mobile == null && bluetooth == null && wimax == null) {
            return false;
        }

        if (wifi != null && wifi.isConnected()) {
            return true;
        }

        if (mobile != null && mobile.isConnected()) {
            return true;
        }

        if (bluetooth != null && bluetooth.isConnected()) {
            return true;
        }

        if (wimax != null && wimax.isConnected()) {
            return true;
        }

        return false;*/
    }

}
