package utours.ultimate.core;

import java.util.Objects;

/**
 *
 */
public sealed interface ComponentId {

    /**
     *
     * @param clazz
     * @return
     */
    static ComponentId ofClass(Class<?> clazz) {
        return new ComponentIdImpl(clazz.getName(), clazz);
    }

    /**
     *
     * @param clazz
     * @param id
     * @return
     */
    static ComponentId of(Class<?> clazz, String id) {
        return new ComponentIdImpl(id, clazz);
    }

    /**
     *
     * @return
     */
    String identifier();

    /**
     *
     * @return
     */
    Class<?> clazz();

    final class ComponentIdImpl implements ComponentId {

        private final String id;
        private final Class<?> clazz;

        private ComponentIdImpl(String id, Class<?> clazz) {
            this.id = id;
            this.clazz = clazz;
        }

        public String identifier() {
            return id;
        }

        public Class<?> clazz() {
            return clazz;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ComponentId that = (ComponentId) o;
            return Objects.equals(id, that.identifier()) && Objects.equals(clazz, that.clazz());
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, clazz);
        }

        @Override
        public String toString() {
            return "ComponentIdImpl{" +
                    "id='" + id + '\'' +
                    ", clazz=" + clazz +
                    '}';
        }
    }

}
