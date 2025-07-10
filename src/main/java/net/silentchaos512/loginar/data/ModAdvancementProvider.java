package net.silentchaos512.loginar.data;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ConsumeItemTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.silentchaos512.lib.util.NameUtils;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.setup.LsBlocks;
import net.silentchaos512.loginar.setup.LsItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementProvider extends AdvancementProvider {
    public ModAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(new Advancements()));
    }

    private static class Advancements implements AdvancementGenerator {
        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
            AdvancementHolder root = Advancement.Builder.advancement()
                    .display(
                            new ItemStack(LsItems.LOGINAR_ANTENNA.get()),
                            title("root"),
                            description("root"),
                            ResourceLocation.withDefaultNamespace("textures/block/deepslate_bricks.png"),
                            AdvancementType.TASK,
                            false,
                            false,
                            false
                    )
                    .addCriterion("get_item", getItem(LsItems.LOGINAR_ANTENNA))
                    .save(saver, id("root"));

            // Loginar Urns
            var tinyUrn = simpleGetItem(saver, LsBlocks.TINY_LOGINAR_URN, root);
            var smallUrn = simpleGetItem(saver, LsBlocks.SMALL_LOGINAR_URN, tinyUrn);
            var mediumUrn = simpleGetItem(saver, LsBlocks.MEDIUM_LOGINAR_URN, smallUrn);
            var largeUrn = simpleGetItem(saver, LsBlocks.LARGE_LOGINAR_URN, mediumUrn);
            var hugeUrn = simpleGetItem(saver, LsBlocks.HUGE_LOGINAR_URN, largeUrn);
            var superUrn = simpleGetItem(saver, LsBlocks.SUPER_LOGINAR_URN, hugeUrn, AdvancementType.GOAL);

            var loginarCalamari = Advancement.Builder.advancement()
                    .parent(root)
                    .display(
                            LsItems.LOGINAR_CALAMARI,
                            title("loginar_calamari"),
                            description("loginar_calamari"),
                            null,
                            AdvancementType.TASK,
                            true,
                            true,
                            false
                    )
                    .addCriterion("eat_item", ConsumeItemTrigger.TriggerInstance.usedItem(LsItems.LOGINAR_CALAMARI))
                    .save(saver, id("loginar_calamari"));
        }

        private static AdvancementHolder simpleGetItem(Consumer<AdvancementHolder> saver, ItemLike item, AdvancementHolder parent) {
            return simpleGetItem(saver, item, parent, NameUtils.fromItem(item).getPath());
        }

        private static AdvancementHolder simpleGetItem(Consumer<AdvancementHolder> saver, ItemLike item, AdvancementHolder parent, AdvancementType advancementType) {
            return simpleGetItem(saver, item, new ItemStack(item), parent, NameUtils.fromItem(item).getPath(), advancementType);
        }

        private static AdvancementHolder simpleGetItem(Consumer<AdvancementHolder> saver, ItemLike item, AdvancementHolder parent, String key) {
            return simpleGetItem(saver, item, new ItemStack(item), parent, key, AdvancementType.TASK);
        }

        private static AdvancementHolder simpleGetItem(Consumer<AdvancementHolder> saver, ItemLike item, ItemStack icon, AdvancementHolder parent, String key, AdvancementType advancementType) {
            return Advancement.Builder.advancement()
                    .parent(parent)
                    .display(icon, title(key), description(key), null, advancementType, true, true, false)
                    .addCriterion("get_item", getItem(item))
                    .save(saver, id(key));
        }

        private static String id(String path) {
            return LoginarMod.getId(path).toString();
        }

        private static Criterion<InventoryChangeTrigger.TriggerInstance> getItem(ItemLike... items) {
            return InventoryChangeTrigger.TriggerInstance.hasItems(items);
        }

        private static Criterion<InventoryChangeTrigger.TriggerInstance> getItem(TagKey<Item> tag) {
            return InventoryChangeTrigger.TriggerInstance.hasItems(
                    ItemPredicate.Builder.item().of(tag).build()
            );
        }

        private static Component title(String key) {
            return Component.translatable("advancement.loginar." + key + ".title");
        }

        private static Component description(String key) {
            return Component.translatable("advancement.loginar." + key + ".description");
        }
    }
}
