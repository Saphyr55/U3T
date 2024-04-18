package utours.ultimate.desktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import utours.ultimate.core.ClassPathResource;
import utours.ultimate.game.model.Board;
import utours.ultimate.game.model.Cell;
import utours.ultimate.game.model.Game;

import java.awt.*;
import java.io.IOException;

public class HelloApplication extends Application {

    Label label = new Label();

    @Override
    public void start(Stage stage) throws Exception {

        // FXMLLoader fxmlLoader = new FXMLLoader(ClassPathResource.getResource("views/hello-view.fxml"));

        Label msgLabel = new Label("X must play");
        Cell[][] cells = new Cell[9][9];

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell.Empty();
            }
        }

        Board board = new Board();
        board.setCells(cells);

        Game game = new Game();
        game.setGameID(1);
        game.setBoard(board);

        GridPane grid = new GridPane();
        BorderPane border = new BorderPane();
        border.setCenter(grid);

        Scene scene = new Scene(grid, 500, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}