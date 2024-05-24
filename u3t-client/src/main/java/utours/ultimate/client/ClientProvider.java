package utours.ultimate.client;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.core.steorotype.Mapping;
import utours.ultimate.net.Client;

@Component
public interface ClientProvider {

    @Mapping
    @Component
    Client getClient();

}
