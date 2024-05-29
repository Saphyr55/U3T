package utours.ultimate.net;

import java.net.ServerSocket;
import java.util.List;
import java.util.Map;

public interface NetServer {

    /**
     * Stop the network server.
     */
    void stop();

    /**
     * Accept a client.
     *
     * @return A client.
     */
    Client acceptClient();

    /**
     *
     * @param address
     * @return
     */
    boolean hasAddress(String address);

    /**
     *
     * @return
     */
    Map<String, List<Handler<Context>>> handlers();

    /**
     *
     * @return
     */
    Map<String, List<Client>> subscribers();

    /**
     *
     * @return
     */
    ServerSocket serverSocket();
}
