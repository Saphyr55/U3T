package utours.ultimate;

import utours.ultimate.core.Application;
import utours.ultimate.core.ApplicationConfiguration;
import utours.ultimate.core.Client;
import utours.ultimate.core.Context;


public class Main {


    public static void main(String[] args) {

        ApplicationConfiguration configuration = ApplicationConfiguration.ofProperties();
        Application application = Application.ofServer(configuration);

        application.handler("any.address", Main::treatment);

        application.start();

    }

    static void treatment(Context context) {
        try (var in = context.reader(); var out = context.writer()) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                out.writeObject(inputLine);
                out.flush();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}