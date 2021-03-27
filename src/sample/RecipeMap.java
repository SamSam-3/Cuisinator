package sample;

import java.util.HashMap;
import java.util.ArrayList;

public class RecipeMap extends HashMap<String, HashMap<String, ArrayList<Recipe>>> {
    private static final long serialVersionUID = 1L;
    
    public RecipeMap(ArrayList<Recipe> recipes) { 

        for (Recipe r : recipes) {

            // Récupère le dictionnaire par categorie
            HashMap<String, ArrayList<Recipe>> ingredientMap = this.get(r.getCategory()); 
            // Ou en fait un nouveau s'il n'éxiste pas déjà pour cette catégorie
            if (ingredientMap == null) { 
                ingredientMap = new HashMap<String, ArrayList<Recipe>>();
                this.put(r.getCategory(), ingredientMap);
            }

            for (String ingredient : r.getIngredients()) {
            
                // Même chose pour le dictionnaire par ingrédients 
                ArrayList<Recipe> recipesByIngredient = ingredientMap.get(ingredient); 
                if (recipesByIngredient == null) {
                    recipesByIngredient = new ArrayList<Recipe>();
                    ingredientMap.put(ingredient, recipesByIngredient);
                }
                
                // Ajout des réferences de la recette dans le dictionnaire.
                recipesByIngredient.add(r);
            }
        }
    }

    
    @Override
    public String toString() {
        String str = "";
        for (var entry : this.entrySet()) {
            str += entry.getKey() + ":" + "\n    " + entry.getValue() + "\n";
        }
        return str;
    }
}
