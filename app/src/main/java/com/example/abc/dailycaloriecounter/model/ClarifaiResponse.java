package com.example.abc.dailycaloriecounter.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ClarifaiResponse implements Parcelable {
    private String id;
    private String name;
    private String appID;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    private double value;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.appID);
        dest.writeDouble(this.value);
    }

    public ClarifaiResponse() {
    }

    protected ClarifaiResponse(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.appID = in.readString();
        this.value = in.readDouble();
    }

    public static final Parcelable.Creator<ClarifaiResponse> CREATOR = new Parcelable.Creator<ClarifaiResponse>() {
        @Override
        public ClarifaiResponse createFromParcel(Parcel source) {
            return new ClarifaiResponse(source);
        }

        @Override
        public ClarifaiResponse[] newArray(int size) {
            return new ClarifaiResponse[size];
        }
    };
}
