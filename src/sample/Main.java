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

    int actuHeight = 400;
    int actuWidth = 600;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();

        Controller ctrl = loader.getController();
        Model model = new Model(ctrl);
        ctrl.initModel(model);

        primaryStage.setTitle("Cuisinator");
        //primaryStage.setFullScreen(true);


        double maxCoef = Screen.getScreens().get(0).getBounds().getMaxX() + Screen.getScreens().get(0).getBounds().getMaxY();

        Scene scene = new Scene(root);
        scene.getStylesheets().add("theme.css");
        primaryStage.setScene(scene);

        primaryStage.widthProperty().addListener((obs, oldValW, newValW ) -> {
            actuWidth = newValW.intValue();

            ctrl.accueil.setPrefWidth(newValW.intValue());
            ctrl.accueil.setPrefWidth(newValW.intValue());
            ctrl.toolbar.setPrefWidth(newValW.intValue());

            //Main Page
            ctrl.recipeContainer.setPrefWidth(newValW.intValue()-14);
            ctrl.recipeContainer.getContent().autosize();

            //Buttons
            double coef = ((newValW.intValue()+actuHeight)*(maxCoef/10))/(maxCoef);
            System.out.println(coef/100);
            ctrl.categorie.setLayoutX(14*(coef/100));
            ctrl.categorie.setLayoutY(14*(coef/100));
            ctrl.categorie.setPrefWidth(52*(coef/100));
            ctrl.categorie.setPrefHeight(52*(coef/100));

            ctrl.course.setLayoutX(500*(coef/100));
            ctrl.course.setLayoutY(14*(coef/100));
            ctrl.course.setPrefWidth(52*(coef/100));
            ctrl.course.setPrefHeight(52*(coef/100));

        });

        primaryStage.heightProperty().addListener(((observableValue, oldValH, newValH) -> {
            actuHeight = newValH.intValue();

            ctrl.accueil.setPrefHeight(newValH.intValue());
            ctrl.accueil.setPrefHeight(newValH.intValue());
            ctrl.toolbar.setPrefHeight((newValH.intValue()-37)*0.2);

            //Main Page
            ctrl.recipeContainer.setLayoutY((newValH.intValue()-37)*0.2);
            ctrl.recipeContainer.setMinHeight((newValH.intValue()-37)*0.8);
            ctrl.recipeContainer.getContent().autosize();

            //Buttons
            double coef = ((newValH.intValue()+actuWidth)*(maxCoef/10))/(maxCoef);
            System.out.println(coef/100);
            ctrl.categorie.setLayoutX(14*(coef/100));
            ctrl.categorie.setLayoutY(14*(coef/100));
            ctrl.categorie.setPrefWidth(52*(coef/100));
            ctrl.categorie.setPrefHeight(52*(coef/100));

            ctrl.course.setLayoutX(500*(coef/100));
            ctrl.course.setLayoutY(14*(coef/100));
            ctrl.course.setPrefWidth(52*(coef/100));
            ctrl.course.setPrefHeight(52*(coef/100));
        }));

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
