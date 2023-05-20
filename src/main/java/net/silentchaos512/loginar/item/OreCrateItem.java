package net.silentchaos512.loginar.item;

import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.silentchaos512.loginar.item.container.ContainerItem;
import net.silentchaos512.loginar.item.container.ContainerItemMenu;
import net.silentchaos512.loginar.setup.LsMenuTypes;
import net.silentchaos512.loginar.setup.LsTags;

public class OreCrateItem extends ContainerItem {
    public OreCrateItem(Properties properties) {
        super("ore_crate", properties);
    }

    @Override
    protected MenuType<? extends ContainerItemMenu> getMenuType() {
        return LsMenuTypes.ORE_CRATE.get();
    }

    @Override
    public int getInventorySize(ItemStack stack) {
        return 27;
    }

    @Override
    public boolean canStore(ItemStack stack) {
        return stack.is(LsTags.Items.ORE_CRATE_CAN_STORE);
    }

    @Override
    public boolean canPickupItems() {
        return true;
    }
}
