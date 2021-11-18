package com.bloomhousemc.bewitchedgarden.common.network;

import com.bloomhousemc.bewitchedgarden.BewitchedGarden;
import com.bloomhousemc.bewitchedgarden.common.blocks.BackpackBlock;
import com.bloomhousemc.bewitchedgarden.common.blocks.blockentity.BackpackBlockEntity;
import com.bloomhousemc.bewitchedgarden.common.items.BackpackItem;
import com.bloomhousemc.bewitchedgarden.common.util.BGUtils;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EquipBackpackPacket {
    public static final Identifier ID = new Identifier(BewitchedGarden.MODID, "equip_backpack");

    public static void send(boolean isBlockEntity, BlockPos pos) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeBlockPos(pos);
        buf.writeBoolean(isBlockEntity);
        ClientPlayNetworking.send(ID, buf);
    }

    public static void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler network, PacketByteBuf buf, PacketSender sender) {
        BlockPos pos = buf.readBlockPos();
        boolean isBlockEntity = buf.readBoolean();

        server.execute(() -> {
            World world = player.world;

            if(isBlockEntity) {
                if(world.getBlockEntity(pos) instanceof BackpackBlockEntity blockEntity) {
                    ItemStack stack = new ItemStack(world.getBlockState(pos).getBlock().asItem());
                    NbtCompound tag = stack.getOrCreateNbt();

                    Inventories.writeNbt(tag, blockEntity.inventory);
                    blockEntity.wasPickedUp = true;
                    player.equipStack(EquipmentSlot.CHEST, stack);
                    world.breakBlock(pos, false, player);
                    player.closeHandledScreen();
                }
            }
            else {
                BlockPos blockPos = pos.offset(player.getHorizontalFacing());

                if(BGUtils.isReplaceable(world, blockPos)) {
                    ItemStack stack = player.getEquippedStack(EquipmentSlot.CHEST);

                    world.playSound(null, blockPos, SoundEvents.BLOCK_WOOL_PLACE, SoundCategory.BLOCKS, 1F, 1F);
                    world.setBlockState(blockPos, ((BackpackItem) stack.getItem()).getBlock().getDefaultState().with(BackpackBlock.FACING, player.getHorizontalFacing()));

                    if(world.getBlockEntity(blockPos) instanceof BackpackBlockEntity backpack)
                        Inventories.readNbt(stack.getOrCreateNbt(), backpack.inventory);

                    player.getEquippedStack(EquipmentSlot.CHEST).decrement(1);
                    player.closeHandledScreen();
                }
            }
        });
    }
}