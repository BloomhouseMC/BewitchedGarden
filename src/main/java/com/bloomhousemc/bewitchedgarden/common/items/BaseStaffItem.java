package com.bloomhousemc.bewitchedgarden.common.items;

import moriyashiine.bewitchment.api.block.WitchAltarBlock;
import moriyashiine.bewitchment.common.block.entity.WitchAltarBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BaseStaffItem extends Item {
    private int maxStorage;
    private int storedPower;

    public BaseStaffItem(int maxStorage, Settings settings) {
        super(settings);
        this.maxStorage = maxStorage;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos pos = context.getBlockPos();
        World world = context.getWorld();
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof WitchAltarBlock altar && world.getBlockEntity(pos) instanceof WitchAltarBlockEntity altarBlockEntity) {
            while (altarBlockEntity.power > 0 && this.getStoredPower() < this.getMaxStorage()) {
                altarBlockEntity.drain(1, false);
                ++storedPower;
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    public void remove(int power) {
        if (this.storedPower >= power) {
            this.storedPower = this.storedPower - power;
        }
    }

    public int getMaxStorage() {
        return maxStorage;
    }

    public int getStoredPower() {
        return storedPower;
    }

    public void check() {
        if (this.getStoredPower() > this.getMaxStorage()) {
            this.storedPower = this.getMaxStorage();
        }
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new LiteralText("Power: " + storedPower + "/" + maxStorage).formatted(Formatting.DARK_GREEN));
    }
}
