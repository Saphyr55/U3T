package utours.ultimate.game.feature.internal;

import utours.ultimate.game.model.U3TGameState;

import java.util.function.Consumer;

public class U3TGameStateHandling implements Consumer<U3TGameState> {

    @Override
    public void accept(U3TGameState state) {

        switch (state) {
            case Ready -> {
                System.out.println("Ready");
            }
            case CrossToPlay -> {
                System.out.println("CrossToPlay");
            }
            case RoundToPlay -> {
                System.out.println("RoundToPlay");
            }
            case Finish -> {
                System.out.println("Finish");
            }
        }

        if (state != U3TGameState.Finish) {
            accept(state.nextState());
        }

    }

}
