package utours.ultimate;

import utours.ultimate.core.Application;
import utours.ultimate.core.ApplicationConfiguration;
import utours.ultimate.core.ApplicationLauncher;
import utours.ultimate.core.Client;


public class Main {


    public static void main(String[] args) {
        ApplicationConfiguration configuration = ApplicationConfiguration.ofProperties();
        Application application = Application.ofServer(configuration);

        application.handler(Main::treatment);

        ApplicationLauncher.ofApplication(application).launch();
    }

    static void treatment(Client client) {
        try {
            String inputLine;
            while ((inputLine = client.reader().readLine()) != null) {
                client.writer().println(inputLine);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}