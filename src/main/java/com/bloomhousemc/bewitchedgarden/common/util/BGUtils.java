package com.bloomhousemc.bewitchedgarden.common.util;

import com.bloomhousemc.bewitchedgarden.BewitchedGarden;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class BGUtils {
    public static NbtList toTag(SimpleInventory inventory) {
        NbtList tag = new NbtList();
        for(int i = 0; i < inventory.size(); i++) {
            NbtCompound stackTag = new NbtCompound();
            stackTag.putInt("Slot", i);
            stackTag.put("Stack", inventory.getStack(i).writeNbt(new NbtCompound()));
            tag.add(stackTag);
        }
        return tag;
    }
    public static void fromTag(NbtList tag, SimpleInventory inventory) {
        inventory.clear();
        tag.forEach(element -> {
            NbtCompound stackTag = (NbtCompound) element;
            int slot = stackTag.getInt("Slot");
            ItemStack stack = ItemStack.fromNbt(stackTag.getCompound("Stack"));
            inventory.setStack(slot, stack);
        });
    }

    public static class Dimension {

        private int width;
        private int height;

        public Dimension(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }
    /**
     * Type declaration for a point in 2D space
     */
    public static class Point {
        public int x;
        public int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static boolean isReplaceable(World world, BlockPos pos) {
        return world.getBlockState(pos).getMaterial().isReplaceable();
    }

    public static <T> T register(Registry<? super T> registry, String name, T entry) {
        return Registry.register(registry, BewitchedGarden.id(name), entry);
    }
}
