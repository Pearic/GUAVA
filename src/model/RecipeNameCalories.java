package model;

public class RecipeNameCalories {
    private String recipeName;
    private int calories;

    public RecipeNameCalories(String recipeName, int calories) {
        this.recipeName = recipeName;
        this.calories = calories;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public int getCalories() {
        return calories;
    }
}
