package com.mastersofmemory.flashnumbers;

import android.os.Bundle;
import android.os.Handler;

class NumberFlashTextPresenter implements NumberFlashGameStateListener, FlashNumbers.Keyboard.UserKeyboardActions {

    private NumberFlashTextView view;
    private int numDigitsEnteredByUser;
    private int numDigits;
    private char[] memoryDigits;
    private char[] recallDigits;
    private final int millisPerDigit = 700;

    NumberFlashTextPresenter(NumberFlashTextView view) {
        this.view = view;
        NumberFlashBus.getBus().subscribe(this);
    }

    @Override
    public void onLoad(NumberFlashConfig config, Bundle savedInstanceState) {
        this.numDigits = config.getNumInitialDigits();
        this.view.showPreMemorizationState();
        this.numDigitsEnteredByUser = 0;
        this.memoryDigits = MemoryDataSetFactory.getDecimalNumberData(config.getNumInitialDigits(), true);
        this.recallDigits = new char[numDigits];
    }

    @Override
    public void onMemorizationStart() {
        view.showMemorizationState();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            int digitNum = 0;

            @Override
            public void run()
            {
                if (digitNum >= numDigits) {
                    NumberFlashBus.getBus().onTransitionToRecall();
                    return;
                }

                view.displayDigit(Character.getNumericValue(memoryDigits[digitNum++]));
                handler.postDelayed(this, millisPerDigit);
            }
        }, 0);
    }

    @Override
    public void onTransitionToRecall() {
        view.showRecallState();
    }

    @Override
    public void onRecallComplete(NumberFlashResult result) {
        view.displayAnswerResponse(result.isCorrect());
    }

    @Override
    public void onPlayAgain(NumberFlashConfig config) {

    }

    @Override
    public void onGameOver() {
        view.setText("");
    }

    @Override
    public void onShutdown() {
        view = null;
    }




    @Override
    public void onKeyPress(char digit) {
        if (numDigitsEnteredByUser >= numDigits) // User entering keys furiously
            return;

        numDigitsEnteredByUser++;
        view.setText(view.getText() + Character.toString(digit) + (numDigitsEnteredByUser % 10 == 0 ? "\n" : ""));
        recallDigits[numDigitsEnteredByUser-1] = digit;

        if (numDigitsEnteredByUser >= numDigits) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    NumberFlashBus.getBus().onRecallComplete(new NumberFlashResult(memoryDigits, recallDigits));
                }
            }, 300);
        }
    }

    @Override
    public void onBackSpace() {
        if (numDigitsEnteredByUser == 0)
            return;

        CharSequence text = view.getText().toString();

        if (text.charAt(text.length()-1) == '\n') {
            text = text.subSequence(0, text.length() - 2);
        }
        else {
            text = text.subSequence(0, text.length() - 1);
        }

        view.setText(text);

        numDigitsEnteredByUser--;
    }
}
