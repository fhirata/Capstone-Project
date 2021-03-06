package com.cupertinojudo.android.data.source.local;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import com.cupertinojudo.android.BuildConfig;

import java.util.List;

/**
 *
 */

public class CJTPersistenceContract {
    public static final String CONTENT_AUTHORITY = BuildConfig.APPLICATION_ID;
    public static final String PATH_TOURNAMENT = "tournament";
    public static final String PATH_CATEGORY = "category";
    public static final String PATH_POOL = "pool";
    public static final String PATH_PARTICIPANT = "participant";
    private static final String CONTENT_SCHEME = "content://";
    public static final Uri BASE_CONTENT_URI = Uri.parse(CONTENT_SCHEME + CONTENT_AUTHORITY);

    private static final String SEPARATOR = "/";

    private CJTPersistenceContract() {
    }

    public static abstract class CJudoParticipantEntry implements BaseColumns {
        public static final String TABLE_NAME = "tournament";
        public static final String COLUMN_NAME_TOURNAMENT_YEAR = "tournament_year";
        public static final String COLUMN_NAME_FIRST = "first";
        public static final String COLUMN_NAME_LAST = "last";
        public static final String COLUMN_NAME_CLUB = "club";
        public static final String COLUMN_NAME_BELT = "belt";
        public static final String COLUMN_NAME_WEIGHT = "weight";
        public static final String COLUMN_NAME_DOB = "dob";
        public static final String COLUMN_NAME_CATEGORY = "category";
        public static final String COLUMN_NAME_POOL = "pool";

        public static String[] PARTICIPANTS_COLUMNS = new String[]{
                CJTPersistenceContract.CJudoParticipantEntry._ID,
                CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_TOURNAMENT_YEAR,
                CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_FIRST,
                CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_LAST,
                CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_CLUB,
                CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_BELT,
                CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_WEIGHT,
                CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_DOB,
                CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_CATEGORY,
                CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_POOL
        };

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).build();

        public static final String CONTENT_PARTICIPANTS_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + CJudoParticipantEntry.TABLE_NAME;

        public static final String CONTENT_PARTICIPANT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + CJudoParticipantEntry.TABLE_NAME;

        public static final String CONTENT_CATEGORIES_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + CJudoParticipantEntry.TABLE_NAME + "/" + CJudoParticipantEntry.COLUMN_NAME_CATEGORY;

        public static final String CONTENT_POOLS_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + CJudoParticipantEntry.TABLE_NAME + "/" + CJudoParticipantEntry.COLUMN_NAME_POOL;

        public static final Uri CATEGORIES_CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).appendPath(COLUMN_NAME_CATEGORY).build();

        public static final Uri POOLS_CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).appendPath(COLUMN_NAME_POOL).build();

        public static final Uri PARTICIPANT_CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(TABLE_NAME).appendPath(PATH_PARTICIPANT).build();

        public static Uri buildParticipantUri(long participantId) {
            return ContentUris.withAppendedId(CONTENT_URI, participantId);
        }

        public static Uri buildParticipantUriWith(long participantId) {
            return PARTICIPANT_CONTENT_URI.buildUpon().appendPath(Long.toString(participantId)).build();
        }

        public static Uri buildCategoriesUri(int year) {
            return CATEGORIES_CONTENT_URI.buildUpon().appendPath(Integer.toString(year)).build();
        }

        public static Uri buildParticipantsUri(int year) {
            return CONTENT_URI.buildUpon().appendPath(Integer.toString(year)).build();
        }

        public static Uri buildParticipantPoolsUri(int year, String category) {
            return CONTENT_URI.buildUpon().appendPath(Integer.toString(year)).appendEncodedPath(category).build();
        }

        public static Uri buildPoolsUri(int year, String category) {
            return POOLS_CONTENT_URI.buildUpon().appendPath(Integer.toString(year)).appendEncodedPath(category).build();
        }

        public static Uri buildPoolUri(int year, String category, String poolName) {
            return CONTENT_URI.buildUpon().appendPath(Integer.toString(year)).appendEncodedPath(category).appendEncodedPath(poolName).build();
        }

        public static long getParticipantIdFromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(2));
        }

        public static int getYearFromUri(Uri uri) {
            if (null != uri) {
                List<String> pathSegments = uri.getPathSegments();

                if (pathSegments.size() > 2) {
                    return Integer.parseInt(uri.getPathSegments().get(1));
                }
            }
            return 0;
        }

        public static String getCategoryFromUri(Uri uri) {
            if (null != uri) {
                List<String> pathSegments = uri.getPathSegments();

                if (pathSegments.size() > 3) {
                    return uri.getPathSegments().get(2);
                }
            }
            return null;
        }

        public static String getPoolNameFromUri(Uri uri) {
            if (null != uri) {
                List<String> pathSegments = uri.getPathSegments();

                if (pathSegments.size() > 4) {
                    return uri.getPathSegments().get(3);
                }
            }
            return null;
        }
    }
}
