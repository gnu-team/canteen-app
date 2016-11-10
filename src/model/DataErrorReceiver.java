package model;

import model.exception.DataException;

@FunctionalInterface
public interface DataErrorReceiver {
    void onFail(DataException e);
}
