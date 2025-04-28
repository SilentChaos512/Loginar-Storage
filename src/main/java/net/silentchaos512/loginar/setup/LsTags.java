package net.silentchaos512.loginar.setup;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.silentchaos512.loginar.LoginarMod;

public final class LsTags {
    public static final class Blocks {
        public static final TagKey<Block> URNS = mod("urns");

        private static TagKey<Block> mod(String path) {
            return BlockTags.create(LoginarMod.getId(path));
        }
    }

    public static final class DamageTypes {
        public static final TagKey<DamageType> BYPASSES_LOGINAR_ARMOR = mod("bypasses_loginar_armor");

        private static TagKey<DamageType> mod(String path) {
            return TagKey.create(Registries.DAMAGE_TYPE, LoginarMod.getId(path));
        }
    }

    public static final class Items {
        public static final TagKey<Item> FLOWER_BASKET_CAN_STORE = mod("flower_basket_can_store");
        public static final TagKey<Item> GEM_BAG_CAN_STORE = mod("gem_bag_can_store");
        public static final TagKey<Item> LOGINAR_ARMOR = mod("loginar_armor");
        public static final TagKey<Item> LOGINAR_ARMOR_BODY = mod("loginar_armor/body");
        public static final TagKey<Item> LOGINAR_ARMOR_FEET = mod("loginar_armor/feet");
        public static final TagKey<Item> LOGINAR_FOOD = mod("loginar_food");
        public static final TagKey<Item> ORE_CRATE_CAN_STORE = mod("ore_crate_can_store");
        public static final TagKey<Item> SEED_BAG_CAN_STORE = mod("seed_bag_can_store");
        public static final TagKey<Item> WOOD_RACK_CAN_STORE = mod("wood_rack_can_store");
        public static final TagKey<Item> URN_UPGRADES = mod("urn_upgrades");
        public static final TagKey<Item> URNS = mod("urns");
        public static final TagKey<Item> URNS_CANNOT_STORE = mod("urns_cannot_store");

        public static final TagKey<Item> GEMS_BORT = common("gems/bort");
        public static final TagKey<Item> GEMS_FIRE_PEARL = common("gems/fire_pearl");

        private static TagKey<Item> mod(String path) {
            return ItemTags.create(LoginarMod.getId(path));
        }

        private static TagKey<Item> common(String path) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", path));
        }
    }
}
