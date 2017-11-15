package com.mastersofmemory.flashnumbers.digitviewer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.mastersofmemory.flashnumbers.GameState;
import com.mastersofmemory.flashnumbers.NumberFlashBus;

public class NumberFlashTextView extends android.support.v7.widget.AppCompatTextView {

    private float displayWidthDP = 0;

    public NumberFlashTextView(Context context) {
        super(context);
    }
    public NumberFlashTextView(Context context, @Nullable AttributeSet attrs) { super(context, attrs); }

    @Override
    protected void onDraw(Canvas canvas){
        float offset = getHeight() - getLineHeight();

        if (NumberFlashBus.gameState == GameState.MEMORIZATION) {
            canvas.translate(0, offset / 2);
        }

        super.onDraw(canvas);
    }

    public void displayDigit(final int digit) {
        if (getText().length() == 0) { // first digit
            setText(Integer.toString(digit));
            animate().alpha(1).setListener(null).setDuration(100).start();
        }
        else { // clear out existing digit first
            animate().alpha(0).setDuration(100).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    setText(Integer.toString(digit));
                    animate().alpha(1).setListener(null).setDuration(100).start();
                }
            }).start();
        }
    }

    public void displayAnswerResponse(boolean correct) {
        setTextSize(TypedValue.COMPLEX_UNIT_SP, displayWidthDP / 2f);

        if (correct) {
            setText("✔");
        }
        else {
            setText("X");
        }
    }

    public void showPreMemorizationState() {
        setText("");
    }

    public void showMemorizationState() {
        setText("");
        setAlpha(0);
        loadScreenSize();
        setTextSize(TypedValue.COMPLEX_UNIT_SP, displayWidthDP / 1.7f);
        setSingleLine();
    }

    public void showRecallState() {
        setText("");
        setAlpha(1);
        setTextSize(TypedValue.COMPLEX_UNIT_SP, displayWidthDP / 8f);
        setSingleLine(false);
    }

    public void showGameOverState() {
        setText("");
    }

    public void refreshRecallData(char[] recallData) {
        setText(new String(recallData));
    }

    private void loadScreenSize() {
        if (displayWidthDP == 0) {
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            displayWidthDP = Math.min(displayMetrics.widthPixels, displayMetrics.heightPixels) / displayMetrics.density;
        }
    }
}
