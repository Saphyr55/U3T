package utours.ultimate.core;

import java.util.Objects;

public final class ComponentId {

    private String id;
    private Class<?> clazz;

    public static ComponentId ofClass(Class<?> clazz) {
        return new ComponentId(clazz.getName(), clazz);
    }

    public ComponentId(String id, Class<?> clazz) {
        this.id = id;
        this.clazz = clazz;
    }

    public static ComponentId of(Class<?> clazz, String id) {
        return new ComponentId(id, clazz);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponentId that = (ComponentId) o;
        return Objects.equals(id, that.id) && Objects.equals(clazz, that.clazz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clazz);
    }

}
