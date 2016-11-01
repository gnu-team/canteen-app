package model;

@FunctionalInterface
public interface DataReceiver<T> {
    public void onSuccess(T data);
}
