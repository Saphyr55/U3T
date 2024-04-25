package utours.ultimate.ioc;

import utours.ultimate.core.ApplicationContext;
import utours.ultimate.core.ModularApplicationContext;
import utours.ultimate.core.ModuleProvider;
import utours.ultimate.core.internal.ClassPathXmlModuleProvider;
import utours.ultimate.ui.ComponentInterface;

public class Main {


    public static void main(String[] args) {

        ModuleProvider moduleProvider = new ClassPathXmlModuleProvider();
        ApplicationContext applicationContext = new ModularApplicationContext(moduleProvider);

        applicationContext
                .getContainerReadOnly()
                .getAdditionalComponent(ComponentInterface.class)
                .forEach(ComponentInterface::service);

    }


}
