package net.silentchaos512.loginar.crafting.recipe;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.neoforged.neoforge.common.Tags;
import net.silentchaos512.lib.crafting.recipe.ExtendedShapedRecipe;
import net.silentchaos512.lib.util.Color;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlock;
import net.silentchaos512.loginar.block.urn.UrnData;
import net.silentchaos512.loginar.compat.SgearCompat;
import net.silentchaos512.loginar.setup.LsRecipeSerializers;

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

    private final Color clayColor;

    public UrnBaseRecipe(String pGroup, CraftingBookCategory pCategory, ShapedRecipePattern pPattern, ItemStack pResult, Color clayColor) {
        super(pGroup, pCategory, pPattern, pResult, false);
        this.clayColor = clayColor;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return LsRecipeSerializers.URN.get();
    }

    @Override
    public ItemStack assemble(CraftingContainer craftingContainer, RegistryAccess registryAccess) {
        ItemStack baseResult = super.getResultItem(registryAccess);
        if (baseResult.getItem() instanceof BlockItem && ((BlockItem) baseResult.getItem()).getBlock() instanceof LoginarUrnBlock block) {
            int gemColor = getGemColor(findGem(craftingContainer));
            return block.makeStack(this.clayColor.getColor(), gemColor);
        } else {
            LoginarMod.LOGGER.error("Result of urn base recipe {} is not an urn", this);
            return ItemStack.EMPTY;
        }
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        ItemStack baseResult = super.getResultItem(registryAccess);
        if (baseResult.getItem() instanceof BlockItem && ((BlockItem) baseResult.getItem()).getBlock() instanceof LoginarUrnBlock block) {
            return block.makeStack(this.clayColor.getColor(), UrnData.DEFAULT_GEM_COLOR);
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

    public static class Serializer implements RecipeSerializer<UrnBaseRecipe> {
        public static final Codec<UrnBaseRecipe> CODEC = RecordCodecBuilder.create(
                builder -> builder.group(
                                ExtraCodecs.strictOptionalField(Codec.STRING, "group", "").forGetter(r -> r.group),
                                CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(r -> r.category),
                                ShapedRecipePattern.MAP_CODEC.forGetter(r -> r.pattern),
                                ItemStack.ITEM_WITH_COUNT_CODEC.fieldOf("result").forGetter(r -> r.result),
                                ExtraCodecs.strictOptionalField(Color.CODEC, "clay_color", new Color(UrnData.DEFAULT_CLAY_COLOR)).forGetter(r -> r.clayColor)
                        )
                        .apply(builder, UrnBaseRecipe::new)
        );

        @Override
        public Codec<UrnBaseRecipe> codec() {
            return CODEC;
        }

        @Override
        public UrnBaseRecipe fromNetwork(FriendlyByteBuf pBuffer) {
            String s = pBuffer.readUtf();
            CraftingBookCategory craftingbookcategory = pBuffer.readEnum(CraftingBookCategory.class);
            ShapedRecipePattern shapedrecipepattern = ShapedRecipePattern.fromNetwork(pBuffer);
            ItemStack itemstack = pBuffer.readItem();
            Color clayColor = Color.read(pBuffer);
            return new UrnBaseRecipe(s, craftingbookcategory, shapedrecipepattern, itemstack, clayColor);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, UrnBaseRecipe pRecipe) {
            pBuffer.writeUtf(pRecipe.group);
            pBuffer.writeEnum(pRecipe.category);
            pRecipe.pattern.toNetwork(pBuffer);
            pBuffer.writeItem(pRecipe.result);
            pRecipe.clayColor.write(pBuffer);
        }
    }
}
