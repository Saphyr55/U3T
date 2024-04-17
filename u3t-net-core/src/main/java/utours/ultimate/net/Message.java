package utours.ultimate.net;

import java.io.Serializable;

public interface Message extends Serializable {

    /**
     *
     * @return
     */
    Object content();

    /**
     *
     * @return
     */
    String address();

    /**
     *
     * @return
     */
    boolean isSuccess();

    /**
     * Do not use this address.
     */
    String SUBSCRIBE_ADDRESS = "internal.server.subscribe";

}
