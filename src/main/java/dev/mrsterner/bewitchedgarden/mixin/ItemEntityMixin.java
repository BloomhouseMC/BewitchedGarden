package dev.mrsterner.bewitchedgarden.mixin;

import dev.mrsterner.bewitchedgarden.common.registry.BGObjects;
import moriyashiine.bewitchment.common.registry.BWObjects;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {

    @Shadow public abstract ItemStack getStack();

    @Inject(method = "tick", at = @At("TAIL"))
    private void snareCraftingTick(CallbackInfo ci) {
        ItemEntity entity = (ItemEntity) (Object) this;
        if (this.getStack().isOf(BGObjects.ENDER_VIAL)) {
            if (entity.isTouchingWater())  {
                BlockPos waterPos = entity.getBlockPos();
                BlockPos waterPos1 = waterPos.up();
                BlockPos north = waterPos1.north();
                BlockPos south = waterPos1.south();
                BlockPos east = waterPos1.east();
                BlockPos west = waterPos1.west();
                if (entity.world.getBlockState(north).isOf(BWObjects.ENDER_BRAMBLE) && entity.world.getBlockState(south).isOf(BWObjects.ENDER_BRAMBLE) && entity.world.getBlockState(east).isOf(BWObjects.ENDER_BRAMBLE) && entity.world.getBlockState(west).isOf(BWObjects.ENDER_BRAMBLE)) {
                    entity.world.setBlockState(waterPos, Blocks.AIR.getDefaultState());
                    entity.world.setBlockState(north, BGObjects.TELEPORTATION_SNARE.getDefaultState());
                    entity.world.setBlockState(south, BGObjects.TELEPORTATION_SNARE.getDefaultState());
                    entity.world.setBlockState(east, BGObjects.TELEPORTATION_SNARE.getDefaultState());
                    entity.world.setBlockState(west, BGObjects.TELEPORTATION_SNARE.getDefaultState());
                    entity.world.addParticle(ParticleTypes.LARGE_SMOKE, (double)waterPos.getX() + 0.5D, (double)waterPos.getY() + 0.5D, (double)waterPos.getZ() + 0.5D, (double)(entity.world.random.nextFloat() / 2.0F), 5.0E-5D, (double)(entity.world.random.nextFloat() / 2.0F));
                    entity.discard();
                }
            }
        }
    }
}
