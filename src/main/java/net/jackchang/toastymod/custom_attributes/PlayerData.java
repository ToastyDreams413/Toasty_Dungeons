package net.jackchang.toastymod.custom_attributes;

import net.minecraft.nbt.CompoundTag;

import java.util.UUID;
import java.util.ArrayList;

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
    private ArrayList<UUID> partyMembers;
    private ArrayList<UUID> partyInvitesSent;
    private String selectedChat;

    // attack, defense, health, mana, speed, evasion, luck of the soul
    private int atk, def, hp, mp, spd, eva, luckOfTheSoul;
    private int meleeDmgBuff, magicDmgBuff;

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
        partyMembers = new ArrayList<UUID>();
        partyInvitesSent = new ArrayList<UUID>();

        selectedChat = "all";

        atk = 0;
        def = 0;
        hp = 0;
        mp = 0;
        spd = 0;
        eva  = 0;
        luckOfTheSoul = 0;
    }

    public void setShillings(int shillings) { this.shillings = shillings; }

    public void setGorbs(int gorbs) { this.gorbs = gorbs; }

    public void setRank(int rank) { this.rank = rank; }

    public void setSelectedClassIndex(int selectedClassIndex) { this.selectedClassIndex = selectedClassIndex; }

    public void setPlayerLevel(int playerLevel) { this.playerLevel = playerLevel; }

    public void setPlayerXP(int playerXP) { this.playerXP = playerXP; }

    public void setSelectedChat(String selectedChat) { this.selectedChat = selectedChat; }

    // party functions:
    public void setInParty(boolean inParty) { this.inParty = inParty; }

    public void setPartyLeader(UUID partyLeaderUUID) { this.partyLeader = partyLeaderUUID; }

    public void setPartyMembers(ArrayList<UUID> partyMembers) {this.partyMembers = partyMembers; }

    public void addPartyMember(UUID newPartyMemberUUID) { this.partyMembers.add(newPartyMemberUUID);  }

    public void addAllPartyMembers(ArrayList<UUID> newPartyMembersUUIDs) { this.partyMembers.addAll(newPartyMembersUUIDs);  }

    public void removePartyMember(UUID partyMemberToRemoveUUID) { this.partyMembers.remove(partyMemberToRemoveUUID); }

    public void removeAllPartyMembers() { this.partyMembers.clear(); } 

    public void setPartyInvitesSent(ArrayList<UUID> partyRequestsSent) {this.partyInvitesSent = partyRequestsSent; }

    public void addPartyInviteSent(UUID newPartyInviteSentUUID) { this.partyInvitesSent.add(newPartyInviteSentUUID);  }

    public void removePartyInviteSent(UUID partyInviteSentToRemoveUUID) { this.partyInvitesSent.remove(partyInviteSentToRemoveUUID); }

    public void removeAllPartyInvitesSent() { this.partyInvitesSent.clear(); } 


    public int getShillings() {
        return shillings;
    }

    public int getGorbs() { return gorbs; }

    public int getRank() { return rank; }

    public int getSelectedClassIndex() { return selectedClassIndex; }

    public int getPlayerLevel() { return playerLevel; }

    public int getPlayerXP() { return playerXP; }

    public int getAtk() { return atk; }

    public int getDef() { return def; }

    public String getSelectedChat() { return selectedChat; }

    // party functions:
    public boolean getInParty() { return inParty; }

    public UUID getPartyLeader() { return partyLeader; }

    public ArrayList<UUID> getPartyMembers() { return partyMembers; }

    public ArrayList<UUID> getPartyInvitesSent() { return partyInvitesSent; }


    public void increaseShillings() { shillings++; }

    public void increaseShillings(int amount) { shillings += amount; }

    public void increaseGorbs(int amount) { gorbs += amount; }

    public void increaseRank() { rank++; }

    public void increaseRank(int amount) { rank += amount; }

    public void increasePlayerXP() { playerXP++; }

    public void increasePlayerXP(int amount) { playerXP += amount; }

    public void increasePlayerLevel() { playerLevel++; }

    public void increasePlayerLevel(int amount) { playerLevel += amount; }


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
