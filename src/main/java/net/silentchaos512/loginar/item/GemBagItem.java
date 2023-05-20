package net.silentchaos512.loginar.item;

import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.silentchaos512.loginar.item.container.ContainerItem;
import net.silentchaos512.loginar.item.container.ContainerItemMenu;
import net.silentchaos512.loginar.setup.LsMenuTypes;
import net.silentchaos512.loginar.setup.LsTags;

public class GemBagItem extends ContainerItem {
    public GemBagItem(Properties properties) {
        super("gem_bag", properties);
    }

    @Override
    protected MenuType<? extends ContainerItemMenu> getMenuType() {
        return LsMenuTypes.GEM_BAG.get();
    }

    @Override
    public int getInventorySize(ItemStack stack) {
        return 27;
    }

    @Override
    public boolean canStore(ItemStack stack) {
        return stack.is(LsTags.Items.GEM_BAG_CAN_STORE);
    }

    @Override
    public boolean canPickupItems() {
        return true;
    }
}
