package net.silentchaos512.loginar.crafting.recipe;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlock;
import net.silentchaos512.loginar.block.urn.UrnHelper;
import net.silentchaos512.loginar.setup.LsRecipeSerializers;

public class UrnUpgradeRecipe extends UrnBaseRecipe {
    public UrnUpgradeRecipe(CommonInfo commonInfo, CraftingBookInfo bookInfo, ShapedRecipePattern pattern, ItemStackTemplate result) {
        super(commonInfo, bookInfo, pattern, result, UrnHelper.DEFAULT_CLAY_COLOR);
    }

    @Override
    public RecipeSerializer<? extends UrnUpgradeRecipe> getSerializer() {
        return LsRecipeSerializers.URN_UPGRADE.get();
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        ItemStack ret = super.assemble(input);

        // Find original urn
        ItemStack original = ItemStack.EMPTY;
        for (int i = 0; i < input.size(); ++i) {
            ItemStack stack = input.getItem(i);
            if (stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof LoginarUrnBlock) {
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
}
