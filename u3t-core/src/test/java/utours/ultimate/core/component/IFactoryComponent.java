package utours.ultimate.core.component;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.core.steorotype.FactoryMethod;

@Component
public interface IFactoryComponent {

    @FactoryMethod
    AComponent getAComponent();

}
