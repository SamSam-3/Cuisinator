package sample;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class Model {

    public RecipeMap recipeMap;
    public ArrayList<Recipe> recipeList;


    private View view;
    private Controller ctrl;

    public Model(View view, Controller ctrl)
    {
        this.view = view;
        this.ctrl = ctrl;
        this.loadData();
    }

    @SuppressWarnings("unchecked")
    public void loadData() {
        recipeMap = (RecipeMap)DataManager.load("recipe-map");
        recipeList = (ArrayList<Recipe>)DataManager.load("recipe-list");
        // TODO: fav.save

        if (recipeMap == null || recipeList == null) {
            throw new RuntimeException("Data base not found");
        }
    }


    public void showLayer(String btn){

        switch(btn){
            case "categorie":

                // Slide layer categorie vers la droite
                for (String name : recipeMap.getCategories()) {
                    this.view.newCategories(name);
                    System.out.println("Cat trouvé");
                }
                this.view.showCategories();
                break;

            case "course":

                // Slide layer liste de course vers la gauche
                for (String s : this.ctrl.ingredientsManquant) {
                    this.view.newIngredient(s);
                }

                this.view.showIngredients();

                break;

            case "avancee":

                // Apparition layer recherche avancée
                this.view.showAdvanced();
                break;
        }

    }

    public void actualRecipe(String recipeName){

        for (Recipe recipe : this.ctrl.recetteclickable){
            if (recipe.getName().equals(recipeName)) {
                this.view.showRecipe(recipe.getName(), recipe.getImage(), recipe.getRequirements(), recipe.getSteps());
            }
        }

    }

    public void search(String input) {

        if(input.length()>0){

            this.ctrl.vb.getChildren().clear();
            this.ctrl.vbI.getChildren().clear();
            this.ctrl.recetteclickable.clear();

            //// View
            this.ctrl.listing.toFront();
            this.ctrl.listing.setVisible(true);
            this.ctrl.recettePossible.setVisible(true);

            //////// Recettes \\\\\\\\
            System.out.println("############################################# Recettes #####################################################");
            for (Recipe recipe : recipeList) {
                String name = recipe.getName();
                if (name.toLowerCase().contains(input)) { // A modifier pour faire des recherches sans accents et autres caracteres spéciaux

                    /// View
                    Label lbl = new Label(name);
                    this.ctrl.vb.getChildren().add(lbl);
                    this.ctrl.recetteclickable.add(recipe);

                    System.out.println(name); // Affiche les recettes correspondantes
                }
            }
            /// View
            this.ctrl.recettePossible.setContent(this.ctrl.vb); // TODO: deplacer tout ca dans la vue

            //////// Ingrédients \\\\\\\\
            System.out.println("############################################# Ingrédients #################################################");

            for(String ing : recipeMap.getIngredients()){
                if(ing.toLowerCase().contains(input)){

                    /// View
                    Label lb = new Label(ing);
                    lb.setFont(new Font("Arial",15));
                    Button cancel = new Button("X");
                    cancel.setPrefSize(5,5);
                    HBox hb = new HBox();
                    hb.getChildren().add(lb);
                    hb.getChildren().add(cancel);
                    hb.setAlignment(Pos.CENTER);
                    this.ctrl.vbI.getChildren().add(hb);

                    System.out.println(ing);
                }
            }
            this.ctrl.ingredientsPossible.setContent(this.ctrl.vbI);
            System.out.println("############################################################################################################");
        }

        if (input.length() == 0) {
            this.ctrl.listing.setVisible(false);

            this.ctrl.vb.getChildren().clear();
            this.ctrl.vbI.getChildren().clear();
            this.ctrl.recetteclickable.clear();
        }
    }    
}
