package sample;

import java.util.ArrayList;

public class Model {

    public RecipeMap recipeMap;
    public ArrayList<Recipe> recipeList;
    private View view;

    public Model(View view) {
        this.view = view;
        this.loadData();
    }

    @SuppressWarnings("unchecked")
    public void loadData() {
        recipeMap = (RecipeMap)DataManager.load("recipeMap");
        recipeList = (ArrayList<Recipe>)DataManager.load("recipeList");

        if (recipeMap == null || recipeList == null) {
            throw new RuntimeException("Data base not found");
        }
    }

    public void search(String input, ArrayList<String> ingredients) {
        // ArrayList<String> names = new ArrayList<String>();
        // TODO: search
        // view.showIngredients()
    }
    
}
