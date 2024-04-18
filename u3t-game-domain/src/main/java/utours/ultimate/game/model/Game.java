package utours.ultimate.game.model;

public class Game {

    private int gameID;
    private Player crossPlayer = new Player();
    private Player roundPlayer = new Player();
    private Board board = new Board();


    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public Player getCrossPlayer() {
        return crossPlayer;
    }

    public void setCrossPlayer(Player crossPlayer) {
        this.crossPlayer = crossPlayer;
    }

    public Player getRoundPlayer() {
        return roundPlayer;
    }

    public void setRoundPlayer(Player roundPlayer) {
        this.roundPlayer = roundPlayer;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
