package net.silentchaos512.loginar.crafting.recipe;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;
import net.silentchaos512.lib.collection.StackList;
import net.silentchaos512.loginar.block.urn.UrnData;
import net.silentchaos512.loginar.block.urn.UrnHelper;
import net.silentchaos512.loginar.setup.LsTags;

import java.util.Collection;
import java.util.Optional;

public class UrnModifcationRecipe extends CustomRecipe {
    public UrnModifcationRecipe(ResourceLocation id) {
        super(id);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return null;
    }

    @Override
    public boolean matches(CraftingContainer inv, Level worldIn) {
        StackList list = StackList.from(inv);
        ItemStack urn = list.uniqueMatch(UrnModifcationRecipe::isUrn);
        Collection<ItemStack> mods = list.allMatches(UrnModifcationRecipe::isModifierItem);
        Collection<ItemStack> dyes = list.allMatches(s -> getDyeColor(s).isPresent());

        // For upgrade items, make sure the urn doesn't have it already
        for (ItemStack mod : mods) {
            /*if (mod.getItem() instanceof IUrnUpgradeItem) {
                IUrnUpgradeItem upgradeItem = (IUrnUpgradeItem) mod.getItem();

                List<UrnUpgrade> currentUpgrades = UrnUpgrade.ListHelper.load(urn);
                if (UrnUpgrade.ListHelper.contains(currentUpgrades, upgradeItem.getSerializer())) {
                    return false;
                }
            }*/
        }

        return !urn.isEmpty() && (list.size() == 1 || !mods.isEmpty() || !dyes.isEmpty());
    }

    @Override
    public ItemStack assemble(CraftingContainer inv) {
        StackList list = StackList.from(inv);
        ItemStack urn = list.uniqueMatch(UrnModifcationRecipe::isUrn).copy();
        Collection<ItemStack> mods = list.allMatches(UrnModifcationRecipe::isModifierItem);
        Collection<ItemStack> dyes = list.allMatches(s -> getDyeColor(s).isPresent());

        // urn is a copy, so modify that directly
        if (mods.isEmpty() && dyes.isEmpty()) {
            // No modifier items, toggle between lidded and lidless version
            //UrnHelper.toggleHasLid(urn);
        } else {
            mods.forEach(mod -> applyModifierItem(urn, mod));
            applyDyes(urn, dyes);
        }

        return urn;
    }

    private static boolean isUrn(ItemStack stack) {
        return stack.is(LsTags.Items.URNS);
    }

    private static boolean isModifierItem(ItemStack stack) {
        Item item = stack.getItem();
        return stack.is(Tags.Items.GEMS); // TODO: Upgrade items
    }

    private static void applyModifierItem(ItemStack urn, ItemStack mod) {
        if (mod.is(Tags.Items.GEMS)) {
            int color = UrnBaseRecipe.getGemColor(mod);
            UrnHelper.setGemColor(urn, color);
        }
        // TODO: Upgrade items
    }

    // Largely copied from RecipesArmorDyes
    private static void applyDyes(ItemStack urn, Collection<ItemStack> dyes) {
        int[] componentSums = new int[3];
        int maxColorSum = 0;
        int colorCount = 0;

        int clayColor = UrnHelper.getClayColor(urn);
        if (clayColor != UrnData.DEFAULT_CLAY_COLOR) {
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
            float[] componentValues = getDyeColor(dye)
                    .orElse(DyeColor.WHITE)
                    .getTextureDiffuseColors();
            int r = (int) (componentValues[0] * 255.0F);
            int g = (int) (componentValues[1] * 255.0F);
            int b = (int) (componentValues[2] * 255.0F);
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
            UrnHelper.setClayColor(urn, finalColor);
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
