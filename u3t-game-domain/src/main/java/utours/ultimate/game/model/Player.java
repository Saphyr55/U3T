package utours.ultimate.game.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public record Player(String id,
                     String name,
                     Integer score
) implements Serializable {

    public static Builder builder() {
        return Builder.newBuilder();
    }

    public static Player ofDefault() {
        return Player.builder().build();
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return Objects.equals(id, player.id);
    }

    public static class Builder {

        private String id = UUID.randomUUID().toString();
        private String name = "Player";
        private Integer score = 0;

        public static Builder copyOf(Player player) {
            return newBuilder(player.id, player.name)
                    .withScore(player.score);
        }

        public static Builder newBuilder() {
            return new Builder();
        }
        
        public static Builder newBuilder(String id, String name) {
            return newBuilder()
                    .withId(id)
                    .withName(name);
        }

        private Builder() { }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withScore(Integer score) {
            this.score = score;
            return this;
        }


        public Player build() {
            return new Player(id, name, score);
        }

    }

}
