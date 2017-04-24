package com.test.cupertinojudo;

import android.os.Environment;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

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

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mSolo = new Solo(getInstrumentation(), getActivity());

        // Click on the Notifications Tab to open the notifications fragment
        mSolo.clickOnMenuItem("Notifications");
        mSolo.sleep(5000);
    }

    public void testScreenShot() {
        String path = Environment.getExternalStorageDirectory().getPath() + "/Robotium-Screenshots";
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd-hhmmss");
        String filename = "NotificationsFragment-" + dateFormatter.format(new Date());
        mSolo.getConfig().screenshotSavePath = path;
        mSolo.takeScreenshot(filename);
        File file = new File(path, filename + ".jpg");
        assertTrue(file.exists());
    }
}
