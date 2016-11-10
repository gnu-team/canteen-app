package org.canteen_water.model.api;

import org.canteen_water.model.exception.DataException;

@FunctionalInterface
public interface ApiVoidAction {
    void run() throws DataException;
}
