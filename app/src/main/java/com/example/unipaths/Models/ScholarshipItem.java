package com.example.unipaths.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class ScholarshipItem implements Parcelable{

    String itemName;
    String imageURL;
    String itemDescription;
    String itemWebsite;

    public String getItemWebsite() {
        return itemWebsite;
    }


    public ScholarshipItem(String itemName, String imageURL, String itemDescription, String itemWebsite) {
        this.itemName = itemName;
        this.imageURL = imageURL;
        this.itemDescription = itemDescription;
        this.itemWebsite = itemWebsite;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getItemName() {
        return itemName;
    }

    // Parcelable implementation
    protected ScholarshipItem(Parcel in) {
        itemName = in.readString();
        imageURL = in.readString();
        itemDescription = in.readString();
        itemWebsite = in.readString();
    }

    public static final Parcelable.Creator<ScholarshipItem> CREATOR = new Parcelable.Creator<ScholarshipItem>() {
        @Override
        public ScholarshipItem createFromParcel(Parcel in) {
            return new ScholarshipItem(in);
        }

        @Override
        public ScholarshipItem[] newArray(int size) {
            return new ScholarshipItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(itemName);
        dest.writeString(imageURL);
        dest.writeString(itemDescription);
        dest.writeString(itemWebsite);
    }
}
