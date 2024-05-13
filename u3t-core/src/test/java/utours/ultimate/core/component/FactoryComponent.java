package utours.ultimate.core.component;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.core.steorotype.FactoryMethod;

@Component
public class FactoryComponent {

    @FactoryMethod
    public AComponent getAComponent() {
        return new AComponent("aComponent");
    }

}
