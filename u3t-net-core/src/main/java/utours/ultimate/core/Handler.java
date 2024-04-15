package utours.ultimate.core;

@FunctionalInterface
public interface Handler<T> {

    void handle(T t);

}
