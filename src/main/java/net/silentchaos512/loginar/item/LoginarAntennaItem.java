package net.silentchaos512.loginar.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.silentchaos512.loginar.entity.WildLoginar;
import net.silentchaos512.loginar.util.TextUtil;

public class LoginarAntennaItem extends Item {
    public LoginarAntennaItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (level instanceof ServerLevelAccessor && WildLoginar.isSpawningChunk((ServerLevelAccessor) level, player.blockPosition())) {
            player.displayClientMessage(TextUtil.translate("item", "loginar_antenna.lit"), true);
        }
        return InteractionResult.SUCCESS;
    }
}
