package utours.ultimate.core.component;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.core.steorotype.ConstructorProperty;

@Component
public class BComponent {

    private final AComponent aComponent;

    public BComponent() {
        aComponent = null;
    }

    @ConstructorProperty
    public BComponent(AComponent aComponent) {
        this.aComponent = aComponent;
    }

}
