package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class Controller {

    //####### VARIABLES #######

    int ca=0;
    int co=0;
    int av=0;
    ArrayList<Recipe> r = init();
    ArrayList<String> I = null;

    ArrayList<Recipe> recetteclickable = new ArrayList<Recipe>();
    ArrayList<String> ingredientsManquant = new ArrayList<String>();

    //####### ELEMENTS INTERACTIFS #######

    @FXML
    private TextField barreRecherche;

    @FXML
    ImageView imageRecette = new ImageView();

    @FXML
    Label titreRecette = new Label();

    //####### AFFICHAGE PANE #######

    @FXML
    private ScrollPane recettePossible; //Affichage des recettes contenant le terme recherché

    @FXML
    private ScrollPane ingredientsPossible;

    @FXML
    private Pane layerCategorie;

    @FXML
    private ScrollPane layerCourse;

    @FXML
    private ScrollPane recipeContainer;

    @FXML
    private VBox diffCat; //Affichage des éléments "catégorie" (vertical et + propre)

    @FXML
    private VBox vb;

    @FXML
    private VBox vbI;

    @FXML
    private VBox listing;

    @FXML
    private VBox ingredientsRequis;


    //####### FONCTIONS #######

    private ArrayList<Recipe> init(){

        ArrayList<Recipe> r = null;

        try {
            FileInputStream fis = new FileInputStream("recipes.data");
            ObjectInputStream ois = new ObjectInputStream(fis);

            r = (ArrayList<Recipe>) ois.readObject();

            ois.close();
            fis.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return r;
    } //Initialisation de l'ArrayList plus simple

    private ArrayList<String> getCategories(){
        ArrayList<String> categories = new ArrayList<String>();

        for (Recipe recipe : r) {
            if (!categories.contains(recipe.getCategory())) {
                categories.add(recipe.getCategory());
            }
        }
        return categories;
    } //Récupère les catégories pour le layer Catégorie

    private ArrayList<String> getAllIngredients(){
        for (Recipe recipe : r) {
            for (String ing : recipe.getIngredients()) {
                if(!I.contains(ing)){
                    I.add(ing);
                }
            }
        }

        return I;
    }

    @FXML
    public void handleButtonClick(ActionEvent evt){
            Button test = (Button) evt.getSource();

            //Affiche IDs boutons
            System.out.println(test.getId());
            switch(test.getId()){
                case "categorie":

                    //Slide layer categorie vers la droite
                    if(ca == 0){
                        for(int i=0;i<getCategories().size();i++){
                            Label lb = new Label(getCategories().get(i));
                            lb.setFont(new Font("Arial",20));
                            diffCat.setSpacing(10);
                            diffCat.setAlignment(Pos.TOP_CENTER);
                            diffCat.getChildren().add(lb);
                        }
                        layerCategorie.setTranslateX(Math.abs(layerCategorie.getLayoutX()));
                        layerCategorie.toFront();
                        ca=1;

                    } else {
                        layerCategorie.setTranslateX(0);
                        diffCat.getChildren().clear();
                        ca=0;
                    }
                    break;

                case "course":
                    //Slide layer liste de course vers la gauche

                    VBox liste = new VBox();
                    for (String s : ingredientsManquant) {
                        Label ig = new Label(s);
                        ig.setFont(new Font("Arial", 15));
                        liste.getChildren().add(ig);
                    }

                    layerCourse.setContent(liste);
                    layerCourse.toFront();

                    if(co == 0){
                        layerCourse.setTranslateX(-layerCourse.getWidth());
                        co=1;
                    } else {
                        layerCourse.setTranslateX(0);
                        co=0;
                    }
                    break;

                case "avancee":
                    //Apparition layer recherche avancée
                    if(av == 0 && recettePossible.isVisible()) {
                        vbI.getChildren().add(new Label("Test"));
                        ingredientsPossible.setVisible(true);
                        av=1;
                    } else {
                        ingredientsPossible.setVisible(false);

                        av=0;
                    }
                    break;
            }
        }

    @FXML
    public void findRecipe() {
        System.out.println(recettePossible.isVisible());

        if (barreRecherche.getCharacters().length() > 0) {
            vb.getChildren().clear();
            recetteclickable.clear();

            listing.toFront();
            listing.setVisible(true);
            recettePossible.setVisible(true);

            System.out.println("############################################################################################################");
            for (Recipe recipe : r) {
                if (recipe.getName().toLowerCase().contains(barreRecherche.getCharacters().toString().toLowerCase())) { //A modifier pour faire des recherches sans accents et autres caracteres spéciaux

                    Label lb = new Label(recipe.getName());
                    lb.setFont(new Font("Arial", 15));
                    vb.getChildren().add(lb);
                    recetteclickable.add(recipe);

                    System.out.println(recipe.getName()); //Affiche les recettes correspondantes
                }
            }
            recettePossible.setContent(vb);
            System.out.println("############################################################################################################");


        }

        if (barreRecherche.getCharacters().length() == 0) {
            listing.setVisible(false);

            vb.getChildren().clear();
            recetteclickable.clear();
            vbI.getChildren().clear();
        }
    }

    @FXML
    public void watchRecipe(MouseEvent mouseEvent){

        Text recette = (Text) mouseEvent.getTarget();

        for(Recipe recipe : recetteclickable){
            if(recipe.getName().equals(recette.getText())){
                ingredientsRequis.getChildren().clear();
                recettePossible.setVisible(false);
                recipeContainer.setVisible(true);
                titreRecette.setText(recipe.getName());
                //imageRecette.setImage(new Image(r.getImage())); //Affichage de l'image (Ajouter une banque d'images)

                for(String ing : recipe.getIngredients()){
                    CheckBox cb = new CheckBox(ing);

                    cb.setOnAction(actionEvent -> {

                        CheckBox ch = (CheckBox) actionEvent.getTarget();
                        if(ingredientsManquant.contains(ch.getText())){
                           ingredientsManquant.remove(ch.getText());
                        } else {
                            ingredientsManquant.add(ch.getText());
                        }
                        System.out.println(ingredientsManquant);
                    });

                    ingredientsManquant.add(ing);
                    ingredientsRequis.getChildren().add(cb);
                }
            }
        }

    }

    public Controller() {

        }
}
