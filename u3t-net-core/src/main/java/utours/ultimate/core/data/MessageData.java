package utours.ultimate.core.data;

import utours.ultimate.core.Message;

import java.io.Serializable;

public record MessageData(
        String address,
        Object content
) implements Message, Serializable {

}
