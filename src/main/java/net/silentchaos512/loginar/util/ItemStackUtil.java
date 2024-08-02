package net.silentchaos512.loginar.util;

import net.minecraft.world.item.crafting.CraftingInput;
import net.silentchaos512.lib.collection.StackList;

public class ItemStackUtil {
    /**
     * TODO: Remove later once Silent Lib updates
     * @param input
     * @return
     */
    @Deprecated
    public static StackList stackListFrom(CraftingInput input) {
        StackList newList = StackList.of();
        for (int i = 0; i < input.size(); ++i) {
            newList.add(input.getItem(i));
        }
        return newList;
    }
}
