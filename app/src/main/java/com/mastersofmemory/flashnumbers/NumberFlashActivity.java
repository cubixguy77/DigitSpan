package com.mastersofmemory.flashnumbers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;

import com.mastersofmemory.flashnumbers.gamestate.GameData;
import com.mastersofmemory.flashnumbers.gamestate.GameStateManager;
import com.mastersofmemory.flashnumbers.settings.NumberFlashConfig;
import com.mastersofmemory.flashnumbers.settings.SettingLoaderImpl;
import com.mastersofmemory.flashnumbers.settings.Settings;
import com.mastersofmemory.flashnumbers.toolbar.NumberFlashToolbarView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NumberFlashActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) NumberFlashToolbarView toolbar;
    GameData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_numbers);
        ButterKnife.bind(this);
        toolbar.init(this);
        NumberFlashBus.getBus().subscribe(this);
        loadConfig(savedInstanceState);
    }

    private void loadConfig(Bundle savedInstanceState) {
        SettingLoaderImpl settingLoader = new SettingLoaderImpl();
        Settings settings = settingLoader.getSettings(this);
        NumberFlashConfig config = new NumberFlashConfig(settings);
        data = new GameData(config, savedInstanceState);
        new GameStateManager(data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("ML.NumberFlashActivity", "onStart()");
    }

    /*
     * This method is called between onStart() and onPostCreate(Bundle).
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
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
        NumberFlashBus.getBus().onPreMemorization(data);
        return true;
    }
}