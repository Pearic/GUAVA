package model;

public class SeasoningStoredIn {
    private final String seasoningName;
    private final int storageID;

    public SeasoningStoredIn (String seasoningName, int storageID) {
        this.seasoningName = seasoningName;
        this.storageID = storageID;
    }

    public String getFoodName() {
        return seasoningName;
    }

    public int getStorageID() {
        return storageID;
    }
}
