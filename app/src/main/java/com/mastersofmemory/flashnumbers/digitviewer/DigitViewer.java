package com.mastersofmemory.flashnumbers.digitviewer;

public interface DigitViewer {

    interface Presenter {
        void onStartClick();
        void onResetClick();
        void onCountdownComplete();
    }

    interface View {
        /* Pre Memorization */
        void showPreMemorizationState(int numDigitsToAttempt);
        void showCountdown();

        /* Memorization */
        void showMemorizationState();
        void displayDigit(int digit);

        /* Recall */
        void showRecallState();
        void refreshRecallData(char[] recallData);

        /* Review */
        void displayAnswerResponse(boolean isCorrect);
        void showGameOverState(int numDigitsAchieved);

        void setPresenter(DigitViewer.Presenter presenter);
    }
}
