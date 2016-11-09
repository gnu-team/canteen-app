package model;

@FunctionalInterface
public interface DataReceiver<T> {
    void onSuccess(T data);
}
