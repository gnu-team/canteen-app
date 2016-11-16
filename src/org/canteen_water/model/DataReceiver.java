package org.canteen_water.model;

@FunctionalInterface
public interface DataReceiver<T> {
    void onSuccess(T data);
}
