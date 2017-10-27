package com.mastersofmemory.flashnumbers.keyboard;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.mastersofmemory.flashnumbers.GameState;
import com.mastersofmemory.flashnumbers.NumberFlashBus;
import com.mastersofmemory.flashnumbers.NumberFlashConfig;
import com.mastersofmemory.flashnumbers.NumberFlashGameStateListener;
import com.mastersofmemory.flashnumbers.NumberFlashResult;
import com.mastersofmemory.flashnumbers.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class NumberFlashKeyboard extends TableLayout implements NumberFlashGameStateListener {
    public NumberFlashKeyboard(Context context) { super(context); }
    public NumberFlashKeyboard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init() {
        ButterKnife.bind(this);
    }
    public void subscribe() {
        NumberFlashBus.getBus().subscribe(this);
    }

    @Override
    public void onLoad(NumberFlashConfig config, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (NumberFlashBus.gameState == GameState.RECALL) {
                setEnabled(true);
            } else {
                setEnabled(false);
            }
        }
        else {
            setEnabled(false);
        }
    }

    public void setEnabled(boolean enabled) {
        final int childCount = this.getChildCount();
        for (int i = 0; i < childCount; i++) {
            TableRow row = (TableRow) this.getChildAt(i);
            if (row.getChildCount() > 0) {
                for (int j=0; j<row.getChildCount(); j++) {
                    row.getChildAt(j).setAlpha(enabled ? 1.0f : 0.3f);
                    row.getChildAt(j).setEnabled(enabled);
                }
            }
        }
    }

    @Override
    public void onMemorizationStart() {}

    @Override
    public void onTransitionToRecall() {
        setEnabled(true);
    }

    @Override
    public void onRecallComplete(NumberFlashResult result) {
        setEnabled(false);
    }

    @Override
    public void onPlayAgain(NumberFlashConfig config) {}

    @Override
    public void onGameOver() {}

    @Override
    public void onShutdown() {
    }

    @OnClick(R.id.backSpaceButton) void onBackSpaceClicked() { NumberFlashBus.getBus().onBackSpace(); }
    //@OnClick(R.id.nextRowButton) void onNextRowClick() { NumberFlashBus.getBus().onNextRow(); }
    //@OnClick(R.id.submitRowButton) void onSubmitRowClick() { NumberFlashBus.getBus().onSubmitRow(); }
    @OnClick(R.id.key_1) void on1Clicked() { NumberFlashBus.getBus().onKeyPress('1'); }
    @OnClick(R.id.key_2) void on2Clicked() { NumberFlashBus.getBus().onKeyPress('2'); }
    @OnClick(R.id.key_3) void on3Clicked() { NumberFlashBus.getBus().onKeyPress('3'); }
    @OnClick(R.id.key_4) void on4Clicked() { NumberFlashBus.getBus().onKeyPress('4'); }
    @OnClick(R.id.key_5) void on5Clicked() { NumberFlashBus.getBus().onKeyPress('5'); }
    @OnClick(R.id.key_6) void on6Clicked() { NumberFlashBus.getBus().onKeyPress('6'); }
    @OnClick(R.id.key_7) void on7Clicked() { NumberFlashBus.getBus().onKeyPress('7'); }
    @OnClick(R.id.key_8) void on8Clicked() { NumberFlashBus.getBus().onKeyPress('8'); }
    @OnClick(R.id.key_9) void on9Clicked() { NumberFlashBus.getBus().onKeyPress('9'); }
    @OnClick(R.id.key_0) void on0Clicked() { NumberFlashBus.getBus().onKeyPress('0'); }
}
