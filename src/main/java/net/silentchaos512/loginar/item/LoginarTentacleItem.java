package net.silentchaos512.loginar.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class LoginarTentacleItem extends Item {
    public LoginarTentacleItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack itemStack = super.finishUsingItem(stack, level, entity);

        if (!level.isClientSide) {
            entity.setRemainingFireTicks(120);
        }

        return itemStack;
    }
}
