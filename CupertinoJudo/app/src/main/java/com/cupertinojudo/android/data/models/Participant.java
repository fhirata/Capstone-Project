package com.cupertinojudo.android.data.models;

import android.content.ContentValues;
import android.database.Cursor;

import com.cupertinojudo.android.data.source.local.CJTPersistenceContract;
import com.google.firebase.crash.FirebaseCrash;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Participant {

    @SerializedName("Last Name")
    @Expose
    private String mLastName;
    @SerializedName("First Name")
    @Expose
    private String mFirstName;
    @SerializedName("DOB")
    @Expose
    private String mDOB;
    @SerializedName("Club")
    @Expose
    private String mClub;
    @SerializedName("Belt")
    @Expose
    private String mBelt;
    @SerializedName("Division")
    @Expose
    private String mDivision;
    @SerializedName("Weight")
    @Expose
    private Integer mWeight;
    @SerializedName("Pool")
    @Expose
    private String mPool;
    // Transient to avoid being included in the json by gson
    private transient int mTournamentYear;
    private transient Date mDateDOB;
    private static SimpleDateFormat sDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    private transient int mId;

    /**
     * No args constructor for use in serialization
     *
     */
    public Participant() {
    }

    /**
     *
     * @param lastName
     * @param weight
     * @param division
     * @param pool
     * @param club
     * @param belt
     * @param firstName
     * @param dOB
     */
    public Participant(String lastName, String firstName, String dOB, String club, String belt, String division, Integer weight, String pool) {
        super();
        mLastName = lastName;
        mFirstName = firstName;
        mDOB = dOB;
        mClub = club;
        mBelt = belt;
        mDivision = division;
        mWeight = weight;
        mPool = pool;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getDOB() {
        return mDOB;
    }

    public Date getDateDOB() {
        return mDateDOB;
    }
    
    public void setId(int id) {
        mId = id;
    }
    
    public int getId() {
        return mId;
    }

    public String getFullName() {
        return mFirstName + " " + mLastName;
    }
    /**
     * sestDOB will convert from regular string to Date Object
     * @param dOB - expecting "12/2/2016" like date
     */
    public void setDateDOB(String dOB) {
        mDOB = dOB;
        try {
            mDateDOB = sDateFormat.parse(dOB);
        } catch (ParseException parseException) {
            FirebaseCrash.report(parseException);
        }
    }

    public String getDate() {
        return sDateFormat.format(mDateDOB);
    }

    public String getClub() {
        return mClub;
    }

    public void setClub(String club) {
        mClub = club;
    }

    public String getBelt() {
        return mBelt;
    }

    public void setBelt(String belt) {
        mBelt = belt;
    }

    public String getDivision() {
        return mDivision;
    }

    public void setDivision(String division) {
        mDivision = division;
    }

    public Integer getWeight() {
        return mWeight;
    }

    public void setWeight(Integer weight) {
        mWeight = weight;
    }

    public String getPool() {
        return mPool;
    }

    public void setPool(String pool) {
        mPool = pool;
    }

    public int getTournamentYear() {
        return mTournamentYear;
    }

    public void setTournamentYear(int mTournamentYear) {
        this.mTournamentYear = mTournamentYear;
    }

    public static Participant from(Cursor cursor) {
        Participant participant = new Participant(
                cursor.getString(cursor.getColumnIndex(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_LAST)),
                cursor.getString(cursor.getColumnIndex(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_FIRST)),
                cursor.getString(cursor.getColumnIndex(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_DOB)),
                cursor.getString(cursor.getColumnIndex(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_CLUB)),
                cursor.getString(cursor.getColumnIndex(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_BELT)),
                cursor.getString(cursor.getColumnIndex(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_CATEGORY)),
                cursor.getInt(cursor.getColumnIndex(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_WEIGHT)),
                cursor.getString(cursor.getColumnIndex(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_POOL))
        );
        participant.setId(cursor.getInt(cursor.getColumnIndex(CJTPersistenceContract.CJudoParticipantEntry._ID)));
        return participant;
    }
    
    public static ContentValues from(Participant participant) {

        ContentValues values = new ContentValues();
        values.put(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_TOURNAMENT_YEAR, participant.getTournamentYear());
        values.put(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_FIRST, participant.getFirstName());
        values.put(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_LAST, participant.getLastName());
        values.put(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_CLUB, participant.getClub());
        values.put(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_BELT, participant.getBelt());
        values.put(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_WEIGHT, participant.getWeight());
        values.put(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_DOB, (participant.getDateDOB()).getTime()); // Make it a long before inserting into db
        values.put(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_CATEGORY, participant.getDivision());
        values.put(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_POOL, participant.getPool());

        return values;
    }
}