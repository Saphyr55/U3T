package utours.ultimate.server.model;

import utours.ultimate.game.model.Game;

public class Party {

    private Long id;
    private Game game;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
