package com.justnoobie.fakeutility.mixin;

import com.justnoobie.fakeutility.client.DisguiseConfig;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.block.BlockRenderManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BlockRenderManager.class)
public class BlockRenderManagerMixin {
    @ModifyVariable(method = "getModel", at = @At("HEAD"), argsOnly = true)
    private BlockState fakeutility$disguiseBlock(BlockState original) { return DisguiseConfig.mapBlock(original.getBlock()).getDefaultState(); }
}
