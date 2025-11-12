package dev.fluffyworld.fluffyCustomDrops.reward;

public class Reward {

    private final String command;
    private final double chance;

    public Reward(String command, double chance) {
        this.command = command;
        this.chance = Math.max(0.0, Math.min(1.0, chance));
    }

    public String getCommand() {
        return command;
    }

    public double getChance() {
        return chance;
    }

    public boolean shouldDrop() {
        return Math.random() <= chance;
    }
}
