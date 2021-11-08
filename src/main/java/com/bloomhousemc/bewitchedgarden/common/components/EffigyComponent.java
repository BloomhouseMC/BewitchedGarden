package com.bloomhousemc.bewitchedgarden.common.components;

import com.bloomhousemc.bewitchedgarden.common.entity.EffigyEntity;
import com.bloomhousemc.bewitchedgarden.common.registry.BGComponents;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;

import java.util.*;

public class EffigyComponent implements AutoSyncedComponent, ServerTickingComponent {
    private UUID owner = null;
    private final EffigyEntity effigyEntity;
    private boolean effigy = false;

    public EffigyComponent(EffigyEntity effigyEntity) {
        this.effigyEntity = effigyEntity;
    }

    @Override
    public void serverTick() {
        if (owner != null) {
            Entity owner = ((ServerWorld) effigyEntity.world).getEntity(getOwner());
            if(owner instanceof PlayerEntity playerEntity){
                Map<StatusEffect, StatusEffectInstance> statusEffectsEffigy = effigyEntity.getActiveStatusEffects();
                statusEffectsEffigy.forEach((statusEffect, statusEffectInstance) -> {
                    playerEntity.addStatusEffect(statusEffectInstance);
                });
            }
        }
    }


    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
        BGComponents.EFFIGY_COMPONENT.sync(effigyEntity);
    }

    public boolean hasEffigy(){
        return effigy;
    }

    public static EffigyComponent get(EffigyEntity obj) {
        return BGComponents.EFFIGY_COMPONENT.get(obj);
    }

    public static Optional<EffigyComponent> maybeGet(EffigyEntity obj) {
        return BGComponents.EFFIGY_COMPONENT.maybeGet(obj);
    }


    @Override
    public void readFromNbt(NbtCompound tag) {
        setOwner(tag.getString("ownerUUID").isEmpty() ? null : UUID.fromString(tag.getString("ownerUUID")));

    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putString("ownerUUID", getOwner() == null ? "" : getOwner().toString());
    }
}
