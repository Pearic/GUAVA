package model;

public class FoodStoredIn {
    private final String foodName;
    private final int storageID;

    public FoodStoredIn (String foodName, int storageID) {
        this.foodName = foodName;
        this.storageID = storageID;
    }

    public String getFoodName() {
        return foodName;
    }

    public int getStorageID() {
        return storageID;
    }

}
