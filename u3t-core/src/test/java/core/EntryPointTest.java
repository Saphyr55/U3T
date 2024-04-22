package core;

import org.junit.jupiter.api.BeforeAll;
import utours.ultimate.core.internal.ComponentRegistry;

public class EntryPointTest {

    class AnotherComponent {

    }

    @BeforeAll
    static void setup() {
        ComponentRegistry registry = null;
        registry
                .register(AComponent.class)
                .register(AnotherComponent.class);

    }

}
