package com.bloomhousemc.bewitchedgarden.client.renderer;

import com.bloomhousemc.bewitchedgarden.client.model.EnchantedStaffModel;
import com.bloomhousemc.bewitchedgarden.common.items.EnchantedStaffItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class EnchantedStaffRenderer extends GeoItemRenderer<EnchantedStaffItem> {
    public EnchantedStaffRenderer() {
        super(new EnchantedStaffModel());
    }

}