package utours.ultimate.ioc;

import utours.ultimate.ui.ComponentInterface;

public class BComponent implements ComponentInterface {

    private ComponentInterface wrapped;

    public BComponent(ComponentInterface wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public void service() {
        wrapped.service();
        System.out.println("BComponent service");
        wrapped.service();
    }

}
