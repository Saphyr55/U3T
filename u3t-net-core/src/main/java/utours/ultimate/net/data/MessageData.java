package utours.ultimate.net.data;

import utours.ultimate.net.Message;

import java.io.Serializable;

public record MessageData(
        String address,
        Object content,
        boolean isSuccess
) implements Message, Serializable {

}
