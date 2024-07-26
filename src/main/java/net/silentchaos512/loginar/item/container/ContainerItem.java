package net.silentchaos512.loginar.item.container;

import io.netty.buffer.Unpooled;
import net.minecraft.Util;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.connection.ConnectionType;
import net.silentchaos512.lib.util.NameUtils;
import net.silentchaos512.loginar.util.TextUtil;

import java.util.List;

public abstract class ContainerItem extends Item implements IContainerItem {
    private final Component containerName;

    public ContainerItem(String containerNameKey, Properties properties) {
        super(properties);
        this.containerName = TextUtil.translate("container", containerNameKey);
    }

    protected abstract MenuType<? extends ContainerItemMenu> getMenuType();

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable(Util.makeDescriptionId("item", NameUtils.fromItem(this)) + ".desc"));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        if (!worldIn.isClientSide) {
            playerIn.openMenu(new SimpleMenuProvider((id, playerInventory, player) -> {
                return this.getMenuType().create(id, playerInventory, new RegistryFriendlyByteBuf(Unpooled.buffer(), player.registryAccess(), ConnectionType.NEOFORGE));
            }, this.containerName));
        }
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, playerIn.getItemInHand(handIn));
    }
}
