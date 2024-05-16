package utours.ultimate.core.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ErrorManager {

    public static void throwErrorsOf(List<Throwable> errors) {
        if (!errors.isEmpty()) {
            throw errors.stream()
                    .reduce(ErrorManager::throwableReducer)
                    .map(IllegalStateException::new)
                    .orElseThrow();
        }
    }

    public static Throwable throwableReducer(Throwable e, Throwable e2) {
        e.addSuppressed(e2);
        return e;
    }

    public static <T> List<Throwable> forEachOf(List<T> elements, Consumer<T> onElement) {
        List<Throwable> errors = new ArrayList<>();
        for (T el : elements) {
            try {
                onElement.accept(el);
            } catch (Throwable e) {
                errors.add(e);
            }
        }
        return errors;
    }

}
