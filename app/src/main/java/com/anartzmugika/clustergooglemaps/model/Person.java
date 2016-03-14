package com.anartzmugika.clustergooglemaps.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/***************************************
 * Created by anartzmugika on 27/2/16.
 */
public class Person implements ClusterItem, Parcelable {
    public String name;
    public int profilePhoto;
    private LatLng mPosition;
    private String photo_url;

    public Person(LatLng position, String name, int pictureResource, String photo_url) {
        this.name = name;
        profilePhoto = pictureResource;
        mPosition = position;
        this.photo_url = photo_url;
    }

    public Person(Parcel in) {
        readFromParcel(in);
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    public String getName()
    {
        return this.name;
    }

    public void setmPosition(double lat, double lng)
    {
        mPosition = new LatLng(lat, lng);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(profilePhoto);
        dest.writeParcelable(mPosition, flags);
    }

    public void readFromParcel(Parcel in)
    {
        name = in.readString();
        profilePhoto = in.readInt();
        mPosition = in.readParcelable(LatLng.class.getClassLoader());;
    }

    public static final Parcelable.Creator<Person> CREATOR
            = new Parcelable.Creator<Person>() {
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}
