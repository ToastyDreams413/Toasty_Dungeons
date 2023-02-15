package net.jackchang.toastymod.client;

public class ClientShillingsData {
    private static int playerShillings;

    public static void set(int shillings) {
        ClientShillingsData.playerShillings = shillings;
    }

    public static int getPlayerShillings() {
        return playerShillings;
    }
}
