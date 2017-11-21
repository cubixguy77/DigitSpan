package com.mastersofmemory.flashnumbers.toolbar;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;

import com.mastersofmemory.flashnumbers.R;

public class NumberFlashToolbarView extends BaseToolbarView implements Toolbar.View {

    private MenuItem heartOne;
    private MenuItem heartTwo;
    private MenuItem heartThree;

    public NumberFlashToolbarView(Context context) {
        super(context);
    }
    public NumberFlashToolbarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public NumberFlashToolbarView(Context context, AttributeSet attrs, int defStyle) { super(context, attrs, defStyle); }

    public void onCreateOptionsMenu(Menu menu, Context context) {
        System.out.println("onCreateOptionsMenu");
        ((AppCompatActivity) context).getMenuInflater().inflate(R.menu.menu_flash_numbers, menu);
    }

    public void onPrepareOptionsMenu(Menu menu) {
        System.out.println("onPrepareOptionsMenu");
        heartOne = menu.findItem(R.id.heart_one);
        heartTwo = menu.findItem(R.id.heart_two);
        heartThree = menu.findItem(R.id.heart_three);

        new ToolbarPresenter(this);
    }

    @Override
    public void displayLivesRemaining(int numLivesRemaining) {
        heartOne.setIcon(numLivesRemaining >= 1 ? R.drawable.icon_heart : R.drawable.icon_heart_empty);
        heartTwo.setIcon(numLivesRemaining >= 2 ? R.drawable.icon_heart : R.drawable.icon_heart_empty);
        heartThree.setIcon(numLivesRemaining >= 3 ? R.drawable.icon_heart : R.drawable.icon_heart_empty);
    }
}
