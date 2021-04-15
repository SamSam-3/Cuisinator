package sample;

// import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
// import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
// import javafx.scene.control.ScrollPane;
// import javafx.scene.image.Image;
// import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
// import javafx.scene.layout.Pane;
// import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class Model {

    public RecipeMap recipeMap;
    public ArrayList<Recipe> recipeList;


    private View view;
    private Controller ctrl;
    private ArrayList<String> ingredsLeft;
    private ArrayList<Recipe> recipeDisp;

    public Model(View view, Controller ctrl) {
        this.view = view;
        this.ctrl = ctrl;
        this.loadData();
        this.ingredsLeft = new ArrayList<String>();
        this.recipeDisp = new ArrayList<Recipe>();
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

        // Slide layer categorie vers la droite
        // Slide layer liste de course vers la gauche
        // Apparition layer recherche avancée

        switch (btn) {
            case "categorie" -> {
                for (String name : recipeMap.getCategories()) {
                    this.view.newCategories(name);
                    System.out.println("Cat trouvé");
                }
                this.view.showCategories();
            }
            case "course" -> {
                for (String s : ingredsLeft) {
                    this.view.newIngredient(s);
                }
                this.view.showIngredients();
            }
            case "avancee" -> this.view.showAdvanced();
        }

    }

    public void actualRecipe(String recipeName){

        for (Recipe recipe : this.recipeDisp){
            if (recipe.getName().equals(recipeName)) {
                this.view.showRecipe(recipe.getName(), recipe.getImage(), recipe.getRequirements(), recipe.getSteps(), this.ingredsLeft);
            }
        }

    }

    public void search(String input) {

        if(input.length()>0){

            this.recipeDisp.clear();
            this.view.wipe(input);

            //////// Recettes \\\\\\\\
            System.out.println("############################################# Recettes #####################################################");
            for (Recipe recipe : recipeList) {
                String name = recipe.getName();
                if (name.toLowerCase().contains(input)) { // A modifier pour faire des recherches sans accents et autres caracteres spéciaux

                    this.view.addRecipe(name);
                    this.recipeDisp.add(recipe);
                    System.out.println(name); // Affiche les recettes correspondantes
                }
            }


            //////// Ingrédients \\\\\\\\
            System.out.println("############################################# Ingrédients #################################################");

            for(String ing : recipeMap.getIngredients()){
                if(ing.toLowerCase().contains(input)){
                    this.view.addIngredients(ing);
                    System.out.println(ing);
                }
            }

            System.out.println("############################################################################################################");

            this.view.saveState();
        }

        if (input.length() == 0) {
            this.recipeDisp.clear();
            this.view.wipe(input);
        }
    }    
}
