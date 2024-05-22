package utours.ultimate.core.component;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.core.steorotype.Mapping;

@Component
public interface IFactoryComponent {

    @Mapping
    @Component
    AComponent getAComponent();

    @Mapping
    @Component
    EComponent getEComponent();

}
