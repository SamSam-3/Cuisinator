package sample;

import java.util.*;

public class Model {

    public RecipeMap recipeMap;
    public ArrayList<Recipe> recipeList;

    private Controller control;
    private Set<String> categories;

    public Model(Controller control) {
        this.control = control;
        this.loadData();
        this.categories = recipeMap.getCategories();

        this.control.mainPage(recipeList);
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
    public Set<Recipe> search(String searchStr, Set<String> categsFilter, Set<String> ingredsFilter) {
        if (categsFilter != null) {    
            categsFilter = this.categories; // de base on recherche sur toutes les categories
        }
        Set<Recipe> output = new HashSet<Recipe>();

        if (ingredsFilter != null) {
            for (String cat : categsFilter) {
                for (String ing : ingredsFilter) {
                    for (Recipe recipe : this.recipeMap.get(cat).get(ing)) {
                        String name = recipe.getName();
                        if (name.toLowerCase().contains(searchStr)) {
                            output.add(recipe);
                        }
                    }
                }
            }
        } else {
            for (Recipe recipe : this.recipeList) {
                String name = recipe.getName();
                if (name.toLowerCase().contains(searchStr)) {
                    output.add(recipe);
                }
            }
        }

        return output;
    }

    public void search(String input) {
        //////// Recettes \\\\\\\\
        System.out.println("############################################# Recettes #####################################################");

        for (Recipe recipe : recipeList) {
            String name = recipe.getName();
            if (name.toLowerCase().contains(input)) { // A modifier pour faire des recherches sans accents et autres caracteres spéciaux
                this.control.addRecipe(name);
                this.control.recipeDisplay.add(recipe);

                System.out.println(name); // Affiche les recettes correspondantes
            }
        }

        //////// Ingrédients \\\\\\\\
        System.out.println("############################################# Ingrédients #################################################");

        for (String ing : recipeMap.getIngredients()){
            if (ing.toLowerCase().contains(input)){
                this.control.addIngredients(ing);
                System.out.println(ing);
            }
        }

        System.out.println("############################################################################################################");
    }

    public Set<String> getCategories() {
        return categories;
    }

}
