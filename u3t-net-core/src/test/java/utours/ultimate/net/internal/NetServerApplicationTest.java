package utours.ultimate.net.internal;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import utours.ultimate.net.NetApplication;
import utours.ultimate.net.NetServerConfiguration;
import utours.ultimate.net.Context;

import java.io.IOException;

public class NetServerApplicationTest {

    protected static final String HOST_ADDRESS = "127.0.0.1";
    protected static final int PORT = 7776;
    protected static final NetServerConfiguration configuration =
            NetServerConfiguration.of(HOST_ADDRESS, PORT);

    protected static NetApplication application;

    // TODO: Find a better method to start only one time.
    private static boolean isSetup = false;

    @BeforeAll
    static void setup() {
        if (isSetup) return;

        isSetup = true;
        application = NetApplication.serverOf(configuration);

        application.handler("an.address", NetServerApplicationTest::defaultTreatment);
        application.handler("foo.address", NetServerApplicationTest::fooTreatment);

        Thread.ofPlatform().start(application::start);
    }

    static void fooTreatment(Context context) {
        context.respond("<foo>" + context.message().content() + "</foo>");
    }

    static void defaultTreatment(Context context) throws IOException {
        var out = context.oos();
        out.writeObject(context.message());
        out.flush();
    }

    @AfterAll
    static void tearDown() {
        if (isSetup) return;
        application.stop();
    }

}