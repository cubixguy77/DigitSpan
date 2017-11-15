package com.mastersofmemory.flashnumbers.toolbar;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mastersofmemory.flashnumbers.GameData;
import com.mastersofmemory.flashnumbers.NumberFlashBus;
import com.mastersofmemory.flashnumbers.NumberFlashGameStateListener;
import com.mastersofmemory.flashnumbers.NumberFlashResult;
import com.mastersofmemory.flashnumbers.R;
import com.mastersofmemory.flashnumbers.SaveInstanceStateListener;

public class NumberFlashToolbarView extends BaseToolbarView implements NumberFlashGameStateListener, SaveInstanceStateListener, LifeListener {

    private MenuItem heartOne;
    private MenuItem heartTwo;
    private MenuItem heartThree;

    private GameData data;

    public NumberFlashToolbarView(Context context) {
        super(context);
    }
    public NumberFlashToolbarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public NumberFlashToolbarView(Context context, AttributeSet attrs, int defStyle) { super(context, attrs, defStyle); }

    public void subscribe() {
        NumberFlashBus.getBus().subscribe(this);
    }

    public void onCreateOptionsMenu(Menu menu, Context context) {
        System.out.println("onCreateOptionsMenu");
        ((AppCompatActivity) context).getMenuInflater().inflate(R.menu.menu_flash_numbers, menu);
        setBarTitle("Flash Numbers");
        setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Settings clicked");
            }
        });
    }

    public void onPrepareOptionsMenu(Menu menu) {
        System.out.println("onPrepareOptionsMenu");
        heartOne = menu.findItem(R.id.heart_one);
        heartTwo = menu.findItem(R.id.heart_two);
        heartThree = menu.findItem(R.id.heart_three);

        refreshToolbarIcon(data.getNumLivesRemaining());
    }

    private void refreshToolbarIcon(int numLivesRemaining) {
        switch (numLivesRemaining) {
            case 0:
                heartOne.setIcon(R.drawable.icon_heart_empty);
                heartTwo.setIcon(R.drawable.icon_heart_empty);
                heartThree.setIcon(R.drawable.icon_heart_empty);
                break;
            case 1:
                heartOne.setIcon(R.drawable.icon_heart);
                heartTwo.setIcon(R.drawable.icon_heart_empty);
                heartThree.setIcon(R.drawable.icon_heart_empty);
                break;
            case 2:
                heartOne.setIcon(R.drawable.icon_heart);
                heartTwo.setIcon(R.drawable.icon_heart);
                heartThree.setIcon(R.drawable.icon_heart_empty);
                break;
            case 3:
                heartOne.setIcon(R.drawable.icon_heart);
                heartTwo.setIcon(R.drawable.icon_heart);
                heartThree.setIcon(R.drawable.icon_heart);
                break;
            default: break;
        }
    }

    @Override
    public void onPreMemorization(GameData data) {
        this.data = data;
    }

    @Override
    public void onMemorizationStart() {}

    @Override
    public void onRecallStart() {}

    @Override
    public void onRecallComplete(NumberFlashResult result) {}

    @Override
    public void onGameOver() {}

    @Override
    public void onShutdown() {}

    @Override
    public void onRestoreInstanceState(Bundle inState) {}

    @Override
    public void onSaveInstanceState(Bundle outState) {}

    @Override
    public void onLifeLost(int livesRemaining) {
        refreshToolbarIcon(livesRemaining);
    }
}
