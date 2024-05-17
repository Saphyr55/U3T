package utours.ultimate.core.component;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.core.steorotype.Mapping;

@Component
public interface IFactoryComponent {

    @Component
    AComponent getAComponent();

    @Component
    EComponent getEComponent();

}
