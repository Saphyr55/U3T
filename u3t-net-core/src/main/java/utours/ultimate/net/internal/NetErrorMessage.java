package utours.ultimate.net.internal;

import utours.ultimate.net.Message;

public record NetErrorMessage(String address) implements Message {

    @Override
    public Object content() {
        return null;
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

}
