package utours.ultimate.game.model;

public record Player(
    String name,
    Integer score
) {

    public static class Builder {

        private String name;
        private Integer score = 0;

        public static Builder copyOf(Player player) {
            return newBuilder(player.name)
                    .score(player.score);
        }

        public static Builder newBuilder(String name) {
            Builder builder = new Builder();
            builder.name = name;
            return builder;
        }

        private Builder() { }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder score(Integer score) {
            this.score = score;
            return this;
        }

        public Player build() {
            return new Player(name, score);
        }

    }

}
