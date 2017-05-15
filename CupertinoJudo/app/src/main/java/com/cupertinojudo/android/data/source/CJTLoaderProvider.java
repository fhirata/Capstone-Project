package com.cupertinojudo.android.data.source;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.cupertinojudo.android.data.source.local.CJTPersistenceContract;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 *
 */

public class CJTLoaderProvider {
    public static final String[] CATEGORIES_PROJECTION = {
            CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_CATEGORY
    };

    public static final int INDEX_CATEGORY = 0;

    @NonNull
    private final Context mContext;

    public CJTLoaderProvider(@NonNull Context context) {
        mContext = checkNotNull(context, "context cannot be null");
    }

    public Loader<Cursor> createCategoriesLoader(int year) {
        return new CursorLoader(mContext,
                CJTPersistenceContract.CJudoParticipantEntry.buildCategoriesUri(year),
                null,
                CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_TOURNAMENT_YEAR + " = ?",
                new String[]{String.valueOf(year)},
                null
        );
    }

    public Loader<Cursor> createPoolsLoader(int year, String categories) {
        return new CursorLoader(mContext,
                CJTPersistenceContract.CJudoParticipantEntry.buildPoolsUri(year, categories),
                null,
                CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_TOURNAMENT_YEAR + " = ? AND " +
                CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_CATEGORY + " = ?",
                new String[]{String.valueOf(year), categories},
                null
        );
    }

    public Loader<Cursor> createPoolLoader(int year, String category, String pool) {
        return new CursorLoader(mContext,
                CJTPersistenceContract.CJudoParticipantEntry.buildPoolUri(year, category, pool),
                null,
                CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_TOURNAMENT_YEAR + " = ? AND " +
                        CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_CATEGORY + " = ? AND " +
                        CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_POOL + " = ?",
                new String[]{String.valueOf(year), category, pool},
                null
        );
    }

    public Loader<Cursor> createParticipantLoader(int playerId) {
        return new CursorLoader(mContext,
                CJTPersistenceContract.CJudoParticipantEntry.buildParticipantUriWith(playerId),
                null,
                null,
                null,
                null
        );
    }
}
