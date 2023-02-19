package net.jackchang.toastymod.client;

import net.jackchang.toastymod.custom_attributes.PlayerData;

public class ClientPlayerData {
    private static PlayerData playerData;

    public static void set(PlayerData playerData) {
        ClientPlayerData.playerData = playerData;
    }

    public static PlayerData getPlayerData() {
        return playerData;
    }
}
