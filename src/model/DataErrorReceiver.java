package model;

import model.exception.DataException;

@FunctionalInterface
public interface DataErrorReceiver {
    public void onFail(DataException e);
}
