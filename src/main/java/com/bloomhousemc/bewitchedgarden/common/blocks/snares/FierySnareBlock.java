package com.bloomhousemc.bewitchedgarden.common.blocks.snares;

import com.bloomhousemc.bewitchedgarden.common.util.SnareDamageSource;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmokingRecipe;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Optional;

public class FierySnareBlock extends SnareBlock {

    public FierySnareBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity user) {
            if (!world.isClient) {
                entity.slowMovement(state, new Vec3d(0.800000011920929D, 0.75D, 0.800000011920929D));
                user.setOnFireFor(5);
                world.setBlockState(pos, (BlockState)state.with(CLOSED, true), 3);
                world.getBlockTickScheduler().schedule(pos, this, 30);
            }
        }
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        Optional<SmokingRecipe> recipe = world.getRecipeManager().listAllOfType(RecipeType.SMOKING).stream().filter((smeltingRecipe -> {
            return smeltingRecipe.getIngredients().get(0).test(stack);
        })).findFirst();
        if (recipe.isPresent()) {
            ItemStack output = recipe.get().getOutput();
            output.setCount(stack.getCount());
            stack.decrement(stack.getCount());
            ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getX(), output);
            world.playSound(pos.getX() + world.random.nextDouble(), pos.getY() + 1.0D, pos.getZ() + world.random.nextDouble(), SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 0.2F + world.random.nextFloat() * 0.2F, 0.9F + world.random.nextFloat() * 0.15F, false);
            world.spawnEntity(itemEntity);
            return ActionResult.CONSUME;
        }
        return ActionResult.PASS;
    }
}
