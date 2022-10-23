package net.silentchaos512.loginar.crafting.recipe;

import com.google.common.collect.ImmutableMap;
import net.minecraft.tags.TagKey;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;
import net.silentchaos512.lib.crafting.recipe.ExtendedShapedRecipe;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlock;
import net.silentchaos512.loginar.block.urn.UrnData;
import net.silentchaos512.loginar.compat.SgearCompat;
import net.silentchaos512.loginar.setup.LsRecipeSerializers;
import net.silentchaos512.utils.Color;

import java.util.Map;

public class UrnBaseRecipe extends ExtendedShapedRecipe {
    private static final Map<TagKey<Item>, Integer> GEM_COLORS = ImmutableMap.of(
            Tags.Items.GEMS_AMETHYST, 0x8D6ACC,
            Tags.Items.GEMS_DIAMOND, UrnData.DEFAULT_GEM_COLOR,
            Tags.Items.GEMS_EMERALD, 0x17DD62,
            Tags.Items.GEMS_LAPIS, 0x345EC3,
            Tags.Items.GEMS_PRISMARINE, 0x91C5B7,
            Tags.Items.GEMS_QUARTZ, 0xDDD4C6
    );

    private int color = UrnData.DEFAULT_CLAY_COLOR;

    public UrnBaseRecipe(ShapedRecipe recipe) {
        super(recipe);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return LsRecipeSerializers.URN_BASE.get();
    }

    @Override
    public boolean matches(CraftingContainer craftingContainer, Level level) {
        return this.getBaseRecipe().matches(craftingContainer, level);
    }

    @Override
    public ItemStack assemble(CraftingContainer craftingContainer) {
        ItemStack baseResult = getBaseRecipe().getResultItem();
        if (baseResult.getItem() instanceof BlockItem && ((BlockItem) baseResult.getItem()).getBlock() instanceof LoginarUrnBlock) {
            LoginarUrnBlock block = (LoginarUrnBlock) ((BlockItem) baseResult.getItem()).getBlock();
            int gemColor = getGemColor(findGem(craftingContainer));
            return block.makeStack(this.color, gemColor);
        } else {
            LoginarMod.LOGGER.error("Result of urn base recipe {} is not an urn", getId());
            return ItemStack.EMPTY;
        }
    }

    @Override
    public ItemStack getResultItem() {
        ItemStack baseResult = getBaseRecipe().getResultItem();
        if (baseResult.getItem() instanceof BlockItem && ((BlockItem) baseResult.getItem()).getBlock() instanceof LoginarUrnBlock) {
            LoginarUrnBlock block = (LoginarUrnBlock) ((BlockItem) baseResult.getItem()).getBlock();
            return block.makeStack(this.color, UrnData.DEFAULT_GEM_COLOR);
        }
        return baseResult;
    }

    private static ItemStack findGem(CraftingContainer craftingContainer) {
        for (int i = 0; i < craftingContainer.getContainerSize(); ++i) {
            ItemStack stack = craftingContainer.getItem(i);
            if (stack.is(Tags.Items.GEMS)) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    static int getGemColor(ItemStack stack) {
        for (Map.Entry<TagKey<Item>, Integer> entry : GEM_COLORS.entrySet()) {
            TagKey<Item> tag = entry.getKey();
            Integer color = entry.getValue();

            if (stack.is(tag)) {
                return color;
            }
        }

        // Try to get a color from Silent Gear
        int gearMaterialColor = SgearCompat.getMainPartColor(stack);
        if ((gearMaterialColor & 0xFFFFFF) != 0xFFFFFF) {
            LoginarMod.LOGGER.debug("Got gem color {} for {} from Silent Gear", Color.format(gearMaterialColor), stack);
            return gearMaterialColor;
        }

        return UrnData.DEFAULT_GEM_COLOR;
    }

    public static class Serializer extends ExtendedShapedRecipe.Serializer<UrnBaseRecipe> {
        public Serializer() {
            super(
                    UrnBaseRecipe::new,
                    (json, recipe) -> {
                        recipe.color = Color.from(json, "clay_color", UrnData.DEFAULT_CLAY_COLOR).getColor() & 0xFFFFFF;
                    },
                    (buf, recipe) -> {
                        recipe.color = buf.readVarInt();
                    },
                    (buf, recipe) -> {
                        buf.writeVarInt(recipe.color);
                    }
            );
        }
    }
}
