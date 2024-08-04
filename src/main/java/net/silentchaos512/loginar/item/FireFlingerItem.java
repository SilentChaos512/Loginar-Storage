package net.silentchaos512.loginar.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class FireFlingerItem extends Item {
    public FireFlingerItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        var direction = player.getLookAngle();
        var smallFireball = new SmallFireball(
                level,
                player,
                new Vec3(
                        direction.x + 0.1 * player.getRandom().nextDouble(),
                        direction.y + 0.1 * player.getRandom().nextDouble(),
                        direction.z + 0.1 * player.getRandom().nextDouble()
                )
        );
        var position = new Vec3(
                player.getX() + direction.x,
                player.getEyeY() + direction.y - 0.5,
                player.getZ() + direction.z
        );
        smallFireball.setPos(position);
        level.addFreshEntity(smallFireball);

        player.getCooldowns().addCooldown(this, 5);
        var stack = player.getItemInHand(usedHand);
        stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(usedHand));

        return InteractionResultHolder.consume(stack);
    }
}
