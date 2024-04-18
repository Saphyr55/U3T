package utours.ultimate.net;

import java.util.function.Consumer;

/**
 * Represents an operation that handles a single input argument and returns no
 * result. Unlike most other functional interfaces.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #handle(Object)}.
 *
 * @param <T> the type of the input to the operation
 *
 * @since 1.8
 */
@FunctionalInterface
public interface Handler<T> {

    /**
     * Performs this operation on the given argument.
     *
     * @param t input argument
     * @throws Exception
     */
    void handle(T t) throws Exception;

}
