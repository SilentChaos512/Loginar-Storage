package net.silentchaos512.loginar.block.urn;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.silentchaos512.loginar.setup.LsItems;
import net.silentchaos512.loginar.setup.UrnTypes;

public class LoginarUrnBlockItem extends BlockItem {
    private final UrnTypes urnType;

    public LoginarUrnBlockItem(LoginarUrnBlock block, Properties properties) {
        super(block, properties);
        this.urnType = block.getType();
    }

    public UrnTypes getUrnType() {
        return this.urnType;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (UrnHelper.hasUpgrade(stack, LsItems.BACKPACK_UPGRADE)) {
            if (!level.isClientSide) {
                openContainer((ServerPlayer) player, stack);
            }
            return InteractionResultHolder.success(stack);
        }

        return InteractionResultHolder.pass(stack);
    }

    public void openContainer(ServerPlayer player, ItemStack stack) {
        player.openMenu(
                new SimpleMenuProvider(
                        (id, inv, z) -> new LoginarUrnBackpackMenu(id, inv, stack),
                        stack.getHoverName()
                ),
                buf -> ItemStack.STREAM_CODEC.encode(buf, stack)
        );
    }

    @Override
    public boolean canFitInsideContainerItems() {
        // Cannot be stored in shulker boxes
        return false;
    }
}
