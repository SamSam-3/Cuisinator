package sample;

import java.io.Serializable;

public class Recipe implements Comparable<Recipe>, Serializable{
    private static final long serialVersionUID = 1L;
    
    private String category;
    private String name;
    private String[] ingredients;
    private String[] requirements;
    private String image;  
    private String steps;
    private String favorite;
    private int likes;

    public Recipe(String name, String category, String[] ingredients, String[] requirements, String image, String steps) {
        this.name = name;
        this.ingredients = ingredients;
        this.category = category;
        this.requirements = requirements;
        this.image = image;
        this.steps = steps;
        this.favorite = "unfavorite";
        this.likes = (int)Math.random()*1000;

    }

    public boolean isFavorite(){
        return this.favorite.equals("favorite");
    }

    public void setFavorite(boolean b){
        if(b){
            this.favorite = "favorite";
        } else {
            this.favorite = "unfavorite";
        }
    }

    public String getFavorite(){
        return this.favorite;
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

    public String[] getRequirements() {
        return requirements;
    }

    public void setRequirements(String[] requirements) {
        this.requirements = requirements;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void addLikes(int number) {
        this.likes += number;
    }

    public boolean equals(Recipe recipe) {
        return name == recipe.name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int compareTo(Recipe recipe) {
        return likes - recipe.getLikes();
    }
}
