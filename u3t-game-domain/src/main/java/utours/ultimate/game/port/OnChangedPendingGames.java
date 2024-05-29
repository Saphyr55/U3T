package utours.ultimate.game.port;

import utours.ultimate.game.model.PendingGame;

import java.util.List;

public interface OnChangedPendingGames {

    void onChanged(List<PendingGame> games);

}
