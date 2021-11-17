package com.bloomhousemc.bewitchedgarden.common.items.staff;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.network.ISyncable;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class EnchantedStaffItem extends BaseStaffItem implements IAnimatable, ISyncable {
    public AnimationFactory factory = new AnimationFactory(this);
    public EnchantedStaffItem(int maxStorage, Settings settings) {
        super(maxStorage, settings);
        GeckoLibNetwork.registerSyncable(this);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient && user.isSneaking()) {
            ItemStack itemStack = user.getStackInHand(hand);
            if(itemStack.getItem() instanceof EnchantedStaffItem){
                AnimationController controller = GeckoLibUtil.getControllerForStack(this.factory, user.getStackInHand(hand), "controller");
                String animationName = controller.getCurrentAnimation().animationName;
                int mode = Integer.parseInt(animationName.substring(animationName.length()-1));
                if(mode>=6)mode=1;
                mode++;
                itemStack.getOrCreateNbt().putInt("mode", mode);
                final int id = GeckoLibUtil.guaranteeIDForStack(user.getStackInHand(hand), (ServerWorld) world);
                GeckoLibNetwork.syncAnimation(user, this, id, mode);
                for (PlayerEntity otherPlayer : PlayerLookup.tracking(user)) {
                    GeckoLibNetwork.syncAnimation(otherPlayer, this, id, mode);
                }
            }
        }
        return super.use(world, user, hand);
    }

    private <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        if(event.getController().getCurrentAnimation() == null){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.enchanted_staff.glyph_0", true));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 2, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void onAnimationSync(int id, int state) {
            final AnimationController controller = GeckoLibUtil.getControllerForID(this.factory, id, "controller");
                controller.markNeedsReload();
                controller.setAnimation(new AnimationBuilder().addAnimation("animation.enchanted_staff.glyph_"+state, true));
        }
}
