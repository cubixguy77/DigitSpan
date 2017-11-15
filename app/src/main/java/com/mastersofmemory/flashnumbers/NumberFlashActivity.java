package com.mastersofmemory.flashnumbers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;

import com.mastersofmemory.flashnumbers.settings.SettingLoaderImpl;
import com.mastersofmemory.flashnumbers.settings.Settings;
import com.mastersofmemory.flashnumbers.toolbar.NumberFlashToolbarView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NumberFlashActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) NumberFlashToolbarView toolbar;

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
        NumberFlashBus.getBus().subscribe(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("ML.NumberFlashActivity", "onStart()");

        SettingLoaderImpl settingLoader = new SettingLoaderImpl();
        Settings settings = settingLoader.getSettings(this);
        this.onLoad(new NumberFlashConfig(settings), null);
    }

    /*
     * This method is called between onStart() and onPostCreate(Bundle).
     */
    @Override
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
        Log.d("ML.NumberFlashActivity", "onRestoreInstanceState()");
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

        NumberFlashBus.unsubscribeAll();
        NumberFlashBus.getBus().onShutdown();
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

    public void onLoad(NumberFlashConfig config, Bundle bundle) {
        GameData data = new GameData(config);
        NumberFlashBus.getBus().onPreMemorization(data);
        new GameStateManager(data);
    }
}