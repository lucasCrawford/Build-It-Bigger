package org.thorrism.gcmdemo;

import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;
import android.test.mock.MockContext;
import android.util.Log;


import org.apache.commons.lang3.StringUtils;
import org.thorrism.gcmdemo.task.FetchJokeTask;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by lcrawford on 1/28/16.
 */
public class FetchJokeTest extends InstrumentationTestCase {

    public static final String TAG = "FetchJokeTest";
    private CountDownLatch mSignal;

    /**
     * Ensure the response is not null.
     */
    public void testApiResponse() throws Throwable{
        mSignal = new CountDownLatch(1);

        /* Create the task and method scope callback */
        final FetchJokeTask task = new FetchJokeTask(new MockContext(), new FetchJokeTask.FetchJokeCallback() {
            @Override
            public void onJokeReceived(String joke) {
                mSignal.countDown();
                Log.d(TAG, "Response: " + joke);
                assertTrue(StringUtils.isNotBlank(joke));
            }
        });

        /* Run the test on UI thread */
        try {
            runTestOnUiThread(new Runnable() {
                @Override
                public void run() {
                    task.execute(true);
                }
            });
        } catch (Throwable throwable) {
            Log.d(TAG, throwable.getMessage());
            throwable.printStackTrace();
        }

        /* Provide ample time for the test to execute */
        mSignal.await(30, TimeUnit.SECONDS);
    }

}
