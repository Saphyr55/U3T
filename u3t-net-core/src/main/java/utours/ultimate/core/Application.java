package utours.ultimate.core;

import utours.ultimate.core.base.NetServerApplication;

import java.util.concurrent.CompletableFuture;

public interface Application {

    static Application ofServer(ApplicationConfiguration configuration) {
        try {
            return new NetServerApplication(configuration);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    CompletableFuture<Void> start();

    void stop();

    void handler(Handler<Client> handler);

}
