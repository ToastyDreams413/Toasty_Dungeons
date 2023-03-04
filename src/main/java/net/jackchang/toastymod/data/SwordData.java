package net.jackchang.toastymod.data;


import net.jackchang.toastymod.item.ModCreativeModeTab;
import net.jackchang.toastymod.item.ModItems;
import net.jackchang.toastymod.item.custom.CustomSword;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;

public class SwordData {

    public static final Map<String, Integer> SWORD_DAMAGE = Map.ofEntries(
            Map.entry("starter_sword", 1),
            Map.entry("decaying_drumstick", 3),
            Map.entry("rusty_sword", 5),
            Map.entry("arachnas_fang", 10),
            Map.entry("flimsy_rapier", 12),
            Map.entry("cracked_stone_hammer", 15),
            Map.entry("cutlass_of_the_seas", 20),
            Map.entry("crystal_javelin", 35),
            Map.entry("sword_of_electrifying_power", 100)
    );

    public static final Map<String, Float> SWORD_COOLDOWN = Map.ofEntries(
            Map.entry("starter_sword", 2f),
            Map.entry("decaying_drumstick", 2f),
            Map.entry("rusty_sword", 2f),
            Map.entry("arachnas_fang", 1f),
            Map.entry("flimsy_rapier", 0.7f),
            Map.entry("cracked_stone_hammer", 3f),
            Map.entry("cutlass_of_the_seas", 0.6f),
            Map.entry("crystal_javelin", 0.8f),
            Map.entry("sword_of_electrifying_power", 0.1f)
    );

    public static float calculateAttackSpeedFromCooldown(float cooldown) {
        return 1 / cooldown;
    }

}
