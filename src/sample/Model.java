package sample;

import java.util.*;

public class Model {

    private Controller control;
    private Set<String> categories;
    private Set<String> ingredients;

    public ArrayList<Recipe> recipeList; //J'ai du le passer en public dans le main (va voir si ca te dérange sinon supprime le commentaire)
    private RecipeMap recipeMap;
    private ArrayList<Recipe> favList;
    private ArrayList<Recipe> likeList;

    public Model(Controller control) {
        this.control = control;
        this.loadData();
        this.categories = recipeMap.getCategories();
        this.ingredients = recipeMap.getIngredients();
    }

    @SuppressWarnings("unchecked")
    public void loadData() {
        recipeMap = (RecipeMap)DataManager.load("recipe-map");
        recipeList = (ArrayList<Recipe>)DataManager.load("recipe-list");
        favList = (ArrayList<Recipe>)DataManager.load("fav-list");
        likeList = (ArrayList<Recipe>)DataManager.load("like-list");

        if (recipeMap == null || recipeList == null) {
            throw new RuntimeException("Data base not found");
        }
    }
    public void saveData() {
        DataManager.save("recipe-map", recipeMap);
        DataManager.save("recipe-list", recipeList);
        DataManager.save("fav-list", favList);
        DataManager.save("like-list", likeList);
    }

    public Recipe bestMatchRecipe(String searchStr) {
        Recipe bestMatch = null;

        for (Recipe recipe : this.recipeList) {
            if (recipe.getName().toLowerCase().startsWith(searchStr)) {
                if (bestMatch != null && bestMatch.getLikes() < recipe.getLikes()) {
                    bestMatch = recipe;
                } 
            } 
        }

        return bestMatch;
    }

    public String bestMatchIngred(String searchString) {
        String bestMatch = null;
        for (String ing : this.ingredients) {
            if (ing.toLowerCase().startsWith(searchString)) {
                bestMatch = searchString;
            }
        }    
        return bestMatch;
    }

    public Set<Recipe> search(String searchStr, Set<String> categsFilter, Set<String> ingredsFilter) {
        Set<Recipe> output = new HashSet<Recipe>();

        if (categsFilter == null || categsFilter.size() == 0) {    
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
    
    public void toggleLike(Recipe recipe) {
        if (! likeList.contains(recipe)) {
            recipe.addLikes(1);
            likeList.add(recipe);
        } else {
            recipe.addLikes(-1);
            likeList.remove(recipe);
        }
    }
    public void toggleFav(Recipe recipe) {
        if (! favList.contains(recipe)) {
            favList.add(recipe);
        } else {
            favList.remove(recipe);
        }
    }
}
