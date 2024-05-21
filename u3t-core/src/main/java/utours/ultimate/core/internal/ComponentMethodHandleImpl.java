package utours.ultimate.core.internal;

import utours.ultimate.core.ComponentId;
import utours.ultimate.core.ComponentMethodHandle;

import java.lang.invoke.MethodHandle;

public record ComponentMethodHandleImpl(ComponentId componentId, MethodHandle method)
        implements ComponentMethodHandle {

}
