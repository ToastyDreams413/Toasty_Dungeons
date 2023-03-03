package net.jackchang.toastymod.custom_attributes;

import net.jackchang.toastymod.Player;
import net.minecraft.nbt.CompoundTag;

import java.util.UUID;

public class PlayerData {

    private final int NUMBER_OF_KINGDOMS = 3;
    private final int NUMBER_OF_CLASSES = 3;

    private int shillings;
    private int gorbs;
    private int[] reputation;
    private int rank;
    private int selectedClassIndex;
    private int[] unlockedClasses;
    private int playerXP;
    private int playerLevel;
    private int dungeonXP;
    private int dungeonLevel;
    private int[] unlockedKingdoms;
    private boolean inParty;
    private UUID partyLeader;
    private UUID[] party;

    //private int shillings, rank, selectedClassIndex, totalClasses, maxClasses, playerXP, playerLevel;

    private PlayerClass[] playerClasses;

    public PlayerData() {
        shillings = 0;
        gorbs = 0;
        reputation = new int[NUMBER_OF_KINGDOMS];
        reputation[0] = 0;
        rank = 0;
        selectedClassIndex = 0;
        unlockedClasses = new int[NUMBER_OF_CLASSES];
        playerXP = 0;
        playerLevel = 1;
        dungeonXP = 0;
        dungeonLevel = 1;
        unlockedKingdoms = new int[NUMBER_OF_KINGDOMS];
        inParty = false;
        partyLeader = null;
        party = null;
    }

    public void setShillings(int shillings) { this.shillings = shillings; }

    public void setGorbs(int gorbs) { this.gorbs = gorbs; }

    public void setRank(int rank) { this.rank = rank; }

    public void setSelectedClassIndex(int selectedClassIndex) { this.selectedClassIndex = selectedClassIndex; }

    public void setPlayerLevel(int playerLevel) { this.playerLevel = playerLevel; }

    public void setPlayerXP(int playerXP) { this.playerXP = playerXP; }

    public int getShillings() {
        return shillings;
    }

    public int getGorbs() { return gorbs; }

    public int getRank() { return rank; }

    public int getSelectedClassIndex() { return selectedClassIndex; }

    public int getPlayerLevel() { return playerLevel; }

    public int getPlayerXP() { return playerXP; }

    public PlayerClass getSelectedClass() { return playerClasses[selectedClassIndex]; }

    public void increaseShillings() { shillings++; }

    public void increaseShillings(int amount) { shillings += amount; }

    public void increaseRank() { rank++; }

    public void increaseRank(int amount) { rank += amount; }

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



    public void copyFrom(PlayerData source) {
        /*
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
         */
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("shillings", shillings);
        nbt.putInt("rank", rank);
        nbt.putInt("selectedClassIndex", selectedClassIndex);
        nbt.putInt("playerXP", playerXP);
        nbt.putInt("playerLevel", playerLevel);
    }

    public void loadNBTData(CompoundTag nbt) {
        shillings = nbt.getInt("shillings");
        rank = nbt.getInt("rank");
        selectedClassIndex = nbt.getInt("selectedClassIndex");
        playerXP = nbt.getInt("playerXP");
        playerLevel = nbt.getInt("playerLevel");
    }

}
