package com.cupertinojudo.android;

import android.os.Environment;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import org.junit.Test;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class NotificationsFragmentScreenshot extends ActivityInstrumentationTestCase2<MainActivity> {
    private Solo mSolo;

    public NotificationsFragmentScreenshot() {
        super(MainActivity.class);
    }

    public void setUp() {
        mSolo = new Solo(getInstrumentation(), getActivity());

        // Click on the Notifications Tab to open the notifications fragment
        mSolo.clickOnMenuItem("Notifications");
        mSolo.sleep(3000);
    }

    @Test
    public void testScreenShot() {
        String path = Environment.getExternalStorageDirectory().getPath() + "/Robotium-Screenshots";
        File directory = new File(path);
        if (!directory.exists()) {
            assertTrue(directory.mkdirs());
        }
        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd-hhmmss");
        String filename = "NotificationsFragment-" + dateFormatter.format(new Date());
        mSolo.getConfig().screenshotSavePath = path;
        mSolo.takeScreenshot(filename);
        File file = new File(path, filename + ".jpg");
        assertTrue(file.exists());
    }
}
