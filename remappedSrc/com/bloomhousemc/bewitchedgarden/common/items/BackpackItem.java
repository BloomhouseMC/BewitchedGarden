package com.bloomhousemc.bewitchedgarden.common.items;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class BackpackItem extends BlockItem implements IAnimatable {
    public AnimationFactory factory = new AnimationFactory(this);

    public BackpackItem(Block block, Settings settings) {
        super(block, settings.maxCount(1));
    }

    @Override
    public void registerControllers(AnimationData animationData) {

    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
