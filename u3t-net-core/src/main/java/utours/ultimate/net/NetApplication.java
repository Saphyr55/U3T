package utours.ultimate.net;

import utours.ultimate.net.internal.NetServerApplication;

public interface NetApplication {

    static NetApplication serverOf(NetServerConfiguration configuration) {
        try {
            return new NetServerApplication(configuration);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void start();

    void stop();

    void handler(String address, Handler<Context> handler);

    void sendMessage(String address, Object content);

}
