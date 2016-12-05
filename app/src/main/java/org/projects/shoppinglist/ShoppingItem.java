package org.projects.shoppinglist;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Alice on 15/09/2016.
 */
public class ShoppingItem implements Parcelable {

    private String itemName;
    private int itemCount;
// constructors
    public ShoppingItem(String name) {
        this.itemName = name;
        this.itemCount = 0;
    }

    public ShoppingItem(String name, int quantity) {
        this.itemName = name;
        this.itemCount = quantity;
    }

    //getters

    public String getItemName() {
        return itemName;
    }

    public int getItemCount() {
        return itemCount;
    }




    public void IncreaseCount() {
        this.itemCount++;
    }

    public void IncreaseCount(int count) {
        this.itemCount += count;
    }

    @Override
    public String toString() {
        if (itemCount > 0) {
            return itemName + ", " + itemCount;
        } else {
            return itemName;
        }
    }

    //Parcelation
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemName);
        dest.writeInt(itemCount);
    }

    // Must be the same order in which the attributes have been put in
    public ShoppingItem(Parcel in) {
        itemName = in.readString();
        itemCount = in.readInt();
    }

    // Creator
    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public ShoppingItem createFromParcel(Parcel in) {
            return new ShoppingItem(in);
        }

        public ShoppingItem[] newArray(int size) {
            return new ShoppingItem[size];
        }
    };

    //firebase setup
    public ShoppingItem() {
    }

    public String SetItemName(String name) {
        this.itemName = name;
        return itemName;
    }

    public int SetItemCount(int count) {
        this.itemCount = count;
        return itemCount;
    }
}

