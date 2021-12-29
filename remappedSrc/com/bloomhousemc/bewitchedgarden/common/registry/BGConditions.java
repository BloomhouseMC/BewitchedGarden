package com.bloomhousemc.bewitchedgarden.common.registry;

import com.bloomhousemc.bewitchedgarden.BewitchedGarden;
import com.bloomhousemc.bewitchedgarden.common.util.BGUtils;
import io.github.ytg1234.recipeconditions.api.RecipeConds;
import io.github.ytg1234.recipeconditions.api.condition.util.RecipeCondsUtil;
import vazkii.patchouli.api.PatchouliAPI;

@SuppressWarnings("ConstantConditions")
public class BGConditions {
    public static void init(){
        PatchouliAPI.get().setConfigFlag("bg_backpack", BewitchedGarden.config.enableBackpack);
        BGUtils.register(RecipeConds.RECIPE_CONDITION, "bg_config", RecipeCondsUtil.stringParam(BGConditions::getOption));
    }
    public static boolean getOption(String key){
        switch (key) {
            case "backpack":
                return BewitchedGarden.config.enableBackpack;
        }
        return false;
    }
}
