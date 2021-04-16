package sample;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.util.*;

public class Model {

    public RecipeMap recipeMap;
    public ArrayList<Recipe> recipeList;


    private View view;
    private Controller control;
    private Set<String> categories;
    private ArrayList<String> ingredsLeft;
    private ArrayList<Recipe> recipeDisplay;

    public Model(View view, Controller control) {
        this.view = view;
        this.control = control;
        this.loadData();
        this.ingredsLeft = new ArrayList<String>();
        this.recipeDisplay = new ArrayList<Recipe>();
        this.categories = recipeMap.getCategories();
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

        switch (btn) { // TODO: a modifier
            case "categorie" -> {
                for (String name : this.categories) {
                    this.view.newCategories(name); 
                }
                this.view.showCategories();
            }
            case "course" -> {
                this.view.liste.getChildren().clear();

                for (String s : this.ingredsLeft) {
                    this.view.newIngredient(s);
                }
                this.view.showIngredients();
            }
            case "avancee" -> this.view.showAdvanced();
        }

    }

    public void actualRecipe(String recipeName){

        for (Recipe recipe : this.recipeDisplay){
            if (recipe.getName().equals(recipeName)) {
                this.view.showRecipe(recipe, this.ingredsLeft);
            }
        }

    }

    public void search(String input) {

        if(input.length()>0){

            this.recipeDisplay.clear();
            this.view.wipe(input);

            //////// Recettes \\\\\\\\
            System.out.println("############################################# Recettes #####################################################");
            for (Recipe recipe : recipeList) {
                String name = recipe.getName();
                if (name.toLowerCase().contains(input)) { // A modifier pour faire des recherches sans accents et autres caracteres spéciaux

                    this.view.addRecipe(name);
                    this.recipeDisplay.add(recipe);
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
            this.recipeDisplay.clear();
            this.view.wipe(input);
        }
    }
}
