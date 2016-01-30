package org.thorrism.jokeandroidlibrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {
    public static final String JOKE_KEY = "joke_key";

    private TextView mJokeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        mJokeView = (TextView) findViewById(R.id.joke_message_view);

        initActivity();
    }

    /**
     * Initialize the activity by reading the joke passed from the
     * client.
     */
    private void initActivity(){

        /* Setup the toolbar */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Random Joke");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JokeActivity.this.finish(); //go back to calling activity
                }
            });
        }

        /* Get the data passed within the intent's bundle */
        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            String joke = bundle.getString(JOKE_KEY);
            mJokeView.setText(joke);
        }
    }
}
