package utours.ultimate.core;

import org.junit.jupiter.api.Test;
import utours.ultimate.core.component.AComponent;
import utours.ultimate.core.component.BComponent;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ClassProviderTest {

    @Test
    void check_can_get_all_classes_in_package() {
        Set<Class<?>> classes = ClassProvider.classesOf("utours.ultimate.core.component");

        assertTrue(classes.contains(AComponent.class));
        assertTrue(classes.contains(BComponent.class));
    }

}