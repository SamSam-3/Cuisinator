package sample;

import java.util.HashMap;
import java.util.ArrayList;

public class Model {

    public Model() {
        // TODO: Verifier que les recettes ne sont pas deja existante sur la machine

        // Creation des differentes recettes
        Recipe[] recipes = {
                new Recipe("Ratatouille", "dinner", new String[]{"oignon", "carotte"}),
                new Recipe("Mousse au chocolat", "dessert", new String[]{"crème", "chocolat"}),
                new Recipe("Crème brulée", "dessert", new String[]{"crème"})
        };

        // Tri dans un dictionnaire
        HashMap<String, HashMap<String, ArrayList<Recipe>>> sortedRecipe = sortRecipe(recipes);
        printSortedRecipe(sortedRecipe);
    }

    HashMap<String, HashMap<String, ArrayList<Recipe>>> sortRecipe(Recipe[] recipes) {
        HashMap<String, HashMap<String, ArrayList<Recipe>>> categorizedRecipes = new HashMap<String, HashMap<String, ArrayList<Recipe>>>();    // recette categorisé

        for (Recipe r : recipes) {
            HashMap<String, ArrayList<Recipe>> ingredientMap = categorizedRecipes.get(r.category);
            if (ingredientMap == null) {
                ingredientMap = new HashMap<String, ArrayList<Recipe>>();
                categorizedRecipes.put(r.category, ingredientMap);
            }
            for (String ingredient : r.ingredients) {
                ArrayList<Recipe> recipesByIngredient = ingredientMap.get(ingredient);
                if (recipesByIngredient == null) {
                    recipesByIngredient = new ArrayList<Recipe>();
                    ingredientMap.put(ingredient, recipesByIngredient);
                }
                recipesByIngredient.add(r);
            }
        }
        return categorizedRecipes;
    }

    void printSortedRecipe(HashMap<String, HashMap<String, ArrayList<Recipe>>> recipes) {
        recipes.forEach((key, value) -> System.out.println(key + ":" + value));
    }
}
