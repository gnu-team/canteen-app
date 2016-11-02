package model.api;

import model.DataErrorReceiver;
import model.DataSuccessReceiver;
import model.exception.DataException;
import javafx.application.Platform;

public class ApiVoidTask implements Runnable {
    private final DataSuccessReceiver onSuccess;
    private final DataErrorReceiver onFail;
    private final ApiVoidAction action;

    public ApiVoidTask(DataSuccessReceiver onSuccess, DataErrorReceiver onFail, ApiVoidAction action) {
        this.onSuccess = onSuccess;
        this.onFail = onFail;
        this.action = action;
    }

    @Override
    public void run() {
        try {
            action.run();
        } catch (DataException e) {
            Platform.runLater(() -> onFail.onFail(e));
            return;
        }

        Platform.runLater(() -> onSuccess.onSuccess());
    }
}
