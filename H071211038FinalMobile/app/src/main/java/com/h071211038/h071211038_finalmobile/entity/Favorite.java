package com.h071211038.h071211038_finalmobile.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Favorite implements Parcelable {
    private int id, dataId, icon;
    private String backdropPath, posterPath, title, releaseDate, overview;
    private Double voteAverage;
    public Favorite() {
    }

    public Favorite(int id, int dataId, int icon, String backdropPath, String posterPath, String title, String releaseDate, String overview, Double voteAverage) {
        this.id = id;
        this.dataId = dataId;
        this.icon = icon;
        this.backdropPath = backdropPath;
        this.posterPath = posterPath;
        this.title = title;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.voteAverage = voteAverage;
    }

    protected Favorite(Parcel in) {
        id = in.readInt();
        dataId = in.readInt();
        icon = in.readInt();
        backdropPath = in.readString();
        posterPath = in.readString();
        title = in.readString();
        releaseDate = in.readString();
        overview = in.readString();
        if (in.readByte() == 0) {
            voteAverage = null;
        } else {
            voteAverage = in.readDouble();
        }
    }

    public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel in) {
            return new Favorite(in);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };

    public int getId() {
        return id;
    }

    public int getDataId() {
        return dataId;
    }

    public int getIcon() {
        return icon;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(dataId);
        parcel.writeInt(icon);
        parcel.writeString(backdropPath);
        parcel.writeString(posterPath);
        parcel.writeString(title);
        parcel.writeString(releaseDate);
        parcel.writeString(overview);
        if (voteAverage == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(voteAverage);
        }
    }
}
