package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.*;

public class Controller {

    @FXML public ScrollPane recettePossible; // Affichage des recettes contenant le terme recherché
    @FXML public ScrollPane ingredientsPossible;
    @FXML public Pane layerCategorie;
    @FXML public Pane accueil;
    @FXML public ScrollPane layerCourse;
    @FXML public ScrollPane recipeContainer;
    @FXML public VBox diffCat; // Affichage des éléments "catégorie" (vertical et + propre)
    @FXML public VBox vb;
    @FXML public VBox vbI;
    @FXML public VBox listing;
    @FXML public VBox ingredientsRequis;
    @FXML public TextField barreRecherche;


    private Model model;
    Stack<String> frigo  = new Stack<String>();


    public void setModel(Model model) {
        this.model = model;
    }

    @FXML
    public void handleButtonClick(ActionEvent evt){
        Button test = (Button) evt.getSource();
        this.model.showLayer(test.getId());
        //Affiche IDs boutons
        System.out.println(test.getId());
    }
    
    /* @FXML
    public void categorieBtn() { }
    @FXML
    public void courseBtn() { }
    @FXML
    public void advanceBtn() { } */

    @FXML
    public void findRecipe() {
        this.model.search(barreRecherche.getCharacters().toString().toLowerCase());
    }

    @FXML
    public void watchRecipe(MouseEvent mouseEvent){
        Text recette = (Text) mouseEvent.getTarget();
        this.model.actualRecipe(recette.getText());
    }

    @FXML
    public void stackIngredients(MouseEvent mouseEvent){
        Text element = (Text) mouseEvent.getTarget();
        frigo.add(element.getText());

        System.out.println("Elements dans le frigo :" + frigo);
        System.out.println("Recette correspondantes : ");

        // Non.
        for (String s : frigo){
            for (Recipe recipe : this.model.recipeList){
                // Faire page d'accueil qui montre les recettes dispo 
                for (String ing : recipe.getIngredients()){
                    if (ing.equals(s)){
                        System.out.println(recipe.getName());
                    }
                }
            }
        }
    }
}
