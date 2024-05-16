package utours.ultimate.core.component;

import utours.ultimate.core.steorotype.Component;

@Component
public interface IFactoryComponent {

    @Component
    AComponent getAComponent();

}
