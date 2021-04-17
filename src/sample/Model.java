package sample;

import java.util.*;

public class Model {

    private Controller control;
    private Set<String> categories;
    private Set<String> ingredients;
    
    private ArrayList<Recipe> recipeList;
    private RecipeMap recipeMap;
    // private ArrayList<Recipe> favList;
    // private ArrayList<Recipe> likeList;

    public Model(Controller control) {
        this.control = control;
        this.loadData();
        this.categories = recipeMap.getCategories();
        this.ingredients = recipeMap.getIngredients();

        this.control.mainPage(recipeList);
    }

    @SuppressWarnings("unchecked")
    public void loadData() {
        recipeMap = (RecipeMap)DataManager.load("recipe-map");
        recipeList = (ArrayList<Recipe>)DataManager.load("recipe-list");
        // favList = (ArrayList<Recipe>)DataManager.load("fav-list");
        // likeList = (ArrayList<Recipe>)DataManager.load("like-list");

        if (recipeMap == null || recipeList == null) {
            throw new RuntimeException("Data base not found");
        }
    }
    public void saveData() {
        DataManager.save("recipe-map", recipeMap);
        DataManager.save("recipe-list", recipeList);
        // DataManager.save("fav-list", favList);
        // DataManager.save("like-list", likeList);
    }

    public Set<Recipe> search(String searchStr, Set<String> categsFilter, Set<String> ingredsFilter) {
        Set<Recipe> output = new HashSet<Recipe>();

        if (categsFilter != null && categsFilter.size() > 0) {    
            categsFilter = this.categories; // de base on recherche sur toutes les categories
        }
        
        if (ingredsFilter != null && ingredsFilter.size() > 0) {
            for (String cat : categsFilter) {
                var ingMap = this.recipeMap.get(cat);
                for (String ing : ingredsFilter) {
                    if (ingMap.containsKey(ing)) {
                        for (Recipe recipe : ingMap.get(ing)) {
                            if (recipe.getName().toLowerCase().contains(searchStr)) {
                                output.add(recipe);
                            }
                        }
                    }
                }
            }
        } else {
            for (Recipe recipe : this.recipeList) {
                if (recipe.getName().toLowerCase().contains(searchStr) && categsFilter.contains(recipe.getCategory())) {
                    output.add(recipe);
                }
            }
        }
        return output;
    }
    
    public Set<String> searchIngredients(String searchStr) {
        Set<String> output = new HashSet<String>();
        
        for (String ing : this.ingredients) {
            if (ing.toLowerCase().contains(searchStr)) {
                output.add(ing);
            }
        }
        return output;
    }

    public void search(String input) { // TODO: retirer cette version
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
    
    public Set<String> getIngredByCateg(Set<String> categories) {
        Set<String> ingreds = new HashSet<String>();
        
        if (categories != null && categories.size() > 0) {    
            return this.ingredients;
        }

        for (String cat : categories) {
            ingreds.addAll(this.recipeMap.get(cat).keySet());
        }
        return ingreds;
    }
    
    // public void toggleLike(Recipe recipe) {
    //     if (! likeList.contains(recipe)) {
    //         recipe.addLikes(1);
    //         likeList.add(recipe);
    //     } else {
    //         recipe.addLikes(-1);
    //         likeList.remove(recipe);
    //     }
    // }
    // public void toggleFav(Recipe recipe) {
    //     if (! favList.contains(recipe)) {
    //         favList.add(recipe);
    //     } else {
    //         favList.remove(recipe);
    //     }
    // }
}
