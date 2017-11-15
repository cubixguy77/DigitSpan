package com.mastersofmemory.flashnumbers.keyboard;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.mastersofmemory.flashnumbers.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class NumberFlashKeyboard extends TableLayout implements Keyboard.View {
    private Keyboard.Presenter presenter;

    public NumberFlashKeyboard(Context context) { super(context); }
    public NumberFlashKeyboard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(Keyboard.Presenter presenter) {
        ButterKnife.bind(this);
        this.presenter = presenter;
    }

    @Override
    public void enable() {
        setIsEnabled(true);
    }

    @Override
    public void disable() {
        setIsEnabled(false);
    }

    private void setIsEnabled(boolean enabled) {
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

    @OnClick(R.id.backSpaceButton) void onBackSpaceClicked() { presenter.onBackspacePress(); }
    @OnClick(R.id.key_1) void on1Clicked() { presenter.onKeyPress('1'); }
    @OnClick(R.id.key_2) void on2Clicked() { presenter.onKeyPress('2'); }
    @OnClick(R.id.key_3) void on3Clicked() { presenter.onKeyPress('3'); }
    @OnClick(R.id.key_4) void on4Clicked() { presenter.onKeyPress('4'); }
    @OnClick(R.id.key_5) void on5Clicked() { presenter.onKeyPress('5'); }
    @OnClick(R.id.key_6) void on6Clicked() { presenter.onKeyPress('6'); }
    @OnClick(R.id.key_7) void on7Clicked() { presenter.onKeyPress('7'); }
    @OnClick(R.id.key_8) void on8Clicked() { presenter.onKeyPress('8'); }
    @OnClick(R.id.key_9) void on9Clicked() { presenter.onKeyPress('9'); }
    @OnClick(R.id.key_0) void on0Clicked() { presenter.onKeyPress('0'); }
}
