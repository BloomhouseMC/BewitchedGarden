package com.bloomhousemc.bewitchedgarden.common.util;

import com.bloomhousemc.bewitchedgarden.common.world.BGWorldState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

public class BGUtils {
    public static EntityType<?> getEffigy(PlayerEntity player) {
        if (!player.world.isClient) {
            BGWorldState bgWorldState = BGWorldState.get(player.world);
            for (Pair<UUID, NbtCompound> pair : bgWorldState.effigies) {
                if (player.getUuid().equals(pair.getLeft())) {
                    return Registry.ENTITY_TYPE.get(new Identifier(pair.getRight().getString("id")));
                }
            }
        }
        return null;
    }
}
