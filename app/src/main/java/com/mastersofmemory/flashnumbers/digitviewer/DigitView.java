package com.mastersofmemory.flashnumbers.digitviewer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mastersofmemory.flashnumbers.gamestate.GameState;
import com.mastersofmemory.flashnumbers.NumberFlashBus;
import com.mastersofmemory.flashnumbers.R;
import com.mastersofmemory.flashnumbers.keyboard.KeyboardPresenter;
import com.mastersofmemory.flashnumbers.keyboard.NumberFlashKeyboard;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DigitView extends Fragment implements DigitViewer.View {

    @BindView(R.id.flashNumberDigitsText) TextView flashNumberDigitsText;
    @BindView(R.id.flashNumberPanel) NumberFlashTextView flashNumberPanel;
    @BindView(R.id.flashStartArrow) ImageView startButton;
    @BindView(R.id.keyboard) NumberFlashKeyboard keyboard;

    private DigitViewer.Presenter presenter;

    public DigitView() {}

    public void setPresenter(DigitViewer.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showPreMemorizationState(int numDigitsToAttempt) {
        flashNumberPanel.showPreMemorizationState();
        flashNumberDigitsText.setText(String.format(getString(R.string.numDigits), numDigitsToAttempt));
        startButton.setVisibility(View.VISIBLE);
        startButton.setEnabled(true);
        startButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_right));
    }

    @Override
    public void showCountdown() {
        startButton
            .animate()
            .alpha(0)
            .setDuration(600)
            .setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    startButton.setVisibility(View.GONE);
                    startButton.setAlpha(1f);

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            presenter.onCountdownComplete();
                        }
                    }, 1500);
                }
            })
            .start();
    }

    @Override
    public void showMemorizationState() {
        flashNumberPanel.showMemorizationState();
    }

    @Override
    public void displayDigit(int digit) {
        flashNumberPanel.displayDigit(digit);
    }

    @Override
    public void showRecallState() {
        flashNumberPanel.showRecallState();
    }

    @Override
    public void refreshRecallData(char[] recallData) {
        flashNumberPanel.refreshRecallData(recallData);
    }

    @Override
    public void displayAnswerResponse(boolean isCorrect) {
        flashNumberPanel.displayAnswerResponse(isCorrect);
    }

    @Override
    public void showGameOverState(int numDigitsAchieved) {
        flashNumberPanel.showGameOverState();
        flashNumberDigitsText.setText(String.format(getString(R.string.numDigitsAchieved), numDigitsAchieved));
        startButton.setImageDrawable(getResources().getDrawable(R.drawable.icon_replay));
        startButton.setVisibility(View.VISIBLE);
        startButton.setEnabled(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("ML.DigitView", "onCreateView()");

        View view = inflater.inflate(R.layout.fragment_number_flash, container, false);
        ButterKnife.bind(this, view);

        initializeViews();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("ML.DigitView", "onViewCreated()");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("ML.DigitView", "onActivityCreated()");
    }

    private void initializeViews() {
        new DigitViewerPresenter(this);
        keyboard.init(new KeyboardPresenter(keyboard));
    }

    @OnClick(R.id.flashStartArrow) void onStartButtonClicked() {
        startButton.setEnabled(false); // prevent multiple clicks

        if (NumberFlashBus.gameState == GameState.PRE_MEMORIZATION) {
            presenter.onStartClick();
        }
        else if (NumberFlashBus.gameState == GameState.RESULTS) {
            presenter.onResetClick();
        }
    }
}
