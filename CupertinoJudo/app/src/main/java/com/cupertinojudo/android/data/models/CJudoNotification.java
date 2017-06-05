package com.cupertinojudo.android.data.models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.cupertinojudo.android.DateFormatterUtil;
import com.google.firebase.crash.FirebaseCrash;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * CupertinoJudoNotification
 */

public class CJudoNotification implements Parcelable {

    private String mTitle;
    private String mBody;
    private boolean mSeen;
    private final Date mTimestamp;

    public CJudoNotification(String title, String body, Date timestamp) {
        mTitle = title;
        mBody = body;
        mTimestamp = timestamp;
    }

    public CJudoNotification() {
        mTimestamp = null;
    }

    public void setSeen(boolean seen) {
        mSeen = seen;
    }

    public void setBody(String body) {
        mBody = body;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getBody() {
        return mBody;
    }


    public String getTimestamp(SimpleDateFormat format) {
        try {
            Date convertedDate;
            convertedDate = DateFormatterUtil.sDateTimeFormat.parse(mTimestamp.toString());
            return format.format(convertedDate);
        } catch (ParseException parseException) {
            FirebaseCrash.report(parseException);
        }
        return null;
    }

    public boolean wasSeen() {
        return mSeen;
    }

    public String getFormattedDate(Context context) {
        return DateFormatterUtil.formatTimestamp(mTimestamp, context);
    }

    public Date getTimeStamp() {
        return mTimestamp;
    }

    //Parcelable implementation

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mTimestamp.getTime());
        dest.writeString(mTitle);
        dest.writeString(mBody);
        dest.writeInt(mSeen ? 1 : 0);
    }

    public static final Parcelable.Creator<CJudoNotification> CREATOR
            = new Parcelable.Creator<CJudoNotification>() {
        public CJudoNotification createFromParcel(Parcel in) {
            return new CJudoNotification(in);
        }

        public CJudoNotification[] newArray(int size) {
            return new CJudoNotification[size];
        }
    };

    private CJudoNotification(Parcel in) {
        mTimestamp = new Date(in.readLong());
        mTitle = in.readString();
        mBody = in.readString();
        mSeen = (in.readInt() == 1);
    }

}
