package net.silentchaos512.loginar.crafting.recipe;

import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.block.urn.LoginarUrnBlock;
import net.silentchaos512.loginar.setup.LsRecipeSerializers;

public class UrnUpgradeRecipe extends UrnBaseRecipe {
    public UrnUpgradeRecipe(ShapedRecipe recipe) {
        super(recipe);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return LsRecipeSerializers.URN_UPGRADE.get();
    }

    @Override
    public boolean matches(CraftingContainer craftingContainer, Level level) {
        return this.getBaseRecipe().matches(craftingContainer, level);
    }

    @Override
    public ItemStack assemble(CraftingContainer craftingContainer) {
        ItemStack ret = this.getBaseRecipe().assemble(craftingContainer);

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
            LoginarMod.LOGGER.error("Urn upgrade recipe {} has no urn in the ingredients", this.getId());
            return ItemStack.EMPTY;
        }

        // Copy NBT to new urn
        ret.setTag(original.getTag());

        return ret;
    }
}
