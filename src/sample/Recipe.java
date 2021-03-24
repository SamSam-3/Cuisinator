package sample;

public class Recipe {
    public String category;
    String name;
    String[] ingredients;
    //String[] steps;

    public Recipe(String name, String category, String[] ingredients) {
        this.name = name;
        this.ingredients = ingredients;
        this.category = category;
        //this.steps = steps;
    }

    public String toString() {
        return this.name;
    }
}
