package common;

import common.content.SpaceMarine;

import java.io.Serializable;
import java.util.TreeMap;

public class Reply implements Serializable {
    private final TreeMap<Integer, SpaceMarine> collection;
    private final boolean successful;
    private final String message;

    public Reply(TreeMap<Integer, SpaceMarine> collection, boolean successful, String message) {
        this.collection = collection;
        this.successful = successful;
        this.message = message;
    }

    public TreeMap<Integer, SpaceMarine> getCollection() {
        return collection;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public String getMessage() {
        return message;
    }
}
