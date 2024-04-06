package net.silentchaos512.loginar.setup;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlock;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlockItem;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class LsBlocks {
    public static final DeferredRegister.Blocks REGISTER = DeferredRegister.createBlocks(LoginarMod.MOD_ID);

    public static final DeferredBlock<LoginarUrnBlock> TINY_LOGINAR_URN = registerUrn(UrnTypes.TINY);
    public static final DeferredBlock<LoginarUrnBlock> SMALL_LOGINAR_URN = registerUrn(UrnTypes.SMALL);
    public static final DeferredBlock<LoginarUrnBlock> MEDIUM_LOGINAR_URN = registerUrn(UrnTypes.MEDIUM);
    public static final DeferredBlock<LoginarUrnBlock> LARGE_LOGINAR_URN = registerUrn(UrnTypes.LARGE);
    public static final DeferredBlock<LoginarUrnBlock> HUGE_LOGINAR_URN = registerUrn(UrnTypes.HUGE);
    public static final DeferredBlock<LoginarUrnBlock> SUPER_LOGINAR_URN = registerUrn(UrnTypes.SUPER);

    public static Collection<LoginarUrnBlock> getUrns() {
        return REGISTER.getEntries().stream()
                .map(DeferredHolder::get)
                .filter(b -> b instanceof LoginarUrnBlock)
                .map(b -> (LoginarUrnBlock) b)
                .collect(Collectors.toList());
    }

    private static <T extends Block> DeferredBlock<T> registerNoItem(String name, Supplier<T> block) {
        return REGISTER.register(name, block);
    }

    private static <T extends Block> DeferredBlock<T> register(String name, Supplier<T> block) {
        return register(name, block, LsBlocks::defaultItem);
    }

    private static <T extends Block> DeferredBlock<T> register(String name, Supplier<T> block, Function<DeferredBlock<T>, Supplier<? extends BlockItem>> item) {
        DeferredBlock<T> ret = registerNoItem(name, block);
        LsItems.register(name, item.apply(ret));
        return ret;
    }

    private static <T extends Block> Supplier<BlockItem> defaultItem(DeferredBlock<T> block) {
        return () -> new BlockItem(block.get(), new Item.Properties());
    }

    private static DeferredBlock<LoginarUrnBlock> registerUrn(UrnTypes type) {
        return register(type.name().toLowerCase(Locale.ROOT) + "_loginar_urn",
                () -> new LoginarUrnBlock(type, urnProps()),
                bro -> () -> new LoginarUrnBlockItem(bro.get(), new Item.Properties().stacksTo(1))
        );
    }

    @NotNull
    private static BlockBehaviour.Properties urnProps() {
        return BlockBehaviour.Properties.of()
                .strength(2f)
                .noOcclusion()
                .isSuffocating((state, level, pos) -> false)
                .isViewBlocking((state, level, pos) -> false)
                .pushReaction(PushReaction.DESTROY);
    }
}
