package utours.ultimate;

import utours.ultimate.core.Application;
import utours.ultimate.core.ApplicationConfiguration;
import utours.ultimate.core.Client;


public class Main {


    public static void main(String[] args) {

        ApplicationConfiguration configuration = ApplicationConfiguration.ofProperties();
        Application application = Application.ofServer(configuration);

        application.handler(Main::treatment);

        application.start();

    }

    static void treatment(Client client) {
        try (var in = client.reader(); var out = client.output()) {
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