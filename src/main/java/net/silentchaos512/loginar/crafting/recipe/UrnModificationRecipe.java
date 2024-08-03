package net.silentchaos512.loginar.crafting.recipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.Tags;
import net.silentchaos512.lib.collection.StackList;
import net.silentchaos512.lib.util.Color;
import net.silentchaos512.loginar.block.urn.UrnHelper;
import net.silentchaos512.loginar.setup.LsDataComponents;
import net.silentchaos512.loginar.setup.LsRecipeSerializers;
import net.silentchaos512.loginar.setup.LsTags;

import java.util.Collection;
import java.util.Optional;

public class UrnModificationRecipe extends CustomRecipe {
    public UrnModificationRecipe(CraftingBookCategory pCategory) {
        super(pCategory);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return LsRecipeSerializers.URN_MODIFICATION.get();
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        StackList list = StackList.from(input);
        ItemStack urn = list.uniqueMatch(UrnModificationRecipe::isUrn);
        Collection<ItemStack> mods = list.allMatches(UrnModificationRecipe::isModifierItem);
        Collection<ItemStack> dyes = list.allMatches(s -> getDyeColor(s).isPresent());

        // For upgrade items, make sure the urn doesn't have it already and has free upgrade slots
        for (ItemStack mod : mods) {
            if (UrnHelper.isUpgrade(mod) && (UrnHelper.hasUpgrade(urn, mod.getItem()) || UrnHelper.getUpgradeCount(urn) >= UrnHelper.getMaxUpgradeCount(urn))) {
                return false;
            }
        }

        int ingredientCount = mods.size() + dyes.size() + 1;
        return !urn.isEmpty() && list.size() == ingredientCount;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        StackList list = StackList.from(input);
        ItemStack urn = list.uniqueMatch(UrnModificationRecipe::isUrn).copy();
        Collection<ItemStack> mods = list.allMatches(UrnModificationRecipe::isModifierItem);
        Collection<ItemStack> dyes = list.allMatches(s -> getDyeColor(s).isPresent());

        // urn is a copy, so modify that directly
        if (mods.isEmpty() && dyes.isEmpty()) {
            // No modifier items, toggle between lidded and lidless version
            //UrnHelper.toggleHasLid(urn);
        } else {
            for (ItemStack mod : mods) {
                if (!applyModifierItem(urn, mod)) {
                    return ItemStack.EMPTY;
                }
            }
            applyDyes(urn, dyes);
        }

        return urn;
    }

    private static boolean isUrn(ItemStack stack) {
        return stack.is(LsTags.Items.URNS);
    }

    private static boolean isModifierItem(ItemStack stack) {
        return stack.is(Tags.Items.GEMS) || UrnHelper.isUpgrade(stack);
    }

    private static boolean applyModifierItem(ItemStack urn, ItemStack mod) {
        if (mod.is(Tags.Items.GEMS)) {
            UrnHelper.setGemColor(urn, UrnBaseRecipe.getGemColor(mod));
            return true;
        }
        if (UrnHelper.isUpgrade(mod)) {
            return UrnHelper.tryAddUpgrade(urn, mod);
        }
        return false;
    }

    // Largely copied from RecipesArmorDyes
    private static void applyDyes(ItemStack urn, Collection<ItemStack> dyes) {
        int[] componentSums = new int[3];
        int maxColorSum = 0;
        int colorCount = 0;

        int clayColor = urn.getOrDefault(LsDataComponents.URN_CLAY_COLOR, UrnHelper.DEFAULT_CLAY_COLOR).getColor();
        if (clayColor != UrnHelper.DEFAULT_CLAY_COLOR.getColor()) {
            float r = (float) (clayColor >> 16 & 255) / 255.0F;
            float g = (float) (clayColor >> 8 & 255) / 255.0F;
            float b = (float) (clayColor & 255) / 255.0F;
            maxColorSum = (int) ((float) maxColorSum + Math.max(r, Math.max(g, b)) * 255.0F);
            componentSums[0] = (int) ((float) componentSums[0] + r * 255.0F);
            componentSums[1] = (int) ((float) componentSums[1] + g * 255.0F);
            componentSums[2] = (int) ((float) componentSums[2] + b * 255.0F);
            ++colorCount;
        }

        for (ItemStack dye : dyes) {
            int textureDiffuseColor = getDyeColor(dye)
                    .orElse(DyeColor.WHITE)
                    .getTextureDiffuseColor();
            int r = (textureDiffuseColor >> 16) & 0xFF;
            int g = (textureDiffuseColor >> 8) & 0xFF;
            int b = textureDiffuseColor & 0xFF;
            maxColorSum += Math.max(r, Math.max(g, b));
            componentSums[0] += r;
            componentSums[1] += g;
            componentSums[2] += b;
            ++colorCount;
        }

        if (colorCount > 0) {
            int r = componentSums[0] / colorCount;
            int g = componentSums[1] / colorCount;
            int b = componentSums[2] / colorCount;
            float maxAverage = (float) maxColorSum / (float) colorCount;
            float max = (float) Math.max(r, Math.max(g, b));
            r = (int) ((float) r * maxAverage / max);
            g = (int) ((float) g * maxAverage / max);
            b = (int) ((float) b * maxAverage / max);
            int finalColor = (r << 8) + g;
            finalColor = (finalColor << 8) + b;

            urn.set(LsDataComponents.URN_CLAY_COLOR, new Color(finalColor));
        }
    }

    private static Optional<DyeColor> getDyeColor(ItemStack dye) {
        return Optional.ofNullable(DyeColor.getColor(dye));
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }
}
