package utours.ultimate.core.component;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.core.steorotype.Mapping;

@Component
@Mapping
public class DComponent implements IDComponent {

    @Override
    public void service() {
        System.out.println("DComponent service");
    }

}
