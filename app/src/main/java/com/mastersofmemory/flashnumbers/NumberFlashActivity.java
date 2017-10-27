package com.mastersofmemory.flashnumbers;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NumberFlashActivity extends AppCompatActivity implements NumberFlashGameStateListener {

    @BindView(R.id.toolbar) NumberFlashToolbarView toolbar;

    int n=4;

    private static int activityInstanceCount = 0;
    private boolean destroyActivity = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_numbers);
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            Log.d("ML.NumberFlashActivity", "onCreate(): with restore");
            NumberFlashBus.gameState = (GameState) savedInstanceState.getSerializable("GameState");
        }
        else {
            Log.d("ML.NumberFlashActivity", "onCreate()");
            NumberFlashBus.gameState = GameState.PRE_MEMORIZATION;
        }

        toolbar.init(this);

        setupSubscriptions();

        activityInstanceCount++;
    }

    private void setupSubscriptions() {
        toolbar.subscribe();
        NumberFlashBus.getBus().subscribe(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("ML.NumberFlashActivity", "onStart()");

        /* TODO: Restore config from user preferences */
        this.onChallengeLoaded(null);
    }

    /*
     * This method is called between onStart() and onPostCreate(Bundle).
     */
    @Override
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
        Log.d("ML.NumberFlashActivity", "onRestoreInstanceState()");
        NumberFlashBus.getBus().onLoad(new NumberFlashConfig(4), inState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("ML.NumberFlashActivity", "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("ML.NumberFlashActivity", "onPause()");
    }


    /*
     * If called, this method will occur before onStop().
     * There are no guarantees about whether it will occur before or after onPause().
     * Note that this does get called when the app moves to the background, as it may get destroyed while there
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("ML.NumberFlashActivity", "onSaveInstanceState()");
        outState.putSerializable("GameState", NumberFlashBus.gameState);
        destroyActivity = false;
        NumberFlashBus.getBus().onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("ML.NumberFlashActivity", "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ML.NumberFlashActivity", "onDestroy()");

        /* If the user decides to play again, then a new activity will be placed on top of the current one
         * This means that the current activity's onStop() and onDestroy() aren't called until
         * the new activity has already loaded all the way to onResume().
         * This check will prevent us from removing the new activity's subscribers
         */
        if (activityInstanceCount <= 1) {
            Log.d("ML.NumberFlashActivity", "onDestroy(unsubscribe all)");
            NumberFlashBus.unsubscribeAll();
        }

        if (destroyActivity) {
            Log.d("ML.NumberFlashActivity", "onDestroy(destroy bus!)");
            NumberFlashBus.getBus().onShutdown();
            NumberFlashBus.destroy();
        }

        activityInstanceCount--;
    }

    @Override
    public void onBackPressed() {
        destroyActivity = true;
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        toolbar.onCreateOptionsMenu(menu, this);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        toolbar.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            this.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }











    ////////// Game State Life Cycle Listener ////////////

    @Override
    public void onLoad(NumberFlashConfig config, Bundle savedInstanceState) {}

    @Override
    public void onMemorizationStart() {}

    @Override
    public void onTransitionToRecall() {}

    @Override
    public void onRecallComplete(final NumberFlashResult result) {

        if (toolbar.getNumLivesRemaining() == 1 && !result.isCorrect()) {
            toolbar.loseLife();

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    NumberFlashBus.getBus().onGameOver();
                }
            }, 1500);

            return;
        }
        else if (!result.isCorrect()) {
            toolbar.loseLife();
            n--;
        }
        else {
            n++;
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                NumberFlashBus.gameState = GameState.PRE_MEMORIZATION;
                NumberFlashBus.getBus().onLoad(new NumberFlashConfig(n), null);
            }
        }, 1500);
    }

    @Override
    public void onPlayAgain(NumberFlashConfig config) {
        Log.d("ML.NumberFlashActivity", "onPlayAgain()");
    }

    @Override
    public void onGameOver() {
        NumberFlashBus.gameState = GameState.REVIEW;
    }

    @Override
    public void onShutdown() {}

    public void onChallengeLoaded(final Challenge challenge) {
        //Log.d("ML.NumberFlashActivity", "onChallengeLoaded(): " + challenge.getTitle());

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                NumberFlashBus.getBus().onLoad(new NumberFlashConfig(4), null);
            }
        }, 50);
    }



    /* full screen mode */
    private void makeFullScreen() {
        if(Build.VERSION.SDK_INT < 19){
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        }
        else {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
