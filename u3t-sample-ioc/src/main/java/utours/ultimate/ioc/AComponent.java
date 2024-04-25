package utours.ultimate.ioc;

import utours.ultimate.ui.AComponentInterface;

public class AComponent implements AComponentInterface {


    @Override
    public void service() {
        System.out.println("AComponent service");
    }

}
