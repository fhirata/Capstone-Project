package com.test.cupertinojudo.data.source.local;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.test.cupertinojudo.data.source.local.CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_DOB;
import static com.test.cupertinojudo.data.source.local.CJTPersistenceContract.CJudoParticipantEntry.TABLE_NAME;
import static com.test.cupertinojudo.data.source.local.CJTPersistenceContract.CJudoParticipantEntry.buildParticipantUri;
import static com.test.cupertinojudo.data.source.local.CJTPersistenceContract.CONTENT_AUTHORITY;
import static com.test.cupertinojudo.data.source.local.CJTPersistenceContract.PATH_CATEGORY;
import static com.test.cupertinojudo.data.source.local.CJTPersistenceContract.PATH_PARTICIPANT;
import static com.test.cupertinojudo.data.source.local.CJTPersistenceContract.PATH_POOL;
import static com.test.cupertinojudo.data.source.local.CJTPersistenceContract.PATH_TOURNAMENT;

/**
 * Created by fabiohh on 5/10/17.
 */

public class CJTProvider extends ContentProvider {
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private CJTDbHelper mOpenHelper;

    public static final int PARTICIPANTS = 100;
    public static final int PARTICIPANTS_WITH_YEAR = 101;
    public static final int PARTICIPANTS_WITH_YEAR_CATEGORY = 102;
    public static final int PARTICIPANTS_WITH_YEAR_CATEGORY_POOLNAME = 103;
    public static final int CATEGORIES_WITH_YEAR = 104;
    public static final int POOLS_WITH_YEAR_CATEGORY = 105;
    public static final int PARTICIPANT_WITH_ID = 106;

    // TODO: Using this for GROUP BY feature for retrieving categories and pools
    private static final SQLiteQueryBuilder sTournamentQueryBuilder;
    static {
        sTournamentQueryBuilder = new SQLiteQueryBuilder();

        sTournamentQueryBuilder.setTables(
                TABLE_NAME);
    }

    // tournament.year = ?
    private static final String sTournamentParticipantsSelection =
    TABLE_NAME +
            "." + CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_TOURNAMENT_YEAR + " = ? ";

    // tournament.year = ? AND category = ?
    private static final String sTournamentParticipantsWithCategorySelection =
            TABLE_NAME +
            "." + CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_TOURNAMENT_YEAR + " = ? AND " +
            "." + CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_CATEGORY + " = ?";

