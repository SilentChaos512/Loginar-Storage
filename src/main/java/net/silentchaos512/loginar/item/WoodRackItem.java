package net.silentchaos512.loginar.item;

import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.silentchaos512.loginar.item.container.ContainerItem;
import net.silentchaos512.loginar.item.container.ContainerItemMenu;
import net.silentchaos512.loginar.setup.LsMenuTypes;
import net.silentchaos512.loginar.setup.LsTags;

public class WoodRackItem extends ContainerItem {
    public WoodRackItem(Properties properties) {
        super("wood_rack", properties);
    }

    @Override
    protected MenuType<? extends ContainerItemMenu> getMenuType() {
        return LsMenuTypes.WOOD_RACK.get();
    }

    @Override
    public int getInventorySize(ItemStack stack) {
        return 27;
    }

    @Override
    public boolean canStore(ItemStack stack) {
        return stack.is(LsTags.Items.WOOD_RACK_CAN_STORE);
    }

    @Override
    public boolean canPickupItems() {
        return true;
    }
}
