package main.model;

import java.util.*;

public class Flag {

    public static final String FLAG_NONE = "NONE";

    private final Set<String> activeFlags;

    public Flag() {
        this.activeFlags = new HashSet<>();
    }

    public void addFlag(String flagId) {
        activeFlags.add(flagId);
    }

    public boolean isFlagActive(String flagId) {
        return activeFlags.contains(flagId);
    }

    public Set<String> getActiveFlags() {
        return Collections.unmodifiableSet(activeFlags);
    }
}
