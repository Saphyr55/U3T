package utours.ultimate.core;

import utours.ultimate.core.internal.ComponentMethodHandleImpl;

import java.lang.invoke.MethodHandle;

public interface ComponentMethodHandle {

    static ComponentMethodHandle of(ComponentId componentId, MethodHandle mh) {
        return new ComponentMethodHandleImpl(componentId, mh);
    }

    MethodHandle method();

    ComponentId componentId();


}
