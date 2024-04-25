package utours.ultimate.ioc;

import utours.ultimate.ui.ComponentInterface;

public class AComponent implements ComponentInterface {

    public AComponent() {

    }

    @Override
    public void service() {
        System.out.println("AComponent service");
    }

}
