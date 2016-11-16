package org.canteen_water.model.api;

import org.canteen_water.model.exception.DataException;

@FunctionalInterface
public interface ApiAction<T> {
    T run() throws DataException;
}
