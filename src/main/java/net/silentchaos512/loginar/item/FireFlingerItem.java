package net.silentchaos512.loginar.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.hurtingprojectile.SmallFireball;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class FireFlingerItem extends Item {
    public FireFlingerItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand usedHand) {
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

        var stack = player.getItemInHand(usedHand);
        player.getCooldowns().addCooldown(stack, 5);
        stack.hurtAndBreak(1, player, usedHand);

        return InteractionResult.CONSUME;
    }
}
