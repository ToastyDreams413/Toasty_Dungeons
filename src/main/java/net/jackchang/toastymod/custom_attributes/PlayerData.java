package net.jackchang.toastymod.custom_attributes;

import net.jackchang.toastymod.Player;
import net.minecraft.nbt.CompoundTag;

public class PlayerData {

    private int shillings, rank, selectedClassIndex, totalClasses, maxClasses, playerXP, playerLevel;

    private PlayerClass[] playerClasses;

    public PlayerData() {
        shillings = 0;
        rank = 1;
        selectedClassIndex = 0;
        totalClasses = 0;
        maxClasses = 5;
        playerXP = 0;
        playerLevel = 0;
    }

    public PlayerData(int shillings, int rank, int selectedClassIndex, int totalClasses, int maxClasses, int playerXP, int playerLevel) {
        this.shillings = shillings;
        this.rank = rank;
        this.selectedClassIndex = selectedClassIndex;
        this.totalClasses = totalClasses;
        this.maxClasses = maxClasses;
        this.playerXP = playerXP;
        this.playerLevel = playerLevel;
    }

    public void setShillings(int shillings) { this.shillings = shillings; }

    public void setRank(int rank) { this.rank = rank; }

    public void setSelectedClassIndex(int selectedClassIndex) { this.selectedClassIndex = selectedClassIndex; }

    public void setTotalClasses(int totalClasses) { this.totalClasses = totalClasses; }

    public void setMaxClasses(int maxClasses) { this.maxClasses = maxClasses; }

    public void setPlayerLevel(int playerLevel) { this.playerLevel = playerLevel; }

    public void setPlayerXP(int playerXP) { this.playerXP = playerXP; }

    public int getShillings() {
        return shillings;
    }

    public int getRank() { return rank; }

    public int getSelectedClassIndex() { return selectedClassIndex; }

    public int getTotalClasses() { return totalClasses; }

    public int getMaxClasses() { return maxClasses; }

    public int getPlayerLevel() { return playerLevel; }

    public int getPlayerXP() { return playerXP; }

    public PlayerClass getSelectedClass() { return playerClasses[selectedClassIndex]; }

    public void increaseShillings() { shillings++; }

    public void increaseShillings(int amount) { shillings += amount; }

    public void increaseRank() { rank++; }

    public void increaseRank(int amount) { rank += amount; }

    public void increaseMaxClasses() { maxClasses++; }

    public void increaseMaxClasses(int amount) { maxClasses += amount; }

    public void increasePlayerXP() { playerXP++; }

    public void increasePlayerXP(int amount) { playerXP += amount; }

    public void increasePlayerLevel() { playerLevel++; }

    public void increasePlayerLevel(int amount) { playerLevel += amount; }

    public boolean hasClass(String className) {
        for (PlayerClass playerClass : playerClasses) {
            if (playerClass.getClassName().equals(className)) {
                return true;
            }
        }
        return false;
    }

    public boolean canCreateClass() {
        return totalClasses < maxClasses;
    }

    public void increaseClassArraySize() {
        PlayerClass[] newClassArray = new PlayerClass[totalClasses];
        for (int i = 0; i < totalClasses; i++) {
            newClassArray[i] = playerClasses[i];
        }
        playerClasses = new PlayerClass[totalClasses + 1];
        for (int i = 0; i < totalClasses; i++) {
            playerClasses[i] = newClassArray[i];
        }
    }

    public void createClass(String className) {
        totalClasses++;
        increaseClassArraySize();
        playerClasses[totalClasses - 1] = new PlayerClass(className);
    }

    public void selectClass(String className) {
        for (int i = 0; i < totalClasses; i++) {
            if (playerClasses[i].getClassName().equals(className)) {
                selectedClassIndex = i;
            }
        }
    }

    public void copyFrom(PlayerData source) {
        this.shillings = source.shillings;
        this.rank = source.rank;
        this.selectedClassIndex = source.selectedClassIndex;
        this.totalClasses = source.totalClasses;
        this.playerClasses = new PlayerClass[source.totalClasses];
        for (int i = 0; i < source.totalClasses; i++) {
            this.playerClasses[i] = source.playerClasses[i];
        }
        this.maxClasses = source.maxClasses;
        this.playerXP = source.playerXP;
        this.playerLevel = source.playerLevel;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("shillings", shillings);
        nbt.putInt("rank", rank);
        nbt.putInt("selectedClassIndex", selectedClassIndex);
        nbt.putInt("totalClasses", totalClasses);
        nbt.putInt("maxClasses", maxClasses);
        nbt.putInt("playerXP", playerXP);
        nbt.putInt("playerLevel", playerLevel);
    }

    public void loadNBTData(CompoundTag nbt) {
        shillings = nbt.getInt("shillings");
        rank = nbt.getInt("rank");
        selectedClassIndex = nbt.getInt("selectedClassIndex");
        totalClasses = nbt.getInt("totalClasses");
        maxClasses = nbt.getInt("maxClasses");
        playerXP = nbt.getInt("playerXP");
        playerLevel = nbt.getInt("playerLevel");
    }

}
