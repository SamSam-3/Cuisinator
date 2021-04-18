package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

        primaryStage.widthProperty().addListener((obs, oldVal, newVal ) -> {
            System.out.println("Largeur :"+newVal.intValue());
            ctrl.accueil.setPrefWidth(newVal.intValue());
        });

        primaryStage.heightProperty().addListener((obs, oldVal, newVal ) -> {
            System.out.println("Hauteur :"+newVal.intValue());
            ctrl.accueil.setPrefHeight(newVal.intValue());
        });

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
