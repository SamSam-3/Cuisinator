package sample;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
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
            ctrl.recipeContainer.setPrefWidth(newValW.intValue()-14);
            ctrl.recipeContainer.getContent().autosize();
        });

        primaryStage.heightProperty().addListener(((observableValue, oldValH, newValH) -> {
            ctrl.accueil.setPrefHeight(newValH.intValue());
            ctrl.accueil.setPrefHeight(newValH.intValue());
            ctrl.toolbar.setPrefHeight(newValH.intValue()*0.2);
            ctrl.recipeContainer.setPrefHeight(newValH.intValue()*0.8);
            ctrl.recipeContainer.getContent().autosize();
        }));

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
