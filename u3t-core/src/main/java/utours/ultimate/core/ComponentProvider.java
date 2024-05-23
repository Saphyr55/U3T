package utours.ultimate.core;

@FunctionalInterface
public interface ComponentProvider {

    ComponentWrapper get();

    static ComponentProvider singleton(ComponentWrapper cw) {
        return new Singleton(cw);
    }

    static ComponentProvider prototype(ComponentWrapper cw) {
        throw new IllegalStateException("Not implemented yet.");
    }

    class Singleton implements ComponentProvider {

        private final ComponentWrapper componentWrapper;

        public Singleton(ComponentWrapper componentWrapper) {
            this.componentWrapper = componentWrapper;
        }

        @Override
        public ComponentWrapper get() {
            return componentWrapper;
        }

    }

}
