package sample;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Dimension2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();

        Controller ctrl = loader.getController();
        Model model = new Model(ctrl);
        ctrl.initModel(model);

        primaryStage.setTitle("Cuisinator");
        primaryStage.setFullScreen(true);

        Scene scene = new Scene(root);
        scene.getStylesheets().add("theme.css");
        primaryStage.setScene(scene);

        primaryStage.widthProperty().addListener((obs, oldValW, newValW ) -> {

            ctrl.accueil.setPrefWidth(newValW.intValue());
            ctrl.accueil.setPrefWidth(newValW.intValue());
            ctrl.toolbar.setPrefWidth(newValW.intValue());

        });

        primaryStage.heightProperty().addListener(((obs, oldValH, newValH) -> {

            ctrl.accueil.setPrefHeight(newValH.intValue());
            ctrl.accueil.setPrefHeight(newValH.intValue());


        }));

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
