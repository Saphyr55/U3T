package utours.ultimate.core.component;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.core.steorotype.Mapping;

@Component
public class AddFactory {

    @Component
    @Mapping(type = Mapping.Type.Additional)
    public IAddComponent getAAddComponent() {
        return new AAddComponent();
    }

    @Component
    @Mapping(type = Mapping.Type.Additional)
    public IAddComponent getBAddComponent(){
        return new BAddComponent();
    }

}
