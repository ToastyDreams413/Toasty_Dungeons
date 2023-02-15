package net.jackchang.toastymod.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {

    public static final String KEY_CATEGORY_TOASTY = "key.category.toastymod.toasty";
    public static final String KEY_SPAWN_COW = "key.toastymod.spawn_cow";
    public static final String KEY_GAIN_SHILLING = "key.toastymod.gain_shilling";

    public static final KeyMapping SPAWN_COW_KEY = new KeyMapping(KEY_SPAWN_COW, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_M, KEY_CATEGORY_TOASTY);
    public static final KeyMapping GAIN_SHILLING_KEY = new KeyMapping(KEY_GAIN_SHILLING, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_N, KEY_CATEGORY_TOASTY);

}
