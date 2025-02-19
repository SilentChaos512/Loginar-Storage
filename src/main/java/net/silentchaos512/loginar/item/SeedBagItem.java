package net.silentchaos512.loginar.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.neoforged.neoforge.items.ComponentItemHandler;
import net.neoforged.neoforge.items.IItemHandler;
import net.silentchaos512.lib.item.FakeItemUseContext;
import net.silentchaos512.loginar.item.container.ContainerItem;
import net.silentchaos512.loginar.item.container.ContainerItemMenu;
import net.silentchaos512.loginar.setup.LsMenuTypes;
import net.silentchaos512.loginar.setup.LsTags;

import javax.annotation.Nullable;

public class SeedBagItem extends ContainerItem {
    public SeedBagItem(Properties properties) {
        super("seed_bag", properties);
    }

    @Override
    protected MenuType<? extends ContainerItemMenu> getMenuType() {
        return LsMenuTypes.SEED_BAG.get();
    }

    @Override
    public int getInventorySize(ItemStack stack) {
        return 27;
    }

    @Override
    public boolean canStore(ItemStack stack) {
        return stack.is(LsTags.Items.SEED_BAG_CAN_STORE);
    }

    @Override
    public boolean canPickupItems() {
        return true;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        BlockItem seedItem = getSeedToPlant(stack);
        if (seedItem == null) {
            return InteractionResult.PASS;
        }

        Player player = context.getPlayer();
        boolean consumeItem = player == null || !player.getAbilities().instabuild;

        ItemStack fakeBlockStack = new ItemStack(seedItem);
        InteractionResult result = fakeBlockStack.useOn(new FakeItemUseContext(context, fakeBlockStack));

        if (result.consumesAction() && consumeItem) {
            consumeSeed(stack);
        }

        return result;
    }

    @Nullable
    private BlockItem getSeedToPlant(ItemStack stack) {
        IItemHandler inventory = getInventory(stack);
        for (int i = 0; i < inventory.getSlots(); ++i) {
            ItemStack itemInBag = inventory.getStackInSlot(i);
            if (itemInBag.getItem() instanceof BlockItem blockItem) {
                return blockItem;
            }
        }
        return null;
    }

    private void consumeSeed(ItemStack stack) {
        ComponentItemHandler inventory = getInventory(stack);
        for (int i = 0; i < inventory.getSlots(); ++i) {
            ItemStack itemInBag = inventory.getStackInSlot(i);
            if (itemInBag.getItem() instanceof BlockItem) {
                ItemStack copy = itemInBag.copy();
                copy.shrink(1);
                inventory.setStackInSlot(i, copy);
                return;
            }
        }
    }
}
