package net.silentchaos512.loginar.setup;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.silentchaos512.loginar.LoginarMod;

public final class LsTags {
    public static final class Blocks {
        public static final TagKey<Block> URNS = mod("urns");

        private static TagKey<Block> forge(String path) {
            return BlockTags.create(new ResourceLocation("forge", path));
        }

        private static TagKey<Block> mod(String path) {
            return BlockTags.create(LoginarMod.getId(path));
        }
    }

    public static final class Items {
        public static final TagKey<Item> FLOWER_BASKET_CAN_STORE = mod("flower_basket_can_store");
        public static final TagKey<Item> GEM_BAG_CAN_STORE = mod("gem_bag_can_store");
        public static final TagKey<Item> ORE_CRATE_CAN_STORE = mod("ore_crate_can_store");
        public static final TagKey<Item> URN_UPGRADES = mod("urn_upgrades");
        public static final TagKey<Item> URNS = mod("urns");

        private static TagKey<Item> forge(String path) {
            return ItemTags.create(new ResourceLocation("forge", path));
        }

        private static TagKey<Item> mod(String path) {
            return ItemTags.create(LoginarMod.getId(path));
        }
    }
}
