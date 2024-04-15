package utours.ultimate.core;

import utours.ultimate.core.base.ServerApplication;

public interface Application {

    static Application ofServer(ApplicationConfiguration configuration) {
        try {
            return new ServerApplication(configuration);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void start();

    void stop();

    void handler(Handler<Client> handler);

}
