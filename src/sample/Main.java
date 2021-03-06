package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {

    private int nbCardSave = 3; //Valeur par défault pour les écran 1280/720

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();

        Controller ctrl = loader.getController();
        Model model = new Model();
        ctrl.initModel(model);
        ctrl.mainPage();

        primaryStage.setTitle("Cuisinator");
        //primaryStage.setFullScreen(true);

        Scene scene = new Scene(root);
        scene.getStylesheets().add("theme.css");
        primaryStage.setScene(scene);

        primaryStage.setMinWidth(700);
        primaryStage.setMinHeight(500);
        double bar = ctrl.barreRecherche.getLayoutX();

        primaryStage.widthProperty().addListener((obs, oldValW, newValW ) -> {

            ctrl.nbCard=((int) (ctrl.accueil.getWidth()-700)/300) +2;

            if(ctrl.nbCard!=nbCardSave && ctrl.actuPage.equals("mainPage")){
                nbCardSave = ctrl.nbCard;
                ctrl.mainPage();
            }
            if(ctrl.nbCard!=nbCardSave && ctrl.actuPage.equals("catPage")){
                nbCardSave = ctrl.nbCard;
                ctrl.categoryPage(ctrl.catCourrant);
            }

            //Pane général
            ctrl.accueil.setPrefWidth(newValW.intValue());
            ctrl.accueil.setPrefWidth(newValW.intValue());
            ctrl.recipeContainer.setPrefWidth(newValW.intValue());
            VBox containerContent = (VBox) ctrl.recipeContainer.getContent();
            containerContent.setPrefWidth(newValW.intValue());

            //Toolbar
            ctrl.toolbar.setPrefWidth(newValW.intValue());
            ctrl.course.setLayoutX(ctrl.toolbar.getWidth()-28-ctrl.course.getWidth());


            //Déplacement des listing de recherche
            ctrl.recipePossible.setLayoutX(ctrl.barreRecherche.getLayoutX());
            ctrl.recipePossible.setLayoutY(ctrl.barreRecherche.getLayoutY()+ctrl.barreRecherche.getHeight());

            if(newValW.intValue()-560<=(bar+ctrl.barreRecherche.getWidth())){
                //Déplacement l'une sous l'autre
                ctrl.barreRecherche.setLayoutY(10);
                ctrl.barreTags.setLayoutY(ctrl.barreRecherche.getHeight()+10);

                //Déplacement en X
                int x = newValW.intValue()/2 - (int) ctrl.barreRecherche.getWidth()/2;
                ctrl.barreRecherche.setLayoutX(x);
                ctrl.barreTags.setLayoutX(x);

            } else {
                ctrl.barreTags.setLayoutY(25);
                ctrl.barreRecherche.setLayoutY(25);

                ctrl.barreTags.setLayoutX(newValW.intValue() - 560);
                ctrl.barreRecherche.setLayoutX(ctrl.categorie.getLayoutX()+100+ctrl.categorie.getWidth());
            }

            //tags / etc..
            ctrl.tags.setPrefWidth(newValW.intValue());
            ctrl.categoriesPossible.setPrefWidth(newValW.intValue()/2);
            ctrl.categoriesPossible.getContent().autosize();
            ctrl.ingredientsPossible.setPrefWidth(newValW.intValue()/2);
            ctrl.ingredientsPossible.getContent().autosize();
            ctrl.ingredientsPossible.setLayoutX(ctrl.categoriesPossible.getWidth());

            //layer course et cat
            if(ctrl.etatCA == 0){
                ctrl.layerCategorie.setLayoutX(-ctrl.layerCategorie.getWidth());
            }
            if(ctrl.etatCO == 1){
                ctrl.layerCourse.setLayoutX(ctrl.accueil.getWidth()); //Petit bug graphique a corrigé quand on veux
            }

            ctrl.layerCategorie.setPrefWidth(newValW.intValue()/3); //Changer taille du texte en fonction de la taille du layer
            ctrl.layerCourse.setPrefWidth(newValW.intValue()/3); //Changer taille du texte en fonction de la taille du layer

            AnchorPane courseContent = (AnchorPane) ctrl.layerCourse.getContent();
            courseContent.setPrefWidth(newValW.intValue()/3); //Changer taille du texte en fonction de la taille du layer

            ctrl.diffCat.setPrefWidth(ctrl.layerCategorie.getWidth());
            ctrl.layerCategorie.getChildren().get(0).setLayoutX((ctrl.layerCategorie.getWidth()/2)-100);

            //Met à jour la barre de recherche des tags
            ctrl.findByIngredients.setPrefWidth(newValW.intValue());
            ctrl.categoriesPossible.setMinWidth(newValW.intValue()/2);
            ctrl.ingredientsPossible.setMinWidth(newValW.intValue()/2);
            ctrl.tags.setPrefWidth(newValW.intValue());

        });

        primaryStage.heightProperty().addListener(((obs, oldValH, newValH) -> {

            //Pane général
            ctrl.accueil.setPrefHeight(newValH.intValue());
            ctrl.accueil.setPrefHeight(newValH.intValue());
            ctrl.recipeContainer.setMaxHeight(newValH.intValue()-80);
            ctrl.recipeContainer.getContent().autosize();
            ctrl.diffCat.autosize();
        }));

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
