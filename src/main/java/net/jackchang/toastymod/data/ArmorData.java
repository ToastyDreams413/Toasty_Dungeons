package net.jackchang.toastymod.data;


import java.util.Map;

public class ArmorData {

    public static final Map<String, Integer> ARMOR_DEFENSE = Map.ofEntries(
            Map.entry("air", 0),

            Map.entry("starter_helmet", 1),
            Map.entry("starter_chestplate", 3),
            Map.entry("starter_chausses", 2),
            Map.entry("starter_sabaton", 1),

            Map.entry("helmet_of_dread", 15),
            Map.entry("chestplate_of_dread", 20),
            Map.entry("chausses_of_dread", 18),
            Map.entry("sabaton_of_dread", 14)
    );

    public static final Map<String, Integer> ARMOR_ATTACK = Map.ofEntries(
            Map.entry("air", 0),

            Map.entry("starter_helmet", 0),
            Map.entry("starter_chestplate", 0),
            Map.entry("starter_chausses", 0),
            Map.entry("starter_sabaton", 0),

            Map.entry("helmet_of_dread", 22),
            Map.entry("chestplate_of_dread", 30),
            Map.entry("chausses_of_dread", 28),
            Map.entry("sabaton_of_dread", 20)
    );

}
