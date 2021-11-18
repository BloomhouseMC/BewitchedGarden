package com.bloomhousemc.bewitchedgarden.client.screen;

import com.bloomhousemc.bewitchedgarden.BewitchedGarden;
import com.bloomhousemc.bewitchedgarden.common.network.EquipBackpackPacket;
import com.bloomhousemc.bewitchedgarden.common.screen.BackpackScreenHandler;
import com.bloomhousemc.bewitchedgarden.common.util.BGUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class BackpackScreen extends HandledScreen<BackpackScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(BewitchedGarden.MODID, "textures/gui/backpack.png");
    protected PlayerInventory playerInventory;
    protected ButtonWidget equipButton;
    protected PlayerEntity player;
    protected int craftingX;
    protected int craftingY;

    public BackpackScreen(BackpackScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
        this.playerInventory = playerInventory;
        this.backgroundWidth = 322;
        this.backgroundHeight = 172;
        this.player = playerInventory.player;
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - 322) / 2;
        int y = (height - 172) / 2;
        DrawableHelper.drawTexture(matrices, x, y, 0, 0, 0, 322, 172, 172, 322);
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        super.drawForeground(matrices, mouseX, mouseY);
        textRenderer.draw(matrices, new TranslatableText("container.crafting"), craftingX, craftingY, 4210752);

    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
        InventoryScreen.drawEntity(x + 50, y + 125 + 9, 30, (x + 50) - mouseX, (y + 125 - 50) - mouseY, player);

        boolean canPlace = BGUtils.isReplaceable(player.world, handler.blockPos.offset(player.getHorizontalFacing()));
        boolean canEquip = player.getEquippedStack(EquipmentSlot.CHEST).isEmpty();

        if(equipButton.isHovered()) {
            if(!handler.isBlockEntity && !canPlace)
                renderTooltip(matrices, new TranslatableText("container.bewitchedgarden.obstructed_block"), mouseX, mouseY);
            if(handler.isBlockEntity && !canEquip)
                renderTooltip(matrices, new TranslatableText("container.bewitchedgarden.cant_equip"), mouseX, mouseY);
        }

        if(equipButton != null)
            equipButton.active = (!handler.isBlockEntity && canPlace) || canEquip;
    }

    @Override
    protected void init() {
        super.init();
        titleX = 81;
        playerInventoryTitleX = 81;
        playerInventoryTitleY = 78;
        craftingX = 255;
        craftingY = 6;
        equipButton = addDrawableChild(new ButtonWidget(width / 2 + 86, height / 2 + 58, 68, 20, new TranslatableText(handler.isBlockEntity ? "container.bewitchedgarden.equip" : "container.bewitchedgarden.unequip"), this::doButtonShit));
    }

    private void doButtonShit(ButtonWidget button) {
        if(handler.isBlockEntity && player.getEquippedStack(EquipmentSlot.CHEST).isEmpty())
            EquipBackpackPacket.send(true, handler.blockPos);
        else if(!handler.isBlockEntity)
            EquipBackpackPacket.send(false, handler.blockPos);
    }
}
