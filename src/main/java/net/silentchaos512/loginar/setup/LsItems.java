package net.silentchaos512.loginar.setup;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.silentchaos512.lib.registry.ItemRegistryObject;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.item.LoginarTentacleItem;

import java.util.function.Supplier;

public class LsItems {
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, LoginarMod.MOD_ID);

    public static final ItemRegistryObject<Item> LOGINAR_ANTENNA = register("loginar_antenna", () ->
            new Item(props())
    );
    public static final ItemRegistryObject<LoginarTentacleItem> LOGINAR_TENTACLE = register("loginar_tentacle", () ->
            new LoginarTentacleItem(props()
                    .tab(CreativeModeTab.TAB_FOOD)
                    .food(LsFoods.LOGINAR_TENTACLE)
            )
    );
    public static final ItemRegistryObject<Item> LOGINAR_CALAMARI = register("loginar_calamari", () ->
            new Item(props()
                    .tab(CreativeModeTab.TAB_FOOD)
                    .food(LsFoods.LOGINAR_CALAMARI)
            )
    );

    public static final ItemRegistryObject<ForgeSpawnEggItem> LOGINAR_SPAWN_EGG = register("loginar_spawn_egg", () ->
            new ForgeSpawnEggItem(LsEntityTypes.LOGINAR, 0x59B9FF, 0xFFFFFF, props()));

    private static <T extends Item> ItemRegistryObject<T> register(String name, Supplier<T> item) {
        return new ItemRegistryObject<>(REGISTER.register(name, item));
    }

    private static Item.Properties props() {
        return new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS);
    }
}
