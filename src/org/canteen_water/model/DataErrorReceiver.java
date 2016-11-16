package org.canteen_water.model;

import org.canteen_water.model.exception.DataException;

@FunctionalInterface
public interface DataErrorReceiver {
    void onFail(DataException e);
}
