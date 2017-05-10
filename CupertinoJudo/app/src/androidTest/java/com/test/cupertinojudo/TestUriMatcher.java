package com.test.cupertinojudo;

import android.content.UriMatcher;
import android.net.Uri;
import android.test.AndroidTestCase;

import com.test.cupertinojudo.data.source.local.CJTPersistenceContract;
import com.test.cupertinojudo.data.source.local.CJTProvider;

/**
 * Created by fabiohh on 5/10/17.
 */

public class TestUriMatcher extends AndroidTestCase {

    private static final Uri TEST_PARTICIPANT_DIR = CJTPersistenceContract.CJudoParticipantEntry.CONTENT_URI;
    private static final Uri TEST_PARTICIPANT_WITH_YEAR_DIR = CJTPersistenceContract.CJudoParticipantEntry.buildPlayersUri(2017);
    private static final Uri TEST_PARTICIPANT_WITH_YEAR_WITH_CATEGORY_DIR = CJTPersistenceContract.CJudoParticipantEntry.buildPoolsUri(2017, "Junior Males");
    private static final Uri TEST_PARTICIPANT_WITH_YEAR_WITH_CATEGORY_POOLNAME_DIR = CJTPersistenceContract.CJudoParticipantEntry.buildPoolUri(2017, "Junior Males", "A");
    public void testUriMatcher() {
        UriMatcher testMatcher = CJTProvider.buildUriMatcher();

        assertEquals("Error: PARTICIPANTS URI was matched incorrectly.",
                testMatcher.match(TEST_PARTICIPANT_DIR), CJTProvider.PARTICIPANTS);
        assertEquals("Error: PARTICIPANTS WITH YEAR was matched incorrectly.",
                testMatcher.match(TEST_PARTICIPANT_WITH_YEAR_DIR), CJTProvider.PARTICIPANTS_WITH_YEAR);
        assertEquals("Error: PARTICIPANTS WITH YEAR was matched incorrectly.",
                testMatcher.match(TEST_PARTICIPANT_WITH_YEAR_WITH_CATEGORY_DIR), CJTProvider.PARTICIPANTS_WITH_YEAR_CATEGORY);
        assertEquals("Error: PARTICIPANTS WITH YEAR was matched incorrectly.",
                testMatcher.match(TEST_PARTICIPANT_WITH_YEAR_WITH_CATEGORY_POOLNAME_DIR), CJTProvider.PARTICIPANTS_WITH_YEAR_CATEGORY_POOLNAME);
    }
}