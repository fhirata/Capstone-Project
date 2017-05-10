package com.test.cupertinojudo.data.source.local;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.test.cupertinojudo.data.source.local.CJTPersistenceContract.CATEGORY;
import static com.test.cupertinojudo.data.source.local.CJTPersistenceContract.CONTENT_AUTHORITY;
import static com.test.cupertinojudo.data.source.local.CJTPersistenceContract.PATH_TOURNAMENT;
import static com.test.cupertinojudo.data.source.local.CJTPersistenceContract.POOLNAME;

/**
 * Created by fabiohh on 5/10/17.
 */

public class CJTProvider extends ContentProvider {
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private CJTDbHelper mOpenHelper;

    static final int PARTICIPANTS = 100;
    static final int PARTICIPANTS_WITH_YEAR = 101;
    static final int PARTICIPANTS_WITH_YEAR_CATEGORY = 102;
    static final int PARTICIPANTS_WITH_YEAR_CATEGORY_POOLNAME = 103;

    private static final SQLiteQueryBuilder sTournamentQueryBuilder;

    // tournament.year = ?
    private static final String sTournamentParticipantsSelection =
    CJTPersistenceContract.CJudoParticipantEntry.TABLE_NAME +
            "." + CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_TOURNAMENT_YEAR + " = ? ";

    // tournament.year = ? AND category = ?
    private static final String sTournamentParticipantsWithCategorySelection =
            CJTPersistenceContract.CJudoParticipantEntry.TABLE_NAME +
            "." + CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_TOURNAMENT_YEAR + " = ? AND " +
            "." + CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_CATEGORY + " = ?";

    // tournament.year = ? AND category = ? AND pool = ?
    private static final String sTournamentParticipantsWithCategoryWithPoolNameSelection =
            CJTPersistenceContract.CJudoParticipantEntry.TABLE_NAME +
                    "." + CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_TOURNAMENT_YEAR + " = ? AND " +
                    "." + CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_CATEGORY + " = ?" +
                    "." + CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_POOL + " = ?";

    static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_TOURNAMENT, PARTICIPANTS);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_TOURNAMENT + "/#", PARTICIPANTS_WITH_YEAR);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_TOURNAMENT + "/#/" + CATEGORY + "/*", PARTICIPANTS_WITH_YEAR_CATEGORY);
        uriMatcher.addURI(CONTENT_AUTHORITY, PATH_TOURNAMENT + "/#/" + CATEGORY + "/*" + POOLNAME + "/*", PARTICIPANTS_WITH_YEAR_CATEGORY_POOLNAME);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {

            case PARTICIPANTS:
                retCursor = getCurrentTournamentParticipants(uri, projection, sortOrder);
                break;
            case PARTICIPANTS_WITH_YEAR:
                retCursor = getCurrentTournamentParticipantsByYear(uri, projection, selection, selectionArgs, sortOrder);
                break;
            case PARTICIPANTS_WITH_YEAR_CATEGORY:
                retCursor = getCurrentTournamentParticipants(uri, projection, sortOrder);
                break;
            case PARTICIPANTS_WITH_YEAR_CATEGORY_POOLNAME:
                retCursor = getCurrentTournamentParticipants(uri, projection, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri:" + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }
    private Cursor getCurrentTournamentParticipantsByYear(@NonNull Uri uri, @NonNull String[] projection, @NonNull String selection, @NonNull String[] selectionArgs, @Nullable String sortOrder) {
        return mOpenHelper.getReadableDatabase().query(
                CJTPersistenceContract.CJudoParticipantEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    private Cursor getCurrentTournamentParticipants(@NonNull Uri uri, @NonNull String[] projection, @Nullable String sortOrder) {
        String currentYear = currentYearDateFormat.format(today);

        return sTournamentParticipantsSelection.
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
