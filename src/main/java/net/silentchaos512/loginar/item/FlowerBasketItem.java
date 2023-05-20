package net.silentchaos512.loginar.item;

import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.silentchaos512.loginar.item.container.ContainerItem;
import net.silentchaos512.loginar.item.container.ContainerItemMenu;
import net.silentchaos512.loginar.setup.LsMenuTypes;
import net.silentchaos512.loginar.setup.LsTags;

public class FlowerBasketItem extends ContainerItem {
    public FlowerBasketItem(Properties properties) {
        super("flower_basket", properties);
    }

    @Override
    protected MenuType<? extends ContainerItemMenu> getMenuType() {
        return LsMenuTypes.FLOWER_BASKET.get();
    }

    @Override
    public int getInventorySize(ItemStack stack) {
        return 27;
    }

    @Override
    public boolean canStore(ItemStack stack) {
        return stack.is(LsTags.Items.FLOWER_BASKET_CAN_STORE);
    }

    @Override
    public boolean canPickupItems() {
        return true;
    }
}
