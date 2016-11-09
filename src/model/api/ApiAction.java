package model.api;

import model.exception.DataException;

@FunctionalInterface
public interface ApiAction<T> {
    T run() throws DataException;
}