    // tournament.year = ? AND category = ? AND pool = ?
    private static final String sTournamentParticipantsWithCategoryWithPoolNameSelection =
            TABLE_NAME +
                    "." + CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_TOURNAMENT_YEAR + " = ? AND " +
                    "." + CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_CATEGORY + " = ?" +
                    "." + CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_POOL + " = ?";

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_TOURNAMENT, PARTICIPANTS);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_TOURNAMENT + "/#", PARTICIPANTS_WITH_YEAR);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_TOURNAMENT + "/" + PATH_PARTICIPANT + "/#",PARTICIPANT_WITH_ID);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_TOURNAMENT + "/#/*", PARTICIPANTS_WITH_YEAR_CATEGORY);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_TOURNAMENT + "/#/*/*", PARTICIPANTS_WITH_YEAR_CATEGORY_POOLNAME);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_TOURNAMENT + "/" + PATH_CATEGORY + "/#", CATEGORIES_WITH_YEAR);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_TOURNAMENT + "/" + PATH_POOL + "/#/*", POOLS_WITH_YEAR_CATEGORY);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new CJTDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor retCursor;

        switch (sUriMatcher.match(uri)) {
            case PARTICIPANTS:
            case PARTICIPANTS_WITH_YEAR:
            case PARTICIPANTS_WITH_YEAR_CATEGORY:
            case PARTICIPANTS_WITH_YEAR_CATEGORY_POOLNAME:
                retCursor = getTournamentParticipants(uri, projection, selection, selectionArgs, sortOrder);
                break;
            case CATEGORIES_WITH_YEAR:
                retCursor = getTournamentCategories(uri, projection, selection, selectionArgs, sortOrder);
                break;
            case POOLS_WITH_YEAR_CATEGORY:
                retCursor = getTournamentPools(uri, projection, selection, selectionArgs, sortOrder);
                break;
            case PARTICIPANT_WITH_ID:
                retCursor = getParticipant(uri, projection, selection, selectionArgs, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri:" + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }

    private Cursor getTournamentPools(@NonNull Uri uri, @NonNull String[] projection, @NonNull String selection, @NonNull String[] selectionArgs, @Nullable String sortOrder) {
        return mOpenHelper.getReadableDatabase().query(
                TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_POOL,
                null,
                sortOrder
        );
    }

    private Cursor getTournamentCategories(@NonNull Uri uri, @NonNull String[] projection, @NonNull String selection, @NonNull String[] selectionArgs, @Nullable String sortOrder) {
        return mOpenHelper.getReadableDatabase().query(
                TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_CATEGORY,
                null,
                sortOrder
        );
    }

    private Cursor getTournamentParticipants(@NonNull Uri uri, @NonNull String[] projection, @NonNull String selection, @NonNull String[] selectionArgs, @Nullable String sortOrder) {
        return mOpenHelper.getReadableDatabase().query(
                TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getParticipant(@NonNull Uri uri, @NonNull String[] projection, @NonNull String selection, @NonNull String[] selectionArgs, @Nullable String sortOrder) {
        long participantId = CJTPersistenceContract.CJudoParticipantEntry.getParticipantIdFromUri(uri);
        return mOpenHelper.getReadableDatabase().query(
                TABLE_NAME,
                projection,
                CJTPersistenceContract.CJudoParticipantEntry._ID + " = ? ",
                new String[]{Long.toString(participantId)},
                null,
                null,
                sortOrder
        );
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch(match) {
            case PARTICIPANTS: // current year
            case PARTICIPANTS_WITH_YEAR:
            case PARTICIPANTS_WITH_YEAR_CATEGORY:
                return CJTPersistenceContract.CJudoParticipantEntry.CONTENT_PARTICIPANTS_TYPE;
            case PARTICIPANTS_WITH_YEAR_CATEGORY_POOLNAME:
                return CJTPersistenceContract.CJudoParticipantEntry.CONTENT_PARTICIPANT_ITEM_TYPE;
            case CATEGORIES_WITH_YEAR:
                return CJTPersistenceContract.CJudoParticipantEntry.CONTENT_CATEGORIES_TYPE;
            case POOLS_WITH_YEAR_CATEGORY:
                return CJTPersistenceContract.CJudoParticipantEntry.CONTENT_POOLS_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case PARTICIPANTS:
            case PARTICIPANTS_WITH_YEAR:
            case PARTICIPANTS_WITH_YEAR_CATEGORY:
            case PARTICIPANTS_WITH_YEAR_CATEGORY_POOLNAME:
                normalizeDate(values);
                long _id = db.insert(TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = buildParticipantUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new android.database.SQLException("Failed to insert row into " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        switch (match) {
            case PARTICIPANTS:
            case PARTICIPANTS_WITH_YEAR:
            case PARTICIPANTS_WITH_YEAR_CATEGORY:
            case PARTICIPANTS_WITH_YEAR_CATEGORY_POOLNAME:
                rowsDeleted = db.delete(TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    private void normalizeDate(ContentValues values) {
        // normalize the date value
        if (values.containsKey(COLUMN_NAME_DOB)) {
            long dateValue = values.getAsLong(COLUMN_NAME_DOB);
            values.put(COLUMN_NAME_DOB, Long.valueOf(dateValue));
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsImpacted;

        switch (match) {
            case PARTICIPANTS:
            case PARTICIPANTS_WITH_YEAR:
            case PARTICIPANTS_WITH_YEAR_CATEGORY:
            case PARTICIPANTS_WITH_YEAR_CATEGORY_POOLNAME:
                rowsImpacted = db.update(TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsImpacted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsImpacted;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PARTICIPANTS:
            case PARTICIPANTS_WITH_YEAR:
            case PARTICIPANTS_WITH_YEAR_CATEGORY:
            case PARTICIPANTS_WITH_YEAR_CATEGORY_POOLNAME:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        normalizeDate(value);
                        long _id = db.insert(CJTPersistenceContract.CJudoParticipantEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }
}
