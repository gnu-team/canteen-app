package org.canteen_water.model.api;

import org.canteen_water.model.DataErrorReceiver;
import org.canteen_water.model.DataReceiver;
import org.canteen_water.model.exception.DataException;
import java.util.function.Supplier;
import javafx.application.Platform;

public class ApiTask<T> implements Runnable {
    private final DataReceiver<T> onSuccess;
    private final DataErrorReceiver onFail;
    private final ApiAction<T> action;

    public ApiTask(DataReceiver<T> onSuccess, DataErrorReceiver onFail, ApiAction<T> action) {
        this.onSuccess = onSuccess;
        this.onFail = onFail;
        this.action = action;
    }

    @Override
    public void run() {
        T result;
        try {
            result = action.run();
        } catch (DataException e) {
            Platform.runLater(() -> onFail.onFail(e));
            return;
        }

        Platform.runLater(() -> onSuccess.onSuccess(result));
    }
}
