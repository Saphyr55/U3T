package utours.ultimate.net;

import utours.ultimate.net.data.MessageData;
import utours.ultimate.net.internal.NetErrorMessage;

import java.io.Serializable;

public interface Message extends Serializable {

    /**
     *
     * @param address
     * @param content
     * @return
     */
    static Message success(String address, Object content) {
        return new MessageData(address, content, true);
    }

    /**
     *
     * @param address
     * @return
     */
    static Message error(String address) {
        return new NetErrorMessage(address);
    }

    /**
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
     * PRIVATE ADDRESS. <br/>
     *
     * Do not use this address. <br/>
     * Address use to subscribe clients.
     */
    String SUBSCRIBE_ADDRESS = "internal.server.subscribe";

}
