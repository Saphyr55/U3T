package utours.ultimate.server.common;

import java.util.HashMap;
import java.util.Map;

public final class MapHelper {

    public static <K, V> Map<K, V> emptyMap() {
        return new HashMap<>();
    }

}
