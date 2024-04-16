package utours.ultimate.net.internal;

import org.junit.jupiter.api.BeforeAll;
import utours.ultimate.net.Application;
import utours.ultimate.net.ApplicationConfiguration;
import utours.ultimate.net.Context;

import java.io.IOException;

public class NetServerApplicationTest {

    protected static final String HOST_ADDRESS = "127.0.0.1";
    protected static final int PORT = 7776;
    protected static final ApplicationConfiguration configuration =
            ApplicationConfiguration.of(HOST_ADDRESS, PORT);

    protected static Application application;

    // TODO: Find a better method to start only one time.
    private static boolean isSetup = false;

    @BeforeAll
    static void setup() {
        if (!isSetup) {
            isSetup = true;
            application = Application.ofServer(configuration);
            application.handler("an.address", NetServerApplicationTest::defaultTreatment);
            Thread.ofPlatform().start(application::start);
        }
    }

    static void defaultTreatment(Context context) throws IOException {
        var out = context.writer();
        out.writeObject(context.message());
        out.flush();
    }

}