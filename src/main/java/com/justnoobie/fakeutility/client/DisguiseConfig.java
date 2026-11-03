package com.justnoobie.fakeutility.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public final class DisguiseConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path FILE = Path.of("config/fakeutility-lootdrop.json");
    private static final Map<String, String> MAPPINGS = new LinkedHashMap<>();

    private DisguiseConfig() {}

    public static void load() {
        MAPPINGS.clear();
        try {
            if (Files.exists(FILE)) {
                try (Reader reader = Files.newBufferedReader(FILE)) {
                    Map<?, ?> saved = GSON.fromJson(reader, Map.class);
                    if (saved != null) saved.forEach((k, v) -> {
                        if (k instanceof String key && v instanceof String value) MAPPINGS.put(key, value);
                    });
                }
            } else {
                // Requested default disguises. Original item IDs/names are not changed.
                MAPPINGS.put("minecraft:purpur_block", "minecraft:netherite_block");
                MAPPINGS.put("minecraft:terracotta", "minecraft:gilded_blackstone");
                MAPPINGS.put("minecraft:iron_chestplate", "minecraft:elytra");
                MAPPINGS.put("minecraft:tropical_fish", "minecraft:enchanted_golden_apple");
                MAPPINGS.put("minecraft:blue_concrete", "minecraft:spawner");
                save();
            }
        } catch (Exception e) { System.err.println("[FakeUtility] Could not load mappings: " + e.getMessage()); }
    }

    public static Map<String, String> all() { return MAPPINGS; }
    public static void add(String from, String to) {
        Identifier a = Identifier.tryParse(from.trim());
        Identifier b = Identifier.tryParse(to.trim());
        if (a == null || b == null) throw new IllegalArgumentException("Use valid IDs, e.g. minecraft:stone");
        if (!Registries.BLOCK.containsId(a) && !Registries.ITEM.containsId(a)) throw new IllegalArgumentException("Unknown original ID: " + a);
        if (!Registries.BLOCK.containsId(b) && !Registries.ITEM.containsId(b)) throw new IllegalArgumentException("Unknown disguise ID: " + b);
        MAPPINGS.put(a.toString(), b.toString()); save();
    }
    public static void remove(String id) { MAPPINGS.remove(id); save(); }
    public static void save() {
        try { Files.createDirectories(FILE.getParent()); try (Writer writer = Files.newBufferedWriter(FILE)) { GSON.toJson(MAPPINGS, writer); } }
        catch (Exception e) { System.err.println("[FakeUtility] Could not save mappings: " + e.getMessage()); }
    }
    public static Block mapBlock(Block original) {
        Identifier id = Registries.BLOCK.getId(original);
        String target = MAPPINGS.get(id.toString());
        if (target == null) return original;
        Identifier targetId = Identifier.tryParse(target);
        return targetId != null && Registries.BLOCK.containsId(targetId) ? Registries.BLOCK.get(targetId) : original;
    }
    public static ItemStack mapStack(ItemStack original) {
        if (original.isEmpty()) return original;
        Identifier id = Registries.ITEM.getId(original.getItem());
        String target = MAPPINGS.get(id.toString());
        if (target == null) return original;
        Identifier targetId = Identifier.tryParse(target);
        if (targetId == null || !Registries.ITEM.containsId(targetId)) return original;
        ItemStack disguised = new ItemStack(Registries.ITEM.get(targetId), original.getCount());
        return disguised;
    }
}
