package net.silentchaos512.loginar.data.client;

import net.minecraft.client.data.models.EquipmentAssetProvider;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.util.Const;

import java.util.function.BiConsumer;

public class ModEquipmentAssetProvider extends EquipmentAssetProvider {
    public ModEquipmentAssetProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void registerModels(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> output) {
        output.accept(
                Const.LOGINAR_BODY_ARMOR_EQUIPMENT_ASSET,
                EquipmentClientInfo.builder()
                        .addLayers(
                                Const.LOGINAR_BODY_LAYER.getValue(),
                                new EquipmentClientInfo.Layer(LoginarMod.getId("loginar_body_armor"))
                        )
                        .build()
        );
    }

    @Override
    public String getName() {
        return "Loginar Storage - Equipment Asset Definitions";
    }
}
