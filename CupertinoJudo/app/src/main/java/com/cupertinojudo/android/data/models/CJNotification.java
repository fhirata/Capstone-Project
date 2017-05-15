package com.cupertinojudo.android.data.models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.cupertinojudo.android.DateFormatterUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * CupertinoJudoNotification
 */

public class CJNotification implements Parcelable {

    private String mTitle;
    private String mBody;
    private boolean mSeen;
    private final Date mTimestamp;

    public CJNotification(String title, String body, Date timestamp) {
        mTitle = title;
        mBody = body;
        mTimestamp = timestamp;
    }

    public CJNotification() {
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
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean getIfSeen() {
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

    public static final Parcelable.Creator<CJNotification> CREATOR
            = new Parcelable.Creator<CJNotification>() {
        public CJNotification createFromParcel(Parcel in) {
            return new CJNotification(in);
        }

        public CJNotification[] newArray(int size) {
            return new CJNotification[size];
        }
    };

    private CJNotification(Parcel in) {
        mTimestamp = new Date(in.readLong());
        mTitle = in.readString();
        mBody = in.readString();
        mSeen = (in.readInt() == 1);
    }

}
