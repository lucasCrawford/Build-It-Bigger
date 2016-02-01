package org.thorrism.gcmdemo.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.lcrawford.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import org.thorrism.gcmdemo.BuildConfig;
import org.thorrism.gcmdemo.R;
import org.thorrism.gcmdemo.utils.Constants;

import java.io.IOException;

/**
 * AsyncTask to handle fetching a joke from the GAE Endpoint
 * that serves random jokes.
 *
 * Handles running the fetch via local or remote service endpoint.
 *
 * Created by lcrawford on 1/26/16.
 */
public class FetchJokeTask extends AsyncTask<Boolean, Void, String> {
    private MyApi mApiService = null;
    private FetchJokeCallback mJokeCallback;
    private Context mContext;

    private static final String TAG = "FetchJokeTask";

    public FetchJokeTask(Context context, FetchJokeCallback callback){
        mContext = context;
        mJokeCallback = callback;
    }

    @Override
    protected String doInBackground(Boolean... params) {
        Boolean localService = params[0];
        MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(), null);

        /* If the task is for a local service end point */
        if(localService) {
            builder.setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                @Override
                public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                    abstractGoogleClientRequest.setDisableGZipContent(true);
                }
            });

            /* Check if we are running this local service on an emulator */
            if(BuildConfig.DEBUG_EMULATOR){
                builder.setRootUrl(Constants.LOCAL_URL);
            }

        }else{
            builder.setRootUrl(Constants.ENDPOINT_URL);

        }
        mApiService = builder
                .setApplicationName(Constants.APP_NAME).
                build();

        /* Get the joke from the api service. */
        try {
            return mApiService.tellJoke().execute().getJoke();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }

    }

    @Override
    protected void onPostExecute(String result){
        mJokeCallback.onJokeReceived(result);
    }

    public interface FetchJokeCallback{
        public void onJokeReceived(String joke);
    }
}
