package org.thorrism.gcmdemo.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import org.thorrism.gcmdemo.R;
import org.thorrism.gcmdemo.task.FetchJokeTask;
import org.thorrism.gcmdemo.utils.ActivityUtils;
import org.thorrism.gcmdemo.utils.NetworkUtils;
import org.thorrism.jokeandroidlibrary.JokeActivity;

public class MainFragment extends Fragment implements FetchJokeTask.FetchJokeCallback{

    public ProgressBar mProgressBar;
    public LinearLayout mContentLayout;
    public InterstitialAd mInterstitialAd;

    private boolean mIsInitialized = false;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        mContentLayout = (LinearLayout) v.findViewById(R.id.content_layout);
        mProgressBar = (ProgressBar) v.findViewById(R.id.fetch_joke_progress);
        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        loadAd();

        v.findViewById(R.id.fetch_joke_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchJoke();
            }
        });

        mIsInitialized = true;
        return v;
    }
    public void fetchJoke(){
        /* Check network is available before calling the network task */
        if(NetworkUtils.isNetworkAvailable(getActivity())){
            toggleLoading(true);
            new FetchJokeTask(getActivity(), this).execute(false);
        }else{
            ActivityUtils.createToast(getActivity(), getString(R.string.network_unavailable));
        }
    }

    /**
     * Toggle the visibility of content / progress bar to show loading.
     * @param contentVisible - true for content visible / progress bar showing, false for opposite.
     */
    private void toggleLoading(boolean contentVisible){
        mContentLayout.setVisibility( contentVisible? View.GONE : View.VISIBLE);
        mProgressBar.setVisibility(contentVisible ? View.VISIBLE : View.GONE);
    }

    /**
     * Load an ad via AdMob.
     */
    private void loadAd(){
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    public void onJokeReceived(String joke) {
        final Intent intent = new Intent(getActivity(), JokeActivity.class);
        intent.putExtra(JokeActivity.JOKE_KEY, joke);

        /* Ensure we have an add loaded, now show it. */
        if(mInterstitialAd.isLoaded()){
            mInterstitialAd.show();

            /* When the add is closed, start the activity to show joke. */
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    loadAd(); //load new add
                    getActivity().startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if(mIsInitialized){
            toggleLoading(false);
        }
    }

}
