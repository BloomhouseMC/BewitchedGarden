package com.bloomhousemc.bewitchedgarden.common.world;

import com.bloomhousemc.bewitchedgarden.BewitchedGarden;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Pair;
import net.minecraft.world.PersistentState;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BGWorldState extends PersistentState {
    public final List<Pair<UUID, NbtCompound>> effigies = new ArrayList<>();

    public static BGWorldState readNbt(NbtCompound nbt) {
        BGWorldState bgWorldState = new BGWorldState();
        NbtList effigiesList = nbt.getList("Familiars", NbtType.COMPOUND);
        for (int i = 0; i < effigiesList.size(); i++) {
            NbtCompound effigiesCompound = effigiesList.getCompound(i);
            bgWorldState.effigies.add(new Pair<>(effigiesCompound.getUuid("Player"), effigiesCompound.getCompound("Effigy")));
        }
        return bgWorldState;

    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtList effigiesList = new NbtList();
        for (Pair<UUID, NbtCompound> pair : this.effigies) {
            NbtCompound effigyCompound = new NbtCompound();
            effigyCompound.putUuid("Player", pair.getLeft());
            effigyCompound.put("Effigies", pair.getRight());
            effigiesList.add(effigyCompound);
        }
        nbt.put("Effigies", effigiesList);
        return nbt;
    }
    @SuppressWarnings("ConstantConditions")
    public static BGWorldState get(World world) {
        return world.getServer().getOverworld().getPersistentStateManager().getOrCreate(BGWorldState::readNbt, BGWorldState::new, BewitchedGarden.MODID);
    }
}
