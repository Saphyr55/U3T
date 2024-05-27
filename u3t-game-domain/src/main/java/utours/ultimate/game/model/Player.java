package utours.ultimate.game.model;

import java.io.Serializable;

public record Player(
        String id,
        String name,
        Integer score
) implements Serializable {

    @Override
    public boolean equals(Object obj) {
        return id != null && id.equals(((Player) obj).id());
    }

    public static class Builder {

        private String id;
        private String name;
        private Integer score = 0;

        public static Builder copyOf(Player player) {
            return newBuilder(player.id, player.name)
                    .score(player.score);
        }

        public static Builder newBuilder(String id, String name) {
            Builder builder = new Builder();
            builder.id = id;
            builder.name = name;
            return builder;
        }

        private Builder() { }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder score(Integer score) {
            this.score = score;
            return this;
        }


        public Player build() {
            return new Player(id, name, score);
        }

    }

}
