package com.mastersofmemory.flashnumbers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.mastersofmemory.flashnumbers.digitviewer.DigitViewerPresenter;

public class NumberFlashTextView extends android.support.v7.widget.AppCompatTextView {

    private DigitViewerPresenter presenter;

    public NumberFlashTextView(Context context) {
        super(context);
    }
    public NumberFlashTextView(Context context, @Nullable AttributeSet attrs) { super(context, attrs); }

    public void init(DigitViewerPresenter presenter) {
        this.presenter = presenter;
    }
    public DigitViewerPresenter getPresenter() { return this.presenter; }

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

        /*
        setText(Integer.toString(digit));

        animate().alpha(1).setDuration(300).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animate().alpha(0).setDuration(300).setStartDelay(500).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        //setText("");
                    }
                }).start();
            }
        }).start();
        */
    }

    public void displayAnswerResponse(boolean correct) {
        if (correct) {
            setTextSize(140f);
            setText("✔");
        }
        else {
            setTextSize(140f);
            setText("X");
        }
    }

    public void showPreMemorizationState() {
        setText("");
    }

    public void showMemorizationState() {
        setText("");
        setAlpha(0);
        setTextSize(140f);
        setSingleLine();
    }

    public void showRecallState() {
        setText("");
        setAlpha(1);
        setTextSize(40f);
        setSingleLine(false);
    }

    public void showGameOverState() {
        setText("");
    }

    public void refreshRecallData(char[] recallData) {
        setText(new String(recallData));
    }
}
