package utours.ultimate.game.feature.internal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utours.ultimate.game.feature.U3TGameService;
import utours.ultimate.game.model.Board;
import utours.ultimate.game.model.Cell;
import utours.ultimate.game.model.U3TGame;

import static org.junit.jupiter.api.Assertions.assertTrue;

class U3TGameServiceInternalTest {

    U3TGameService gameService;
    U3TGame game;

    @BeforeEach
    void setUp() {

        Cell[][] cells = {
                { new Cell.Round(), new Cell.Round(), new Cell.Cross() },
                { new Cell.Round(), new Cell.Cross(), new Cell.Empty() },
                { new Cell.Cross(), new Cell.Empty(), new Cell.Empty() },
        };

        gameService = new U3TGameServiceInternal();
        game = U3TGame.Builder.newBuilder()
                .board(new Board(cells))
                .build();
    }

    @Test
    void testCheckOuterWinner() {
        assertTrue(gameService.checkOuterWinner(game, Cell.pos(1, 1)));
    }

    @AfterEach
    void tearDown() {

    }

}