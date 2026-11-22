package main.observer;

public record PlayerAttributeChange(
        PlayerAttribute attribute,
        int previousValue,
        int currentValue
) {

    public int difference() {
        return currentValue - previousValue;
    }

    public boolean increased() {
        return currentValue > previousValue;
    }
}
