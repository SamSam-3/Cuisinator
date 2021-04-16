package sample;

import java.io.Serializable;
import java.util.Set;

public class Recipe implements Comparable<Recipe>, Serializable{
    private static final long serialVersionUID = 1L;
    private String category;
    private String name;
    private String[] ingredients;
    private String[] requirements;
    private String image;  
    private String steps;
    private int likes;

    public Recipe(String name, String category, String[] ingredients, String[] requirements, String image, String steps) {
        this.name = name;
        this.ingredients = ingredients;
        this.category = category;
        this.requirements = requirements;
        this.image = image;
        this.steps = steps;
        this.likes = (int)Math.random()*1000;
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
    
    public boolean isContaining(Set<String> ingreds) {
        for (String ing : this.ingredients) {
            if (! ingreds.contains(ing)) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Recipe rcp) {
        return name == rcp.name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int compareTo(Recipe rcp) {
        return likes - rcp.getLikes();
    }
}
