package utours.ultimate.ioc;

import utours.ultimate.ui.AComponentInterface;

public class BComponent implements AComponentInterface {

    private AComponentInterface wrapped;

    public BComponent(AComponentInterface wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public void service() {
        wrapped.service();
        System.out.println("BComponent service");
        wrapped.service();
    }

}
