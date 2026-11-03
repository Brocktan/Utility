package com.justnoobie.fakeutility.mixin;

import com.justnoobie.fakeutility.client.DisguiseConfig;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @ModifyVariable(method = "getModel", at = @At("HEAD"), argsOnly = true)
    private ItemStack fakeutility$disguiseItem(ItemStack original) { return DisguiseConfig.mapStack(original); }
}
