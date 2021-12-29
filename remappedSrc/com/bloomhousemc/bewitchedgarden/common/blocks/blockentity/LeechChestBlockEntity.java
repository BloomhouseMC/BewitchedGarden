package com.bloomhousemc.bewitchedgarden.common.blocks.blockentity;

import com.bloomhousemc.bewitchedgarden.common.registry.BGObjects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.*;
import net.minecraft.client.block.ChestAnimationProgress;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class LeechChestBlockEntity extends ChestBlockEntity implements ChestAnimationProgress {
    private DefaultedList<ItemStack> inventory;
    private final ViewerCountManager stateManager;
    private final ChestLidAnimator lidAnimator;

    public LeechChestBlockEntity(BlockPos pos, BlockState state) {
        this(BGObjects.LEECH_CHEST_BLOCK_ENTITY, pos, state);
    }

    public LeechChestBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
        super(blockEntityType, pos, state);
        this.inventory = DefaultedList.ofSize(27, ItemStack.EMPTY);
        this.stateManager = new ViewerCountManager() {
            protected void onContainerOpen(World world, BlockPos pos, BlockState state) {
                world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }

            protected void onContainerClose(World world, BlockPos pos, BlockState state) {
                world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }

            protected void onViewerCountUpdate(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
                LeechChestBlockEntity.this.onInvOpenOrClose(world, pos, state, oldViewerCount, newViewerCount);
            }

            protected boolean isPlayerViewing(PlayerEntity player) {
                if (!(player.currentScreenHandler instanceof GenericContainerScreenHandler)) {
                    return false;
                } else {
                    Inventory inventory = ((GenericContainerScreenHandler)player.currentScreenHandler).getInventory();
                    return inventory == LeechChestBlockEntity.this;
                }
            }
        };
        this.lidAnimator = new ChestLidAnimator();
    }

    public static void clientTick(World world, BlockPos pos, BlockState state, LeechChestBlockEntity blockEntity) {
        blockEntity.lidAnimator.step();
    }

    public int size() {
        return 27;
    }

    protected Text getContainerName() {
        return new TranslatableText("container.bewitchedgarden.leech_chest");
    }

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.readNbt(nbt, this.inventory);
    }

    public NbtCompound writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inventory);
        return nbt;
    }

    public boolean onSyncedBlockEvent(int type, int data) {
        if (type == 1) {
            this.lidAnimator.setOpen(data > 0);
            return true;
        } else {
            return super.onSyncedBlockEvent(type, data);
        }
    }

    public void onOpen(PlayerEntity player) {
        if (!this.removed && !player.isSpectator()) {
            this.stateManager.openContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
        }
    }

    public void onClose(PlayerEntity player) {
        if (!this.removed && !player.isSpectator()) {
            this.stateManager.closeContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
        }

    }

    public float getAnimationProgress(float tickDelta) {
        return this.lidAnimator.getProgress(tickDelta);
    }

    protected DefaultedList<ItemStack> getInvStackList() {
        return this.inventory;
    }

    protected void setInvStackList(DefaultedList<ItemStack> list) {
        this.inventory = list;
    }

    public static int getPlayersLookingInChestCount(BlockView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        if (blockState.hasBlockEntity()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof LeechChestBlockEntity) {
                return ((LeechChestBlockEntity)blockEntity).stateManager.getViewerCount();
            }
        }

        return 0;
    }

    public static void copyInventory(LeechChestBlockEntity from, LeechChestBlockEntity to) {
        DefaultedList<ItemStack> defaultedList = from.getInvStackList();
        from.setInvStackList(to.getInvStackList());
        to.setInvStackList(defaultedList);
    }

    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return GenericContainerScreenHandler.createGeneric9x3(syncId, playerInventory, this);
    }

    public void onScheduledTick() {
        if (!this.removed) {
            this.stateManager.updateViewerCount(this.getWorld(), this.getPos(), this.getCachedState());
        }

    }

    protected void onInvOpenOrClose(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
        Block block = state.getBlock();
        world.addSyncedBlockEvent(pos, block, 1, newViewerCount);
    }

    public boolean isEmpty() {
        return this.getInvStackList().stream().allMatch(ItemStack::isEmpty);
    }

    public ItemStack getStack(int slot) {
        return (ItemStack)this.getInvStackList().get(slot);
    }

    public ItemStack removeStack(int slot, int amount) {
        ItemStack itemStack = Inventories.splitStack(this.getInvStackList(), slot, amount);
        if (!itemStack.isEmpty()) {
            this.markDirty();
        }
        return itemStack;
    }

    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(this.getInvStackList(), slot);
    }

    public void setStack(int slot, ItemStack stack) {
        this.getInvStackList().set(slot, stack);
        if (stack.getCount() > this.getMaxCountPerStack()) {
            stack.setCount(this.getMaxCountPerStack());
        }
        this.markDirty();
    }

    public boolean canPlayerUse(PlayerEntity player) {
        if (this.world.getBlockEntity(this.pos) != this) {
            return false;
        } else {
            return !(player.squaredDistanceTo((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) > 64.0D);
        }
    }

    public void clear() {
        this.getInvStackList().clear();
    }
}
