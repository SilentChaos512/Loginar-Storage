package net.silentchaos512.loginar.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.silentchaos512.loginar.entity.LoginarEntity;
import net.silentchaos512.loginar.util.TextUtil;

public class LoginarAntennaItem extends Item {
    public LoginarAntennaItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (level instanceof ServerLevelAccessor && LoginarEntity.isSpawningChunk((ServerLevelAccessor) level, player.blockPosition())) {
            player.sendSystemMessage(TextUtil.translate("item", "loginar_antenna.lit"));
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}
