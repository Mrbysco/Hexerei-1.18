package net.joefoxe.hexerei.config;


import net.minecraft.client.util.InputMappings;
import com.sun.java.accessibility.util.Translator;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public final class ModKeyBindings {
    public static final KeyBinding broomDescend;
    private static final String recipeCategoryName = I18n.get("hexerei.key.category.broom");

    private static final List<KeyBinding> allBindings;

    static InputMappings.Input getKey(int key) {
        return InputMappings.Type.KEYSYM.getOrCreate(key);
    }

    static {

        allBindings = List.of(
                // Overlay

                // Recipes
                broomDescend = new KeyBinding("key.hexerei.broomDescend", KeyConflictContext.IN_GAME, getKey(GLFW.GLFW_KEY_LEFT_SHIFT), recipeCategoryName)
        );

    }

    private ModKeyBindings() {
    }

    public static void init() {
        for (KeyBinding binding : allBindings) {
            ClientRegistry.registerKeyBinding(binding);
        }
    }

}

