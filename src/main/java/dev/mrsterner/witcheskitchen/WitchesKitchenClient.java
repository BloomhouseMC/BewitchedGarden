package dev.mrsterner.witcheskitchen;

import dev.mrsterner.witcheskitchen.common.registry.WKRendering;
import net.fabricmc.api.ClientModInitializer;

public class WitchesKitchenClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        WKRendering.init();
    }
}
