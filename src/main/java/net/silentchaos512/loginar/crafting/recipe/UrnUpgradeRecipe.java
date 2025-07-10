package net.silentchaos512.loginar.crafting.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlock;
import net.silentchaos512.loginar.block.urn.UrnHelper;
import net.silentchaos512.loginar.setup.LsRecipeSerializers;

public class UrnUpgradeRecipe extends UrnBaseRecipe {
    public UrnUpgradeRecipe(String pGroup, CraftingBookCategory pCategory, ShapedRecipePattern pPattern, ItemStack pResult) {
        super(pGroup, pCategory, pPattern, pResult, UrnHelper.DEFAULT_CLAY_COLOR);
    }

    @Override
    public RecipeSerializer<? extends ShapedRecipe> getSerializer() {
        return LsRecipeSerializers.URN_UPGRADE.get();
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registryAccess) {
        ItemStack ret = super.assemble(input, registryAccess);

        // Find original urn
        ItemStack original = ItemStack.EMPTY;
        for (int i = 0; i < input.size(); ++i) {
            ItemStack stack = input.getItem(i);
            if (stack.getItem() instanceof BlockItem && ((BlockItem) stack.getItem()).getBlock() instanceof LoginarUrnBlock) {
                original = stack;
                break;
            }
        }

        if (original.isEmpty()) {
            LoginarMod.LOGGER.error("Urn upgrade recipe {} has no urn in the ingredients", this);
            return ItemStack.EMPTY;
        }

        // Copy data to new urn
        ret.applyComponents(original.getComponents());

        return ret;
    }

    public static class Serializer implements RecipeSerializer<UrnUpgradeRecipe> {
        public static final MapCodec<UrnUpgradeRecipe> CODEC = RecordCodecBuilder.mapCodec(
                builder -> builder.group(
                                Codec.STRING.optionalFieldOf("group", "").forGetter(r -> r.group),
                                CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(r -> r.category),
                                ShapedRecipePattern.MAP_CODEC.forGetter(r -> r.pattern),
                                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(r -> r.result)
                        )
                        .apply(builder, UrnUpgradeRecipe::new)
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, UrnUpgradeRecipe> STREAM_CODEC = StreamCodec.of(
                UrnUpgradeRecipe.Serializer::toNetwork,
                UrnUpgradeRecipe.Serializer::fromNetwork
        );

        @Override
        public MapCodec<UrnUpgradeRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, UrnUpgradeRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        public static UrnUpgradeRecipe fromNetwork(RegistryFriendlyByteBuf pBuffer) {
            String group = pBuffer.readUtf();
            CraftingBookCategory category = pBuffer.readEnum(CraftingBookCategory.class);
            ShapedRecipePattern pattern = ShapedRecipePattern.STREAM_CODEC.decode(pBuffer);
            ItemStack result = ItemStack.STREAM_CODEC.decode(pBuffer);
            return new UrnUpgradeRecipe(group, category, pattern, result);
        }

        public static void toNetwork(RegistryFriendlyByteBuf pBuffer, UrnUpgradeRecipe pRecipe) {
            pBuffer.writeUtf(pRecipe.group);
            pBuffer.writeEnum(pRecipe.category);
            ShapedRecipePattern.STREAM_CODEC.encode(pBuffer, pRecipe.pattern);
            ItemStack.STREAM_CODEC.encode(pBuffer, pRecipe.result);
        }
    }
}
