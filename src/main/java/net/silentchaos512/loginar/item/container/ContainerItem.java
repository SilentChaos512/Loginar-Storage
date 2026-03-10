package net.silentchaos512.loginar.item.container;

import io.netty.buffer.Unpooled;
import net.minecraft.ChatFormatting;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Util;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.connection.ConnectionType;
import net.silentchaos512.lib.util.NameUtils;
import net.silentchaos512.loginar.setup.LsDataComponents;
import net.silentchaos512.loginar.util.TextUtil;

import java.util.function.Consumer;

public abstract class ContainerItem extends Item implements IContainerItem {
    private final Component containerName;

    public ContainerItem(String containerNameKey, Properties properties) {
        super(properties);
        this.containerName = TextUtil.translate("container", containerNameKey);
    }

    protected abstract MenuType<? extends ContainerItemMenu> getMenuType();

    public static boolean containsAnyItems(ItemStack stack) {
        var contents = stack.get(LsDataComponents.CONTAINED_ITEMS);
        if (contents != null) {
            for (int i = 0; i < contents.getSlots(); ++i) {
                if (!contents.getStackInSlot(i).isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public InteractionResult use(Level levelIn, Player playerIn, InteractionHand handIn) {
        if (!levelIn.isClientSide()) {
            playerIn.openMenu(new SimpleMenuProvider((id, playerInventory, player) -> {
                return this.getMenuType().create(id, playerInventory, new RegistryFriendlyByteBuf(Unpooled.buffer(), player.registryAccess(), ConnectionType.NEOFORGE));
            }, this.containerName));
        }
        return InteractionResult.SUCCESS;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        tooltipAdder.accept(Component.translatable(Util.makeDescriptionId("item", NameUtils.fromItem(this)) + ".desc"));
        var contents = stack.get(LsDataComponents.CONTAINED_ITEMS);
        if (contents != null) {
            tooltipItemsList(tooltipAdder, contents);
        }
    }

    private static void tooltipItemsList(Consumer<Component> tooltipAdder, ItemContainerContents contents) {
        int i = 0;
        int j = 0;

        for (ItemStack item : contents.nonEmptyItems()) {
            if (!item.isEmpty()) {
                ++j;
                if (i <= 4) {
                    ++i;
                    MutableComponent mutablecomponent = item.getHoverName().copy();
                    mutablecomponent.append(" x").append(String.valueOf(item.getCount()));
                    tooltipAdder.accept(Component.translatable("item.container.item_count", item.getHoverName(), item.getCount()));
                }
            }
        }

        if (j - i > 0) {
            tooltipAdder.accept(Component.translatable("item.container.more_items", j - i).withStyle(ChatFormatting.ITALIC));
        }
    }
}
