package net.silentchaos512.loginar.crafting.recipe;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.Tags;
import net.silentchaos512.lib.collection.StackList;
import net.silentchaos512.lib.util.Color;
import net.silentchaos512.lib.util.ColorBlendAlgorithm;
import net.silentchaos512.lib.util.ColorUtils;
import net.silentchaos512.loginar.block.urn.UrnHelper;
import net.silentchaos512.loginar.setup.LsDataComponents;
import net.silentchaos512.loginar.setup.LsRecipeSerializers;
import net.silentchaos512.loginar.setup.LsTags;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class UrnModificationRecipe extends CustomRecipe {
    public static final UrnModificationRecipe INSTANCE = new UrnModificationRecipe();

    @Override
    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
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
    public ItemStack assemble(CraftingInput input) {
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

    private static void applyDyes(ItemStack urn, Collection<ItemStack> dyes) {
        List<Integer> colors = new ArrayList<>();
        for (var dye : dyes) {
            getDyeColor(dye).ifPresent(dyeColor -> colors.add(dyeColor.getTextureDiffuseColor()));
        }
        int finalColor = ColorUtils.blend(ColorBlendAlgorithm.MIXBOX, colors);
        urn.set(LsDataComponents.URN_CLAY_COLOR, new Color(finalColor));
    }

    private static Optional<DyeColor> getDyeColor(ItemStack dye) {
        return Optional.ofNullable(DyeColor.getColor(dye));
    }
}
