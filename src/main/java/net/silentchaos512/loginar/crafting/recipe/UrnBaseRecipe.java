package net.silentchaos512.loginar.crafting.recipe;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.ShapedCraftingRecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.neoforged.neoforge.common.Tags;
import net.silentchaos512.lib.crafting.recipe.ExtendedShapedRecipe;
import net.silentchaos512.lib.util.Color;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlock;
import net.silentchaos512.loginar.block.urn.UrnHelper;
import net.silentchaos512.loginar.compat.SgearCompat;
import net.silentchaos512.loginar.setup.LsRecipeSerializers;
import net.silentchaos512.loginar.setup.LsTags;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UrnBaseRecipe extends ExtendedShapedRecipe {
    public static final MapCodec<UrnBaseRecipe> CODEC = RecordCodecBuilder.mapCodec(
            i -> i.group(
                    CommonInfo.MAP_CODEC.forGetter(r -> r.commonInfo),
                    CraftingBookInfo.MAP_CODEC.forGetter(r -> r.bookInfo),
                    ShapedRecipePattern.MAP_CODEC.forGetter(r -> r.pattern),
                    ItemStackTemplate.CODEC.fieldOf("result").forGetter(r -> r.result),
                    Color.CODEC.optionalFieldOf("clay_color", UrnHelper.DEFAULT_CLAY_COLOR).forGetter(r -> r.clayColor)
            ).apply(i, UrnBaseRecipe::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, UrnBaseRecipe> STREAM_CODEC = StreamCodec.composite(
            Recipe.CommonInfo.STREAM_CODEC,
            r -> r.commonInfo,
            CraftingRecipe.CraftingBookInfo.STREAM_CODEC,
            r -> r.bookInfo,
            ShapedRecipePattern.STREAM_CODEC,
            r -> r.pattern,
            ItemStackTemplate.STREAM_CODEC,
            r -> r.result,
            Color.STREAM_CODEC,
            r -> r.clayColor,
            UrnBaseRecipe::new
    );
    public static final RecipeSerializer<UrnBaseRecipe> SERIALIZER = new RecipeSerializer<>(CODEC, STREAM_CODEC);

    // TODO: Must be a better way to handle this... A registry or something? Or a data map?
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

    public UrnBaseRecipe(CommonInfo commonInfo, CraftingBookInfo bookInfo, ShapedRecipePattern pattern, ItemStackTemplate result, Color clayColor) {
        super(commonInfo, bookInfo, pattern, result);
        this.clayColor = clayColor;
    }

    @Override
    public RecipeSerializer<? extends UrnBaseRecipe> getSerializer() {
        return LsRecipeSerializers.URN.get();
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        if (this.result.item().value() instanceof BlockItem blockItem && blockItem.getBlock() instanceof LoginarUrnBlock block) {
            Color gemColor = getGemColor(findGem(input));
            return block.makeStack(this.clayColor, gemColor);
        } else {
            LoginarMod.LOGGER.error("Result of urn base recipe {} is not an urn", this);
            return ItemStack.EMPTY;
        }
    }

    @Override
    public List<RecipeDisplay> display() {
        ItemStack displayResult = this.result.create();
        if (displayResult.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof LoginarUrnBlock block) {
            displayResult = block.makeStack(this.clayColor, null);
        }
        return List.of(
                new ShapedCraftingRecipeDisplay(
                        this.pattern.width(),
                        this.pattern.height(),
                        this.pattern.ingredients().stream().map(p_380107_ -> p_380107_.map(Ingredient::display).orElse(SlotDisplay.Empty.INSTANCE)).toList(),
                        new SlotDisplay.ItemStackSlotDisplay(ItemStackTemplate.fromNonEmptyStack(displayResult)),
                        new SlotDisplay.ItemSlotDisplay(Items.CRAFTING_TABLE)
                )
        );
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
}
