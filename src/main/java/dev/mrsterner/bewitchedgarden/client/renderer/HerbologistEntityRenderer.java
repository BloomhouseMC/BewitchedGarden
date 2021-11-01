package dev.mrsterner.bewitchedgarden.client.renderer;

import dev.mrsterner.bewitchedgarden.BewitchedGarden;
import dev.mrsterner.bewitchedgarden.common.entity.HerbologistEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.feature.VillagerHeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.VillagerResemblingModel;
import net.minecraft.util.Identifier;

public class HerbologistEntityRenderer extends MobEntityRenderer<HerbologistEntity, VillagerResemblingModel<HerbologistEntity>> {
    private static final Identifier TEXTURE = new Identifier(BewitchedGarden.MODID, "textures/entity/herbologist.png");

    public HerbologistEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new VillagerResemblingModel(context.getPart(EntityModelLayers.WANDERING_TRADER)), 0.5F);
        this.addFeature(new HeadFeatureRenderer(this, context.getModelLoader()));
        this.addFeature(new VillagerHeldItemFeatureRenderer(this));
    }

    public Identifier getTexture(HerbologistEntity entity) {
        return TEXTURE;
    }
}
