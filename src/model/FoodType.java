package model;

public class FoodType {
    private String foodName;
    private int calories;
    private int fat;
    private int sodium;
    private int carbohydrate;
    private int protein;
    private int vitamin;
    private String datePurchased;
    private String expiryDate;

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

    public void setCalories(int i) {
        this.calories = i;
    }

    public void setFat(int i) {
        this.fat = i;
    }

    public void setSodium(int i) {
        this.sodium = i;
    }

    public void setCarbohydrate(int i) {
        this.carbohydrate = i;
    }

    public void setProtein(int i) {
        this.protein = i;
    }

    public void setVitamin(int i) {
        this.vitamin = i;
    }

    public void setDatePurchased(String s) {
        this.datePurchased = s;
    }

    public void setExpiryDate(String s) {
        this.expiryDate = s;
    }
}
