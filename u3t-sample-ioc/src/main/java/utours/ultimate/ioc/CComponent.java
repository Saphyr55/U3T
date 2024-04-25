package utours.ultimate.ioc;

import utours.ultimate.ui.ComponentInterface;

public class CComponent implements ComponentInterface {

    @Override
    public void service() {
        System.out.println("CComponent service");
    }

}
