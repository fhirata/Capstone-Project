package com.cupertinojudo.android;

import android.content.UriMatcher;
import android.net.Uri;
import android.test.AndroidTestCase;

import com.cupertinojudo.android.data.source.local.CJTPersistenceContract;
import com.cupertinojudo.android.data.source.local.CJTProvider;

/**
 *
 */

public class TestUriMatcher extends AndroidTestCase {

    private static final Uri TEST_PARTICIPANT_DIR = CJTPersistenceContract.CJudoParticipantEntry.CONTENT_URI;
    private static final Uri TEST_PARTICIPANT_WITH_ID = CJTPersistenceContract.CJudoParticipantEntry.buildParticipantUriWith(1234);
    private static final Uri TEST_PARTICIPANT_WITH_YEAR_DIR = CJTPersistenceContract.CJudoParticipantEntry.buildParticipantsUri(2017);
    private static final Uri TEST_PARTICIPANT_WITH_YEAR_CATEGORY_DIR = CJTPersistenceContract.CJudoParticipantEntry.buildParticipantPoolsUri(2017, "Junior Males");
    private static final Uri TEST_PARTICIPANT_WITH_YEAR_CATEGORY_POOLNAME_DIR = CJTPersistenceContract.CJudoParticipantEntry.buildPoolUri(2017, "Junior Males", "A");
    private static final Uri TEST_CATEGORIES_WITH_YEAR_DIR = CJTPersistenceContract.CJudoParticipantEntry.buildCategoriesUri(2017);
    private static final Uri TEST_POOLS_WITH_YEAR_CATEGORY_DIR = CJTPersistenceContract.CJudoParticipantEntry.buildPoolsUri(2017, "Junior Males");
    public void testUriMatcher() {
        UriMatcher testMatcher = CJTProvider.buildUriMatcher();

        assertEquals("Error: PARTICIPANTS URI was matched incorrectly.",
                testMatcher.match(TEST_PARTICIPANT_DIR), CJTProvider.PARTICIPANTS);
        assertEquals("Error: PARTICIPANTS WITH YEAR was matched incorrectly.",
                testMatcher.match(TEST_PARTICIPANT_WITH_YEAR_DIR), CJTProvider.PARTICIPANTS_WITH_YEAR);
        assertEquals("Error: PARTICIPANTS WITH YEAR was matched incorrectly.",
                testMatcher.match(TEST_PARTICIPANT_WITH_YEAR_CATEGORY_DIR), CJTProvider.PARTICIPANTS_WITH_YEAR_CATEGORY);
        assertEquals("Error: PARTICIPANTS WITH YEAR was matched incorrectly.",
                testMatcher.match(TEST_PARTICIPANT_WITH_YEAR_CATEGORY_POOLNAME_DIR), CJTProvider.PARTICIPANTS_WITH_YEAR_CATEGORY_POOLNAME);
        assertEquals("Error: PARTICIPANTS WITH YEAR was matched incorrectly.",
                testMatcher.match(TEST_CATEGORIES_WITH_YEAR_DIR), CJTProvider.CATEGORIES_WITH_YEAR);
        assertEquals("Error: PARTICIPANTS WITH YEAR was matched incorrectly.",
                testMatcher.match(TEST_POOLS_WITH_YEAR_CATEGORY_DIR), CJTProvider.POOLS_WITH_YEAR_CATEGORY);
        assertEquals("Error: PARTICIPANTS WITH ID was matched incorrectly.",
                testMatcher.match(TEST_PARTICIPANT_WITH_ID), CJTProvider.PARTICIPANT_WITH_ID);
    }
}
