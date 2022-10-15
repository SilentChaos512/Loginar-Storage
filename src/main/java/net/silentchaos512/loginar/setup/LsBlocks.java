package net.silentchaos512.loginar.setup;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.silentchaos512.lib.registry.BlockRegistryObject;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlock;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class LsBlocks {
    public static final DeferredRegister<Block> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, LoginarMod.MOD_ID);

    public static final BlockRegistryObject<LoginarUrnBlock> TINY_LOGINAR_URN = registerUrn(UrnTypes.TINY);
    public static final BlockRegistryObject<LoginarUrnBlock> SMALL_LOGINAR_URN = registerUrn(UrnTypes.SMALL);
    public static final BlockRegistryObject<LoginarUrnBlock> MEDIUM_LOGINAR_URN = registerUrn(UrnTypes.MEDIUM);
    public static final BlockRegistryObject<LoginarUrnBlock> LARGE_LOGINAR_URN = registerUrn(UrnTypes.LARGE);
    public static final BlockRegistryObject<LoginarUrnBlock> HUGE_LOGINAR_URN = registerUrn(UrnTypes.HUGE);
    public static final BlockRegistryObject<LoginarUrnBlock> SUPER_LOGINAR_URN = registerUrn(UrnTypes.SUPER);

    public static Collection<LoginarUrnBlock> getUrns() {
        return REGISTER.getEntries().stream()
                .map(RegistryObject::get)
                .filter(b -> b instanceof LoginarUrnBlock)
                .map(b -> (LoginarUrnBlock) b)
                .collect(Collectors.toList());
    }

    private static <T extends Block> BlockRegistryObject<T> registerNoItem(String name, Supplier<T> block) {
        return new BlockRegistryObject<>(REGISTER.register(name, block));
    }

    private static <T extends Block> BlockRegistryObject<T> register(String name, Supplier<T> block) {
        return register(name, block, LsBlocks::defaultItem);
    }

    private static <T extends Block> BlockRegistryObject<T> register(String name, Supplier<T> block, Function<BlockRegistryObject<T>, Supplier<? extends BlockItem>> item) {
        BlockRegistryObject<T> ret = registerNoItem(name, block);
        LsItems.REGISTER.register(name, item.apply(ret));
        return ret;
    }

    private static <T extends Block> Supplier<BlockItem> defaultItem(BlockRegistryObject<T> block) {
        return () -> new BlockItem(block.get(), new Item.Properties().tab(CreativeModeTab.TAB_TOOLS));
    }

    private static BlockRegistryObject<LoginarUrnBlock> registerUrn(UrnTypes type) {
        return register(type.name().toLowerCase(Locale.ROOT) + "_loginar_urn", () -> new LoginarUrnBlock(type, urnProps()));
    }

    @NotNull
    private static BlockBehaviour.Properties urnProps() {
        return BlockBehaviour.Properties.of(Material.STONE)
                .strength(3f)
                .noOcclusion()
                .isSuffocating((state, level, pos) -> false)
                .isViewBlocking((state, level, pos) -> false);
    }
}
