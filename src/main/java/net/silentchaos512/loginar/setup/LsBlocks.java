package net.silentchaos512.loginar.setup;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.block.LoginarEggBlock;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlock;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlockItem;

import java.util.Collection;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LsBlocks {
    public static final DeferredRegister.Blocks REGISTER = DeferredRegister.createBlocks(LoginarMod.MOD_ID);

    public static final DeferredBlock<LoginarUrnBlock> TINY_LOGINAR_URN = registerUrn(UrnTypes.TINY);
    public static final DeferredBlock<LoginarUrnBlock> SMALL_LOGINAR_URN = registerUrn(UrnTypes.SMALL);
    public static final DeferredBlock<LoginarUrnBlock> MEDIUM_LOGINAR_URN = registerUrn(UrnTypes.MEDIUM);
    public static final DeferredBlock<LoginarUrnBlock> LARGE_LOGINAR_URN = registerUrn(UrnTypes.LARGE);
    public static final DeferredBlock<LoginarUrnBlock> HUGE_LOGINAR_URN = registerUrn(UrnTypes.HUGE);
    public static final DeferredBlock<LoginarUrnBlock> SUPER_LOGINAR_URN = registerUrn(UrnTypes.SUPER);

    public static final DeferredBlock<LoginarEggBlock> LOGINAR_EGG = register(
            "loginar_egg",
            LoginarEggBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_CYAN)
                    .forceSolidOn()
                    .strength(0.5F)
                    .sound(SoundType.METAL)
                    .randomTicks()
                    .noOcclusion()
                    .pushReaction(PushReaction.DESTROY)
    );

    public static Collection<LoginarUrnBlock> getUrns() {
        return REGISTER.getEntries().stream()
                .map(DeferredHolder::get)
                .filter(b -> b instanceof LoginarUrnBlock)
                .map(b -> (LoginarUrnBlock) b)
                .collect(Collectors.toList());
    }

    private static <T extends Block> DeferredBlock<T> registerNoItem(
            String name,
            Function<BlockBehaviour.Properties, T> block,
            BlockBehaviour.Properties properties
    ) {
        return REGISTER.registerBlock(name, block, properties);
    }

    private static <T extends Block> DeferredBlock<T> register(
            String name,
            Function<BlockBehaviour.Properties, T> block,
            BlockBehaviour.Properties properties
    ) {
        return register(name, block, properties, LsBlocks::defaultItem, new Item.Properties());
    }

    private static <T extends Block> DeferredBlock<T> register(
            String name,
            Function<BlockBehaviour.Properties, T> block,
            BlockBehaviour.Properties properties,
            Function<DeferredBlock<T>, Function<Item.Properties, ? extends BlockItem>> item,
            Item.Properties itemProperties
    ) {
        DeferredBlock<T> ret = registerNoItem(name, block, properties);
        LsItems.register(name, item.apply(ret), itemProperties);
        return ret;
    }

    private static <T extends Block> Function<Item.Properties, BlockItem> defaultItem(DeferredBlock<T> block) {
        return p -> new BlockItem(block.get(), p);
    }

    private static DeferredBlock<LoginarUrnBlock> registerUrn(UrnTypes type) {
        return register(type.name().toLowerCase(Locale.ROOT) + "_loginar_urn",
                p -> new LoginarUrnBlock(type, p),
                BlockBehaviour.Properties.of()
                        .strength(2f)
                        .noOcclusion()
                        .isSuffocating((state, level, pos) -> false)
                        .isViewBlocking((state, level, pos) -> false)
                        .pushReaction(PushReaction.DESTROY),
                block -> p -> new LoginarUrnBlockItem(
                        block.get(),
                        p
                ),
                new Item.Properties().stacksTo(1)
        );
    }

}
