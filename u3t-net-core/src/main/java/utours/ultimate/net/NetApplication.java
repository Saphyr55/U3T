package utours.ultimate.net;

import utours.ultimate.net.internal.NetServerNetApplication;

public interface NetApplication {

    static NetApplication ofServer(NetApplicationConfiguration configuration) {
        try {
            return new NetServerNetApplication(configuration);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void start();

    void stop();

    void handler(String address, Handler<Context> handler);

    void sendMessage(String address, Object content);

}
