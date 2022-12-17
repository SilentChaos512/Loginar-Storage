package net.silentchaos512.loginar.block.urn;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.network.NetworkHooks;
import net.silentchaos512.loginar.setup.LsItems;
import net.silentchaos512.loginar.util.TextUtil;

public class LoginarUrnBlockItem extends BlockItem {
    public LoginarUrnBlockItem(Block block, Properties properties) {
        super(block, properties);
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

    private void openContainer(ServerPlayer player, ItemStack stack) {
        NetworkHooks.openScreen(player,
                new SimpleMenuProvider(
                        (id, inv, z) -> new LoginarUrnBackpackMenu(id, inv, stack),
                        TextUtil.translate("container", "loginar_urn")
                ),
                buf -> buf.writeItem(stack)
        );
    }
}
