package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.*;

public class Controller {

    ///////// VARIABLES \\\\\\\\\\
    private Model model;
    private Set<String> ingredients;  
    private Set<String> categories;
    int ca=0;
    int co=0;
    int av=0;
    Stack<String> frigo  = new Stack<String>();
    ArrayList<Recipe> recetteclickable =  new ArrayList<Recipe>();
    ArrayList<String> ingredientsManquant = new ArrayList<String>();

    ///////// ELEMENTS INTERACTIFS \\\\\\\\\\

    @FXML public TextField barreRecherche;

    ///////// FONCTIONS \\\\\\\\\\

    public void setModel(Model model) {
        this.model = model;
        this.ingredients = model.recipeMap.getIngredients();
        this.categories = model.recipeMap.getCategories();
    }


    @FXML
    public void handleButtonClick(ActionEvent evt){
            Button test = (Button) evt.getSource();
            this.model.showLayer(test.getId());
            //Affiche IDs boutons
            System.out.println(test.getId());
        }

    @FXML
    public void findRecipe() {
        String input = barreRecherche.getCharacters().toString().toLowerCase();
        this.model.search(input);

    }

    @FXML
    public void watchRecipe(MouseEvent mouseEvent){

        Text recette = (Text) mouseEvent.getTarget();
        this.model.showRecipe(recette);
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
