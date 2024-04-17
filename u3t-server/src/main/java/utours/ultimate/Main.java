package utours.ultimate;

import utours.ultimate.net.Application;
import utours.ultimate.net.ApplicationConfiguration;
import utours.ultimate.net.Context;


public class Main {


    public static void main(String[] args) {

        ApplicationConfiguration configuration = ApplicationConfiguration.ofProperties();

        Application application = Application.ofServer(configuration);

        application.handler("any.address", Main::treatment);

        application.start();
    }

    static void treatment(Context context) {
        System.out.println(context.message().content());
    }

}