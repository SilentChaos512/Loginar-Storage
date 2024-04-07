package net.silentchaos512.loginar.crafting.recipe;

import net.minecraft.core.RegistryAccess;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.silentchaos512.lib.util.Color;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlock;
import net.silentchaos512.loginar.block.urn.UrnData;
import net.silentchaos512.loginar.setup.LsRecipeSerializers;

public class UrnUpgradeRecipe extends UrnBaseRecipe {
    public UrnUpgradeRecipe(String pGroup, CraftingBookCategory pCategory, ShapedRecipePattern pPattern, ItemStack pResult) {
        super(pGroup, pCategory, pPattern, pResult, new Color(UrnData.DEFAULT_CLAY_COLOR));
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return LsRecipeSerializers.URN_UPGRADE.get();
    }

    @Override
    public ItemStack assemble(CraftingContainer craftingContainer, RegistryAccess registryAccess) {
        ItemStack ret = super.assemble(craftingContainer, registryAccess);

        // Find original urn
        ItemStack original = ItemStack.EMPTY;
        for (int i = 0; i < craftingContainer.getContainerSize(); ++i) {
            ItemStack stack = craftingContainer.getItem(i);
            if (stack.getItem() instanceof BlockItem && ((BlockItem) stack.getItem()).getBlock() instanceof LoginarUrnBlock) {
                original = stack;
                break;
            }
        }

        if (original.isEmpty()) {
            LoginarMod.LOGGER.error("Urn upgrade recipe {} has no urn in the ingredients", this);
            return ItemStack.EMPTY;
        }

        // Copy NBT to new urn
        ret.setTag(original.getTag());

        return ret;
    }
}
