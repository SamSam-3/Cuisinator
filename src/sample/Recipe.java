package sample;

import java.io.Serializable;

public class Recipe implements Serializable{
    private static final long serialVersionUID = 1L;
    private String category;
    private String name;
    private String[] ingredients;
    //String[] steps;

    public Recipe(String name, String category, String[] ingredients) {
        this.name = name;
        this.ingredients = ingredients;
        this.category = category;
        //this.steps = steps;
    }
    
    @Override
    public String toString() {
        return this.name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }
}
