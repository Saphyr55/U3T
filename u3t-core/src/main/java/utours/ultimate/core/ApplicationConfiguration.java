package utours.ultimate.core;

public interface ApplicationConfiguration {

    static ApplicationConfiguration of(String address, int port) {
        return new ApplicationConfiguration() {
            @Override
            public String address() {
                return address;
            }

            @Override
            public int port() {
                return port;
            }
        };
    }

    String address();

    int port();

}
