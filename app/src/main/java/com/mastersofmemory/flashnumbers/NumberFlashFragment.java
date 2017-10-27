package com.mastersofmemory.flashnumbers;

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

import com.mastersofmemory.flashnumbers.keyboard.NumberFlashKeyboard;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NumberFlashFragment extends Fragment implements NumberFlashGameStateListener {

    @BindView(R.id.flashNumberDigitsText) TextView flashNumberDigitsText;
    @BindView(R.id.flashNumberPanel) NumberFlashTextView flashNumberPanel;
    @BindView(R.id.flashStartArrow) ImageView startButton;
    @BindView(R.id.keyboard) NumberFlashKeyboard keyboard;

    private int numDigitsAchieved;

    public NumberFlashFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("ML.NumberFlashFragment", "onCreateView()");

        View view = inflater.inflate(R.layout.fragment_number_flash, container, false);
        ButterKnife.bind(this, view);

        NumberFlashBus.getBus().subscribe(this);
        initializeViews();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("ML.NumberFlashFragment", "onViewCreated()");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("ML.NumberFlashFragment", "onActivityCreated()");
    }

    private void initializeViews() {
        flashNumberPanel.init();
        keyboard.init();
        keyboard.subscribe();
    }

    @Override
    public void onLoad(NumberFlashConfig config, Bundle savedInstanceState) {
        Log.d("ML.NumberFlashFragment", "onLoad()");
        flashNumberDigitsText.setText(config.getNumInitialDigits() + " Digits");
        startButton.setVisibility(NumberFlashBus.gameState == GameState.PRE_MEMORIZATION ? View.VISIBLE : View.GONE);
        startButton.setEnabled(NumberFlashBus.gameState == GameState.PRE_MEMORIZATION);
        startButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_right));
    }

    @Override
    public void onMemorizationStart() {
        startButton.setVisibility(View.GONE);
    }

    @Override
    public void onTransitionToRecall() {
    }

    @Override
    public void onRecallComplete(NumberFlashResult result) {
        if (result.isCorrect() && result.getNumDigitsAttempted() > this.numDigitsAchieved) {
            this.numDigitsAchieved = result.getNumDigitsAttempted();
        }
    }

    @Override
    public void onPlayAgain(NumberFlashConfig config) {
        NumberFlashBus.getBus().onLoad(config, null);
    }

    @Override
    public void onGameOver() {
        flashNumberDigitsText.setText("You got " + numDigitsAchieved + " digits!");
        startButton.setImageDrawable(getResources().getDrawable(R.drawable.icon_replay));
        startButton.setVisibility(View.VISIBLE);
        startButton.setEnabled(true);
    }

    @Override
    public void onShutdown() {}

    @OnClick(R.id.flashStartArrow) void onStartButtonClicked() {

        startButton.setEnabled(false); // prevent multiple clicks

        if (NumberFlashBus.gameState == GameState.REVIEW) {
            NumberFlashBus.getBus().onPlayAgain(new NumberFlashConfig(4));
            return;
        }

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
                            NumberFlashBus.getBus().onMemorizationStart();
                        }
                    }, 1500);
                }
            })
            .start();
    }
}
