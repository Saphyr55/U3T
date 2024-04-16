package utours.ultimate.net.internal;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utours.ultimate.net.Application;
import utours.ultimate.net.ApplicationConfiguration;
import utours.ultimate.net.Context;
import utours.ultimate.net.data.MessageData;

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
        if (isSetup) return;

        isSetup = true;
        application = Application.ofServer(configuration);

        application.handler("an.address", NetServerApplicationTest::defaultTreatment);
        
        application.handler("foo.address", NetServerApplicationTest::fooTreatment);

        Thread.ofPlatform().start(application::start);

    }

    static void fooTreatment(Context context) throws IOException {
        var out = context.writer();
        String content = "<foo>" + context.message().content() + "</foo>";
        out.writeObject(new MessageData(context.address(), content, true));
        out.flush();
    }

    static void defaultTreatment(Context context) throws IOException {
        var out = context.writer();
        out.writeObject(context.message());
        out.flush();
    }

    @AfterAll
    static void tearDown() {
        if (isSetup) return;
        application.stop();
    }

}