package com.justnoobie.fakeutility.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LootdropScreen extends Screen {
    private TextFieldWidget originalField, disguiseField;
    private String status = "Enter registry IDs, e.g. minecraft:stone";
    private int scroll = 0;
    private final List<String> entries = new ArrayList<>();
    public LootdropScreen() { super(Text.literal("Fake Utility")); }

    @Override protected void init() {
        int left = this.width / 2 - 150;
        originalField = new TextFieldWidget(textRenderer, left, 55, 300, 20, Text.literal("Original ID"));
        originalField.setPlaceholder(Text.literal("minecraft:stone"));
        disguiseField = new TextFieldWidget(textRenderer, left, 83, 300, 20, Text.literal("Disguise ID"));
        disguiseField.setPlaceholder(Text.literal("minecraft:diamond_block"));
        addDrawableChild(originalField); addDrawableChild(disguiseField);
        addDrawableChild(ButtonWidget.builder(Text.literal("Add Fake Lootdrop"), b -> addMapping()).dimensions(left, 111, 145, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Remove Original ID"), b -> removeMapping()).dimensions(left + 155, 111, 145, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Close"), b -> close()).dimensions(left + 105, this.height - 32, 90, 20).build());
        refreshEntries();
    }
    private void addMapping() {
        try { DisguiseConfig.add(originalField.getText(), disguiseField.getText()); status = "Mapping added and saved."; refreshEntries(); }
        catch (Exception e) { status = e.getMessage(); }
    }
    private void removeMapping() {
        String id = originalField.getText().trim();
        if (DisguiseConfig.all().containsKey(id)) { DisguiseConfig.remove(id); status = "Mapping removed."; refreshEntries(); }
        else status = "No mapping found for that original ID.";
    }
    private void refreshEntries() { entries.clear(); for (Map.Entry<String,String> e : DisguiseConfig.all().entrySet()) entries.add(e.getKey() + "  ->  " + e.getValue()); }
    @Override public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        int left = this.width / 2 - 160;
        context.fill(left, 12, left + 320, this.height - 42, 0xE91B1B1B);
        context.drawCenteredTextWithShadow(textRenderer, "FAKE LOOTDROP", this.width / 2, 22, 0xFFFFFFFF);
        context.drawTextWithShadow(textRenderer, "Original block / item ID", this.width / 2 - 150, 43, 0xFFBDBDBD);
        context.drawTextWithShadow(textRenderer, "Disguise block / item ID", this.width / 2 - 150, 71, 0xFFBDBDBD);
        super.render(context, mouseX, mouseY, delta);
        context.drawTextWithShadow(textRenderer, status, this.width / 2 - 150, 139, 0xFFFFD36A);
        context.drawTextWithShadow(textRenderer, "ACTIVE MAPPINGS", this.width / 2 - 150, 158, 0xFFFFFFFF);
        int y = 174;
        for (int i = scroll; i < Math.min(entries.size(), scroll + 9); i++) { context.drawTextWithShadow(textRenderer, entries.get(i), this.width / 2 - 150, y, 0xFFE0E0E0); y += 13; }
        context.drawTextWithShadow(textRenderer, "Use IDs like minecraft:purpur_block", this.width / 2 - 150, this.height - 52, 0xFF999999);
    }
    @Override public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) { scroll = Math.max(0, Math.min(Math.max(0, entries.size() - 9), scroll + (verticalAmount < 0 ? 1 : -1))); return true; }
    @Override public boolean shouldPause() { return false; }
}
