package com.bloomhousemc.bewitchedgarden.common.blocks.blockentity;

import com.bloomhousemc.bewitchedgarden.common.screen.BackpackScreenHandler;
import com.bloomhousemc.bewitchedgarden.common.registry.BGObjects;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class BackpackBlockEntity extends BlockEntity implements IAnimatable, Inventory, ExtendedScreenHandlerFactory   {
    private final AnimationFactory factory = new AnimationFactory(this);
    public final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(27, ItemStack.EMPTY);
    public boolean wasPickedUp = false;

    public BackpackBlockEntity(BlockPos pos, BlockState state) {
        super(BGObjects.BACKPACK_BLOCK_ENTITY, pos, state);
    }

    private <E extends BlockEntity & IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.backpack.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        Inventories.readNbt(tag, inventory);
        wasPickedUp = tag.getBoolean("PickedUp");
    }

    @Override
    public NbtCompound writeNbt(NbtCompound tag) {
        Inventories.writeNbt(tag, inventory);
        tag.putBoolean("PickedUp", wasPickedUp);
        return super.writeNbt(tag);
    }



    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }



    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public int size() {
        return inventory.size();
    }

    @Override
    public boolean isEmpty() {
        return inventory.isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(inventory, slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(inventory, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.set(slot, stack);
    }


    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return !(player.squaredDistanceTo(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) > 64.0D);
    }

    @Override
    public void clear() {
        inventory.clear();
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new BackpackScreenHandler(syncId, inv, this, ScreenHandlerContext.create(player.world, getPos()), getPos(), true);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(getPos());
        buf.writeBoolean(true);
    }
}
