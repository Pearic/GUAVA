package model;

public class StoredIn {
    private final int storageID;
    private final String foodName;
    private final String seasoningName;

    public StoredIn (int storageID, String foodName, String seasoningName) {
        this.storageID = storageID;
        this.foodName = foodName;
        this.seasoningName = seasoningName;
    }

    public String getFoodName() {
        return foodName;
    }

    public int getStorageID() {
        return storageID;
    }

    public String getSeasoningName() {
        return seasoningName;
    }

}
