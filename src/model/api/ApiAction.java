package model.api;

import model.exception.DataException;

@FunctionalInterface
public interface ApiAction<T> {
    public T run() throws DataException;
}
