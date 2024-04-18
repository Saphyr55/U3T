package utours.ultimate.net.internal;

import utours.ultimate.net.Message;

public class NetErrorMessage implements Message {

    private final String address;

    public NetErrorMessage(String address) {
        this.address = address;
    }

    @Override
    public Object content() {
        return null;
    }

    @Override
    public String address() {
        return address;
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

}
