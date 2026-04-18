package net.silentchaos512.loginar.util;

import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;
import net.silentchaos512.loginar.LoginarMod;

public final class Const {
    public static final Identifier FILLED = LoginarMod.getId("filled");
    public static final Identifier IS_LOGINAR_CHUNK = LoginarMod.getId("is_loginar_chunk");

    // Loot tables
    public static final ResourceKey<LootTable> CHESTS_LOGINAR_DUNGEON = ResourceKey.create(
            Registries.LOOT_TABLE,
            LoginarMod.getId("chests/loginar_dungeon")
    );
    public static final ResourceKey<LootTable> ENTITIES_LOGINAR = ResourceKey.create(
            Registries.LOOT_TABLE,
            LoginarMod.getId("entities/loginar")
    );

    // Equipment Assets
    public static final ResourceKey<EquipmentAsset> LOGINAR_BODY_ARMOR_EQUIPMENT_ASSET = ResourceKey.create(EquipmentAssets.ROOT_ID, LoginarMod.getId("loginar_body_armor"));

    // Enum Extensions
    public static final EnumProxy<EquipmentClientInfo.LayerType> LOGINAR_BODY_LAYER = new EnumProxy<>(
            EquipmentClientInfo.LayerType.class, "loginar:armor");

    private Const() {}
}
