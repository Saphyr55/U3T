package utours.ultimate.desktop.factory;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.net.Client;

@Component
public interface ClientProvider {

    @Component
    Client getClient();

}
