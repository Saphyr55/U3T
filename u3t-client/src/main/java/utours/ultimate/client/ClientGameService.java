package utours.ultimate.client;

import utours.ultimate.core.steorotype.Component;
import utours.ultimate.game.feature.GameService;
import utours.ultimate.game.feature.internal.GameServiceBase;
import utours.ultimate.game.model.*;

@Component
public final class ClientGameService implements GameService {

    private final AsyncGameInventory asyncGameInventory;
    private final GameService gameService;

    private Player clientPlayer;

    public ClientGameService(AsyncGameInventory asyncGameInventory) {

        this.asyncGameInventory = asyncGameInventory;
        this.gameService = new GameServiceBase();
    }

    public Player getClientPlayer() {
        return clientPlayer;
    }

    public void setClientPlayer(Player clientPlayer) {
        this.clientPlayer = clientPlayer;
    }

    @Override
    public boolean isPlayableAction(Game game, Action action) {
        return gameService.isPlayableAction(game, action) &&
                clientPlayer.equals(game.currentPlayer());
    }

    @Override
    public boolean isPlayableCell(Cell cell) {
        return gameService.isPlayableCell(cell);
    }

    @Override
    public Cell cellOfPlayer(Game game, Player currentPlayer) {
        return gameService.cellOfPlayer(game, currentPlayer);
    }

    @Override
    public Cell cellAt(Game game, Cell.Pos posOut) {
        return gameService.cellAt(game, posOut);
    }

    @Override
    public Cell cellAt(Game game, Cell.Pos posOut, Cell.Pos posIn) {
        return gameService.cellAt(game, posOut, posIn);
    }

    @Override
    public Game turnPlayer(Game game) {

        game = gameService.turnPlayer(game);

        asyncGameInventory.update(game);

        return game;
    }

    @Override
    public Player oppositePlayer(Game game, Player player) {
        return gameService.oppositePlayer(game, player);
    }

    @Override
    public Game placeMark(Game game, Action action) {

        game = gameService.placeMark(game, action);

        return game;
    }

    @Override
    public IsWinGame checkInnerWinner(Game game, Action action) {
        return gameService.checkInnerWinner(game, action);
    }

    @Override
    public boolean checkOuterWinner(Game game, Cell.Pos lastPos) {
        return gameService.checkOuterWinner(game, lastPos);
    }


}
