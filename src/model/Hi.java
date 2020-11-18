package model;

public class Hi {
    private final int id;
    private final String first;
    private final String last;

    public Hi(int id, String first, String last) {
        this.id = id;
        this.first = first;
        this.last = last;
    }

    public int getId() {
        return id;
    }

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }
}
