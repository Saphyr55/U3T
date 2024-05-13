package utours.ultimate.core.component;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.core.steorotype.FactoryMethod;

@Component
public class FactoryComponent {

    private final IDComponent dComponent;

    public FactoryComponent(IDComponent dComponent) {
        this.dComponent = dComponent;
    }

    @FactoryMethod
    public AComponent getAComponent() {
        return new AComponent(dComponent, "aComponent");
    }

}
