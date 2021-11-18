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
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PlaceBackpackPacket {
    public static final Identifier ID = new Identifier(BewitchedGarden.MODID, "place_backpack");

    public static void send(BlockHitResult hitResult) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeBlockHitResult(hitResult);
        ClientPlayNetworking.send(ID, buf);
    }

    public static void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler network, PacketByteBuf buf, PacketSender sender) {
        BlockHitResult hitResult = buf.readBlockHitResult();

        server.execute(() -> {
            World world = player.world;
            BlockPos pos = BGUtils.isReplaceable(world, hitResult.getBlockPos()) ? hitResult.getBlockPos() : hitResult.getBlockPos().offset(hitResult.getSide());
            ItemStack stack = player.getEquippedStack(EquipmentSlot.CHEST);

            if(BGUtils.isReplaceable(world, pos)) {
                world.playSound(null, pos, SoundEvents.BLOCK_WOOL_PLACE, SoundCategory.BLOCKS, 1F, 1F);
                world.setBlockState(pos, ((BackpackItem) stack.getItem()).getBlock().getDefaultState().with(BackpackBlock.FACING, player.getHorizontalFacing()));

                if(world.getBlockEntity(pos) instanceof BackpackBlockEntity backpack)
                    Inventories.readNbt(stack.getOrCreateNbt(), backpack.inventory);

                player.getEquippedStack(EquipmentSlot.CHEST).decrement(1);
            }
        });
    }
}