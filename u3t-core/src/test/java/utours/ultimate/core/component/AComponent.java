package utours.ultimate.core.component;

import utours.ultimate.core.steorotype.Component;

@Component
public class AComponent {

    private final IDComponent dComponent;
    private final String name;

    public AComponent(IDComponent dComponent, String name) {
        this.dComponent = dComponent;
        this.name = name;
    }

}
