package net.silentchaos512.loginar.data;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.data.loot.packs.VanillaLootTableProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContextSource;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SmeltItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlock;
import net.silentchaos512.loginar.setup.LsBlocks;
import net.silentchaos512.loginar.setup.LsDataComponents;
import net.silentchaos512.loginar.setup.LsItems;
import net.silentchaos512.loginar.setup.UrnTypes;
import net.silentchaos512.loginar.util.Const;

import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class ModLootTableProvider extends LootTableProvider {
    public ModLootTableProvider(GatherDataEvent event) {
        super(
                event.getGenerator().getPackOutput(),
                Collections.emptySet(),
                VanillaLootTableProvider.create(event.getGenerator().getPackOutput(), event.getLookupProvider()).getTables(),
                event.getLookupProvider()
        );
    }

    @Override
    protected void validate(WritableRegistry<LootTable> tables, ValidationContextSource validationContext, ProblemReporter.Collector problems) {
    }

    @Override
    public List<SubProviderEntry> getTables() {
        return ImmutableList.of(
                new SubProviderEntry(ModBlockLoot::new, LootContextParamSets.BLOCK),
                new SubProviderEntry(provider -> new ModChestLoot(), LootContextParamSets.CHEST),
                new SubProviderEntry(ModEntityLoot::new, LootContextParamSets.ENTITY)
        );
    }

    private static Identifier modId(String path) {
        return LoginarMod.getId(path);
    }

    public static final class ModBlockLoot extends BlockLootSubProvider {
        private ModBlockLoot(HolderLookup.Provider provider) {
            super(Collections.emptySet(), FeatureFlags.REGISTRY.allFlags(), provider);
        }

        @Override
        protected void generate() {
            // Loginar urns (very similar to shulker boxes)
            for (UrnTypes type : UrnTypes.values()) {
                LoginarUrnBlock block = type.block().get();
                add(block, createLoginarUrnDrop(block));
            }

            add(LsBlocks.LOGINAR_EGG.get(), createSilkTouchOnlyTable(LsBlocks.LOGINAR_EGG));
            dropSelf(LsBlocks.FIRE_FLOWER.get());
            add(LsBlocks.POTTED_FIRE_FLOWER.get(), createPotFlowerItemTable(LsBlocks.FIRE_FLOWER));
        }

        private LootTable.Builder createLoginarUrnDrop(LoginarUrnBlock block) {
            return LootTable.lootTable()
                    .withPool(
                            applyExplosionCondition(
                                    block,
                                    LootPool.lootPool()
                                            .setRolls(ConstantValue.exactly(1))
                                            .add(
                                                    LootItem.lootTableItem(block)
                                                            .apply(
                                                                    CopyComponentsFunction.copyComponentsFromBlockEntity(LootContextParams.BLOCK_ENTITY)
                                                                            .include(DataComponents.CUSTOM_NAME)
                                                                            .include(DataComponents.LOCK)
                                                                            .include(DataComponents.CONTAINER_LOOT)
                                                                            .include(LsDataComponents.URN_CLAY_COLOR.get())
                                                                            .include(LsDataComponents.URN_GEM_COLOR.get())
                                                                            .include(LsDataComponents.CONTAINED_ITEMS.get())
                                                                            .include(LsDataComponents.URN_UPGRADES.get())
                                                            )
                                            )
                            )
                    );
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return LsBlocks.REGISTER.getEntries().stream().map(DeferredHolder::get).collect(Collectors.toList());
        }
    }

    public static final class ModChestLoot implements LootTableSubProvider {
        @Override
        public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> biConsumer) {
            biConsumer.accept(Const.CHESTS_LOGINAR_DUNGEON, LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(UniformGenerator.between(2, 3))
                            .add(LootItem.lootTableItem(LsItems.TINY_CRYSTAL)
                                    .setWeight(3)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 3)))
                            )
                            .add(LootItem.lootTableItem(LsItems.SMALL_CRYSTAL)
                                    .setWeight(3)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2)))
                            )
                            .add(LootItem.lootTableItem(LsItems.MEDIUM_CRYSTAL)
                                    .setWeight(1)
                            )
                            .add(LootItem.lootTableItem(Items.BONE)
                                    .setWeight(3)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 6)))
                            )
                            .add(LootItem.lootTableItem(Items.GUNPOWDER)
                                    .setWeight(5)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 4)))
                            )
                            .add(LootItem.lootTableItem(Items.CLOCK)
                                    .setWeight(1)
                            )
                    )
                    .withPool(LootPool.lootPool()
                            .setRolls(UniformGenerator.between(2, 4))
                            .add(LootItem.lootTableItem(Items.APPLE)
                                    .setWeight(1)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 4)))
                            )
                            .add(LootItem.lootTableItem(Items.COAL)
                                    .setWeight(1)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(4, 10)))
                            )
                            .add(LootItem.lootTableItem(Items.GLOW_BERRIES)
                                    .setWeight(1)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2)))
                            )
                            .add(LootItem.lootTableItem(Items.MELON_SLICE)
                                    .setWeight(1)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 8)))
                            )
                            .add(LootItem.lootTableItem(Items.STICK)
                                    .setWeight(1)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 7)))
                            )
                    )
                    .withPool(LootPool.lootPool()
                            .setRolls(UniformGenerator.between(1, 2))
                            .add(LootItem.lootTableItem(Items.DIAMOND)
                                    .setWeight(12)
                            )
                            .add(LootItem.lootTableItem(Items.EMERALD)
                                    .setWeight(12)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2)))
                            )
                            .add(LootItem.lootTableItem(Items.QUARTZ)
                                    .setWeight(12)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 7)))
                            )
                            .add(LootItem.lootTableItem(Items.AMETHYST_SHARD)
                                    .setWeight(12)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(2, 6)))
                            )
                            .add(LootItem.lootTableItem(Items.NETHERITE_INGOT)
                                    .setWeight(1)
                            )
                    )
            );
        }
    }

    public static final class ModEntityLoot extends EntityLootSubProvider {
        public ModEntityLoot(HolderLookup.Provider registries) {
            super(FeatureFlags.REGISTRY.allFlags(), registries);
        }

        @Override
        public void generate() {
        }

        @Override
        public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> biConsumer) {
            biConsumer.accept(Const.ENTITIES_LOGINAR, LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(LsItems.LOGINAR_ANTENNA)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                                    .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                                    .when(LootItemKilledByPlayerCondition.killedByPlayer())
                            )
                    )
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(LsItems.LOGINAR_TENTACLE)
                                    .apply(SmeltItemFunction.smelted().when(this.shouldSmeltLoot()))
                                    .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                            )
                    )
                    .withPool(
                            LootPool.lootPool()
                                    .setRolls(ConstantValue.exactly(1.0F))
                                    .add(LootItem.lootTableItem(LsItems.FIRE_PEARL))
                                    .when(LootItemKilledByPlayerCondition.killedByPlayer())
                                    .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(this.registries, 0.06F, 0.02F))
                    )
            );
        }
    }
}
