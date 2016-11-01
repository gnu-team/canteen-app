package model.api;

import model.exception.DataException;

@FunctionalInterface
public interface ApiVoidAction {
    public void run() throws DataException;
}
