package model;

public class CountFoodStorage {
    private final int storageID;
    private final int count;

    public CountFoodStorage (int storageID, int count) {
        this.storageID = storageID;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public int getStorageID() {
        return storageID;
    }
}
