package utours.ultimate.net;

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

}
