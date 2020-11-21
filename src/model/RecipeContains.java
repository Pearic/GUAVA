package model;

public class RecipeContains {
    private String recipeName;
    private String seasoningName;
    private String foodName;

    public RecipeContains(String recipeName, String seasoningName, String foodName) {
        this.recipeName = recipeName;
        this.seasoningName = seasoningName;
        this.foodName = foodName;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getSeasoningName() {
        return seasoningName;
    }

    public String getFoodName() {
        return foodName;
    }
}
