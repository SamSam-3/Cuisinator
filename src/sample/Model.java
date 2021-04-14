package sample;

import java.util.ArrayList;

public class Model {

    private RecipeMap recipes;

    public Model() {
        recipes = (RecipeMap)DataManager.load("recipes");

        if (recipes == null) {
            throw new RuntimeException("Data base not found");
        }
    }

    public void search(String input, ArrayList<String> ingredients) {
        ArrayList<String> names = new ArrayList<String>();
        // TODO: search
        // view.showIngredients()
    }
}
