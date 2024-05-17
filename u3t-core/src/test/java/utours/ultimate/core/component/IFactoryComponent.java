package utours.ultimate.core.component;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.core.steorotype.Mapping;

@Component
public interface IFactoryComponent {

    @Component
    AComponent getAComponent();

    @Component
    @Mapping(type = Mapping.Type.Additional)
    IAddComponent getAAddComponent();

    @Component
    @Mapping(type = Mapping.Type.Additional)
    IAddComponent getBAddComponent();

}
