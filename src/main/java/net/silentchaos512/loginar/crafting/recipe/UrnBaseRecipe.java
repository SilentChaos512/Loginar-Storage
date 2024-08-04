package net.silentchaos512.loginar.crafting.recipe;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.neoforged.neoforge.common.Tags;
import net.silentchaos512.lib.crafting.recipe.ExtendedShapedRecipe;
import net.silentchaos512.lib.util.Color;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlock;
import net.silentchaos512.loginar.block.urn.UrnHelper;
import net.silentchaos512.loginar.compat.SgearCompat;
import net.silentchaos512.loginar.setup.LsRecipeSerializers;
import net.silentchaos512.loginar.setup.LsTags;

import java.util.Map;
import java.util.Optional;

public class UrnBaseRecipe extends ExtendedShapedRecipe {
    // TODO: Must be a better way to handle this... A registry or something?
    private static final Map<TagKey<Item>, Color> GEM_COLORS = ImmutableMap.of(
            Tags.Items.GEMS_AMETHYST, new Color(0x8D6ACC),
            Tags.Items.GEMS_DIAMOND, UrnHelper.DEFAULT_GEM_COLOR,
            Tags.Items.GEMS_EMERALD, new Color(0x17DD62),
            Tags.Items.GEMS_LAPIS, new Color(0x345EC3),
            Tags.Items.GEMS_PRISMARINE, new Color(0x91C5B7),
            Tags.Items.GEMS_QUARTZ, new Color(0xDDD4C6),
            LsTags.Items.GEMS_BORT, new Color(0x96A3D4),
            LsTags.Items.GEMS_FIRE_PEARL, new Color(0xCD462C)
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
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        ItemStack baseResult = super.getResultItem(registries);
        if (baseResult.getItem() instanceof BlockItem && ((BlockItem) baseResult.getItem()).getBlock() instanceof LoginarUrnBlock block) {
            Color gemColor = getGemColor(findGem(input));
            return block.makeStack(this.clayColor, gemColor);
        } else {
            LoginarMod.LOGGER.error("Result of urn base recipe {} is not an urn", this);
            return ItemStack.EMPTY;
        }
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registryAccess) {
        ItemStack baseResult = super.getResultItem(registryAccess);
        if (baseResult.getItem() instanceof BlockItem && ((BlockItem) baseResult.getItem()).getBlock() instanceof LoginarUrnBlock block) {
            return block.makeStack(this.clayColor, null);
        }
        return baseResult;
    }

    private static ItemStack findGem(CraftingInput input) {
        for (int i = 0; i < input.size(); ++i) {
            ItemStack stack = input.getItem(i);
            if (stack.is(Tags.Items.GEMS)) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    static Color getGemColor(ItemStack stack) {
        for (Map.Entry<TagKey<Item>, Color> entry : GEM_COLORS.entrySet()) {
            TagKey<Item> tag = entry.getKey();
            Color color = entry.getValue();

            if (stack.is(tag)) {
                return color;
            }
        }

        // Try to get a color from Silent Gear
        Optional<Color> gearMaterialColor = SgearCompat.getMainPartColor(stack);
        if (gearMaterialColor.isPresent()) {
            var formattedString = gearMaterialColor.get().format();
            LoginarMod.LOGGER.debug("Got gem color {} for {} from Silent Gear", formattedString, stack);
            return gearMaterialColor.get();
        }

        return UrnHelper.DEFAULT_GEM_COLOR;
    }

    public static class Serializer implements RecipeSerializer<UrnBaseRecipe> {
        public static final MapCodec<UrnBaseRecipe> CODEC = RecordCodecBuilder.mapCodec(
                builder -> builder.group(
                                Codec.STRING.optionalFieldOf("group", "").forGetter(r -> r.group),
                                CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(r -> r.category),
                                ShapedRecipePattern.MAP_CODEC.forGetter(r -> r.pattern),
                                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(r -> r.result),
                                Color.CODEC.optionalFieldOf("clay_color", UrnHelper.DEFAULT_CLAY_COLOR).forGetter(r -> r.clayColor)
                        )
                        .apply(builder, UrnBaseRecipe::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, UrnBaseRecipe> STREAM_CODEC = StreamCodec.of(
                Serializer::toNetwork,
                Serializer::fromNetwork
        );

        @Override
        public MapCodec<UrnBaseRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, UrnBaseRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        public static UrnBaseRecipe fromNetwork(RegistryFriendlyByteBuf pBuffer) {
            String s = pBuffer.readUtf();
            CraftingBookCategory craftingbookcategory = pBuffer.readEnum(CraftingBookCategory.class);
            ShapedRecipePattern shapedrecipepattern = ShapedRecipePattern.STREAM_CODEC.decode(pBuffer);
            ItemStack itemstack = ItemStack.STREAM_CODEC.decode(pBuffer);
            Color clayColor = Color.read(pBuffer);
            return new UrnBaseRecipe(s, craftingbookcategory, shapedrecipepattern, itemstack, clayColor);
        }

        public static void toNetwork(RegistryFriendlyByteBuf pBuffer, UrnBaseRecipe pRecipe) {
            pBuffer.writeUtf(pRecipe.group);
            pBuffer.writeEnum(pRecipe.category);
            ShapedRecipePattern.STREAM_CODEC.encode(pBuffer, pRecipe.pattern);
            ItemStack.STREAM_CODEC.encode(pBuffer, pRecipe.result);
            pRecipe.clayColor.write(pBuffer);
        }
    }
}
