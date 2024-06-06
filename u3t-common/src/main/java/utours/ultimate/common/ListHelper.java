package utours.ultimate.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ListHelper {

    @SafeVarargs
    public static <T> List<T> append(List<T> list, T... elements) {
        List<T> result = new ArrayList<>(list);
        result.addAll(Arrays.asList(elements));
        return result;
    }


}
