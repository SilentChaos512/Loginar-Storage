package net.silentchaos512.loginar.setup;

import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.block.urn.UrnData;

import java.util.function.Supplier;

public class LsDataComponents {
    public static final DeferredRegister.DataComponents REGISTRAR = DeferredRegister.createDataComponents(LoginarMod.MOD_ID);

    public static final Supplier<DataComponentType<NonNullList<ItemStack>>> CONTAINED_ITEMS = REGISTRAR.registerComponentType(
            "contained_items",
            builder -> builder
                    .persistent(NonNullList.codecOf(ItemStack.CODEC))
                    .networkSynchronized(ItemStack.LIST_STREAM_CODEC.map(
                            NonNullList::copyOf,
                            nonnulllist -> nonnulllist
                    ))
    );

    public static final Supplier<DataComponentType<UrnData>> URN_DATA = REGISTRAR.registerComponentType(
            "urn_data",
            builder -> builder
                    .persistent(UrnData.CODEC)
                    .networkSynchronized(UrnData.STREAM_CODEC)
    );
}
