package com.mastersofmemory.flashnumbers.toolbar;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.Menu;

import com.mastersofmemory.flashnumbers.R;

public abstract class BaseToolbarView extends android.support.v7.widget.Toolbar {

    private ActionBar supportActionBar;

    public BaseToolbarView(Context context) {
        super(context);
    }
    public BaseToolbarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public BaseToolbarView(Context context, AttributeSet attrs, int defStyle) { super(context, attrs, defStyle); }

    public void init(AppCompatActivity context) {
        context.setSupportActionBar(this);
        this.supportActionBar = context.getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setHomeAsUpIndicator(R.drawable.icon_settings);
        }
    }

    protected void setBarTitle(String title) {
        supportActionBar.setTitle(title);
    }

    protected abstract void subscribe();
    protected abstract void onCreateOptionsMenu(Menu menu, Context context);
    protected abstract void onPrepareOptionsMenu(Menu menu);
}
