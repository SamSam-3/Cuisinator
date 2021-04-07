package sample;

import java.util.ArrayList;

public class Model {

    private RecipeMap mappedRecipe;

    public Model() {
        // TODO: Vérifier que les recettes ne sont pas déjà existantes sur la machine

        // Récuperation des differentes recettes
        ArrayList<Recipe> recipes = WebScraper.load();

        // Tri dans un dictionnaire
        mappedRecipe = new RecipeMap(recipes);
        //System.out.println(mappedRecipe);
    }
}
