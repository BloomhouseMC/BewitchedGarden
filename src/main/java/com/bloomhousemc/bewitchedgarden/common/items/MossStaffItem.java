package com.bloomhousemc.bewitchedgarden.common.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class MossStaffItem extends BaseStaffItem {

    public MossStaffItem(int maxStorage, Settings settings) {
        super(maxStorage, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (this.getStoredPower() >= 10) {
            if (!world.isClient) {
                SnowballEntity snowballEntity = new SnowballEntity(world, user);
                snowballEntity.setItem(new ItemStack(Items.KELP));
                snowballEntity.setProperties(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
                world.spawnEntity(snowballEntity);
            }
            this.remove(10);
            this.check();
            return TypedActionResult.success(stack);
        }
        return TypedActionResult.pass(stack);
    }
}
