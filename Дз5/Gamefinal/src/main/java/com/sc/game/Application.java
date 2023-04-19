package com.sc.game;

import game.core.GameService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class Application extends javafx.application.Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("game-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 450);


        GameController controller = fxmlLoader.getController();

        GameService gameService = new GameService();
        controller.setGameService(gameService);

        stage.setTitle("Jigsaw");
        InputStream iconStream = getClass().getResourceAsStream("jigsaw.png");
        Image image = new Image(iconStream);
        stage.getIcons().add(image);
        stage.setScene(scene);
        stage.show();

        GameService sdf = new GameService();
    }
}