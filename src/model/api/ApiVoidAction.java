package model.api;

import model.exception.DataException;

@FunctionalInterface
public interface ApiVoidAction {
    void run() throws DataException;
}
