package com.hobber89.androidOpenSpecificFileType;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;

public class FileContentModel implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public FileContentModel createFromParcel(Parcel parcel) {
            return new FileContentModel(parcel);
        }

        public FileContentModel[] newArray(int size) {
            return new FileContentModel[size];
        }
    };

    private String content;

    public FileContentModel(String content) {
        this.content = content;
    }

    public FileContentModel(Parcel parcel) {
        content = parcel.readString();
    }

    public String getContent() {
        return content;
    }

    public JSONObject toJson() {
        try {
            JSONObject object = new JSONObject();
            object.put("content", content);
            return object;
        } catch (JSONException error) {
            return null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(content);
    }
}
