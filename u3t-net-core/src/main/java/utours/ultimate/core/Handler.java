package utours.ultimate.core;

import java.io.IOException;

@FunctionalInterface
public interface Handler<T> {

    void handle(T t) throws Exception;

}
