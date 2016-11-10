package org.canteen_water.model;

import org.canteen_water.model.exception.DataException;

@FunctionalInterface
public interface DataErrorReceiver {
    public void onFail(DataException e);
}
