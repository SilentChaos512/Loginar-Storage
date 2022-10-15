package net.silentchaos512.loginar.data;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.entries.DynamicLoot;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlock;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlockEntity;
import net.silentchaos512.loginar.setup.LsBlocks;
import net.silentchaos512.loginar.setup.LsEntityTypes;
import net.silentchaos512.loginar.setup.LsItems;
import net.silentchaos512.loginar.setup.UrnTypes;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ModLootTableProvider extends LootTableProvider {
    public ModLootTableProvider(DataGenerator gen) {
        super(gen);
    }

    @Override
    public String getName() {
        return super.getName() + ": " + LoginarMod.MOD_ID;
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
        map.forEach((id, table) -> LootTables.validate(validationtracker, id, table));
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return ImmutableList.of(
                Pair.of(ModBlockLoot::new, LootContextParamSets.BLOCK),
                Pair.of(ModEntityLoot::new, LootContextParamSets.ENTITY)
        );
    }

    private static final ResourceLocation modId(String path) {
        return LoginarMod.getId(path);
    }

    public static final class ModBlockLoot extends BlockLoot {
        @Override
        protected void addTables() {
            // Loginar urns (very similar to shulker boxes)
            for (UrnTypes type : UrnTypes.values()) {
                LoginarUrnBlock block = type.block().get();
                BlockEntityType<LoginarUrnBlockEntity> blockEntity = type.blockEntity().get();
                add(block, LootTable.lootTable()
                        .withPool(applyExplosionCondition(block, LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(block)
                                        .apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY))
                                        .apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY)
                                                .copy("Lock", "BlockEntityTag.Lock")
                                                .copy("LootTable", "BlockEntityTag.LootTable")
                                                .copy("LootTableSeed", "BlockEntityTag.LootTableSeed")
                                                .copy("Lidded", "BlockEntityTag.Lidded")
                                                .copy("Color", "BlockEntityTag.Color")
                                                .copy("Gem", "BlockEntityTag.Gem")
                                                .copy("Upgrades", "BlockEntityTag.Upgrades"))
                                        .apply(SetContainerContents.setContents(blockEntity)
                                                .withEntry(DynamicLoot.dynamicEntry(LoginarUrnBlock.CONTENTS))
                                        )
                                )
                        ))
                );
            }
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return LsBlocks.REGISTER.getEntries().stream().map(RegistryObject::get).collect(Collectors.toList());
        }
    }

    public static final class ModEntityLoot extends EntityLoot {
        @Override
        public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
            consumer.accept(modId("entities/loginar"), LootTable.lootTable()
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .add(LootItem.lootTableItem(LsItems.LOGINAR_ANTENNA)
                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                                    .when(LootItemKilledByPlayerCondition.killedByPlayer())
                            )
                    )
                    .withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1.0F))
                            .add(LootItem.lootTableItem(LsItems.LOGINAR_TENTACLE)
                                    .apply(SmeltItemFunction.smelted()
                                            .when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, ENTITY_ON_FIRE)))
                                    .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0.0F, 1.0F)))
                            )
                    )
            );
        }

        @Override
        protected Iterable<EntityType<?>> getKnownEntities() {
            return LsEntityTypes.REGISTER.getEntries().stream().map(RegistryObject::get).collect(Collectors.toList());
        }
    }
}
