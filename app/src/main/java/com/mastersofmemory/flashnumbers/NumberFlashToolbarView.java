package com.mastersofmemory.flashnumbers;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;

import com.mastersofmemory.flashnumbers.toolbar.BaseToolbarView;

public class NumberFlashToolbarView extends BaseToolbarView implements NumberFlashGameStateListener, SaveInstanceStateListener {

    private MenuItem heartOne;
    private MenuItem heartTwo;
    private MenuItem heartThree;

    private int numLivesRemaining = 3;

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
        setTitle("Digit Span");
    }

    public void onPrepareOptionsMenu(Menu menu) {
        System.out.println("onPrepareOptionsMenu");
        heartOne = menu.findItem(R.id.heart_one);
        heartTwo = menu.findItem(R.id.heart_two);
        heartThree = menu.findItem(R.id.heart_three);
    }

    public int getNumLivesRemaining() {
        return this.numLivesRemaining;
    }

    public void loseLife() {
        this.numLivesRemaining--;
        refreshToolbarIcon();
    }

    private void refreshToolbarIcon() {
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
    public void onLoad(NumberFlashConfig config, Bundle savedInstanceState) {

    }

    @Override
    public void onMemorizationStart() {
        refreshToolbarIcon();
    }

    @Override
    public void onTransitionToRecall() {
        refreshToolbarIcon();
    }

    @Override
    public void onRecallComplete(NumberFlashResult result) {
    }

    @Override
    public void onPlayAgain(NumberFlashConfig config) {
        this.numLivesRemaining = 3;
        refreshToolbarIcon();
    }

    @Override
    public void onGameOver() {}

    @Override
    public void onShutdown() {}

    @Override
    public void onRestoreInstanceState(Bundle inState) {}

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }
}
