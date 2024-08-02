package net.silentchaos512.loginar.crafting.recipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.silentchaos512.lib.util.Color;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlock;
import net.silentchaos512.loginar.block.urn.UrnData;
import net.silentchaos512.loginar.setup.LsDataComponents;
import net.silentchaos512.loginar.setup.LsRecipeSerializers;
import net.silentchaos512.loginar.setup.UrnTypes;

import java.util.Objects;

public class UrnUpgradeRecipe extends UrnBaseRecipe {
    public UrnUpgradeRecipe(String pGroup, CraftingBookCategory pCategory, ShapedRecipePattern pPattern, ItemStack pResult) {
        super(pGroup, pCategory, pPattern, pResult, new Color(UrnData.DEFAULT_CLAY_COLOR));
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
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

        // Copy data to new urn, but with the correct new type
        ret.applyComponents(original.getComponents());
        var originalData = original.getOrDefault(LsDataComponents.URN_DATA, UrnData.getDefault(ret));
        var newData = new UrnData(
                Objects.requireNonNull(UrnTypes.fromItem(ret)),
                originalData.clayColor(),
                originalData.gemColor(),
                originalData.items(),
                originalData.upgrades()
        );
        ret.set(LsDataComponents.URN_DATA, newData);

        return ret;
    }
}
