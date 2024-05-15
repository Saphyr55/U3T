package utours.ultimate.core.component;

import utours.ultimate.core.steorotype.Component;

@Component(id = "Internal.CComponent")
public class CComponent {

    private final AComponent aComponent;
    private final BComponent bComponent;

    public CComponent(AComponent aComponent,
                      BComponent bComponent) {

        this.aComponent = aComponent;
        this.bComponent = bComponent;
    }

}
