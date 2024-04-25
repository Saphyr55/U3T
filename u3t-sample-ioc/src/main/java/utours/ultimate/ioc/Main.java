package utours.ultimate.ioc;

import utours.ultimate.core.ApplicationContext;
import utours.ultimate.core.ModularApplicationContext;
import utours.ultimate.ui.AComponentInterface;

public class Main {

    
    public static void main(String[] args) {

        ApplicationContext applicationContext = new ModularApplicationContext();

        applicationContext
                .getContainerReadOnly()
                .getAdditionalComponent(AComponentInterface.class)
                .forEach(AComponentInterface::service);

    }


}
