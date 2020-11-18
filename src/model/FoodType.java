package model;

public class FoodType {
    private final String foodName;
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbohydrate;
    private final int protein;
    private final int vitamin;
    private final String datePurchased;
    private final String expiryDate;

    public FoodType (String foodName, int calories, int fat, int sodium, int carbohydrate, int protein, int vitamin, String datePurchased, String expiryDate) {
        this.foodName = foodName;
        this.calories = calories;
        this.fat = fat;
        this.sodium = sodium;
        this.carbohydrate = carbohydrate;
        this.protein = protein;
        this.vitamin = vitamin;
        this.datePurchased = datePurchased;
        this.expiryDate = expiryDate;
    }

    public String getFoodName() {
        return foodName;
    }

    public int getCalories() {
        return calories;
    }

    public int getFat() {
        return fat;
    }

    public int getSodium() {
        return sodium;
    }

    public int getCarbohydrate() {
        return carbohydrate;
    }

    public int getProtein() {
        return protein;
    }

    public int getVitamin() {
        return vitamin;
    }

    public String getDatePurchased() {
        return datePurchased;
    }

    public String getExpiryDate() {
        return expiryDate;
    }
}
