package utours.ultimate.net;

@FunctionalInterface
public interface Handler<T> {

    /**
     *
     * @param t
     * @throws Exception
     */
    void handle(T t) throws Exception;

}
