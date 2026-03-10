package net.silentchaos512.loginar.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.throwableitemprojectile.AbstractThrownPotion;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownLingeringPotion;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownSplashPotion;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.silentchaos512.loginar.item.container.ContainerItem;
import net.silentchaos512.loginar.item.container.ContainerItemMenu;
import net.silentchaos512.loginar.setup.LsDataComponents;
import net.silentchaos512.loginar.setup.LsMenuTypes;

public class PotionPouchItem extends ContainerItem {
    public static final int USE_COOLDOWN_TIME = 10;

    public PotionPouchItem(Properties properties) {
        super("potion_pouch", properties);
    }

    @Override
    protected MenuType<? extends ContainerItemMenu> getMenuType() {
        return LsMenuTypes.POTION_POUCH.get();
    }

    @Override
    public int getInventorySize(ItemStack stack) {
        return 9;
    }

    @Override
    public boolean canStore(ItemStack stack) {
        return stack.getItem() instanceof PotionItem;
    }

    @Override
    public boolean canPickupItems() {
        return false;
    }

    public ItemStack getNextPotion(ItemStack stack) {
        int slot = getNextPotionSlot(stack);
        var containedItems = stack.get(LsDataComponents.CONTAINED_ITEMS);
        if (containedItems != null && slot >= 0 && slot < containedItems.getSlots()) {
            return containedItems.getStackInSlot(slot);
        }
        return ItemStack.EMPTY;
    }

    public int getNextPotionSlot(ItemStack stack) {
        var containedItems = stack.get(LsDataComponents.CONTAINED_ITEMS);
        if (containedItems != null) {
            for (int i = 0; i < containedItems.getSlots(); ++i) {
                ItemStack stackInSlot = containedItems.getStackInSlot(i);
                if (!stackInSlot.isEmpty()) {
                    return i;
                }
            }
        }
        return -1;
    }

    public ItemStack consumeNextPotion(ItemStack stack, LivingEntity livingEntity) {
        var containedItems = stack.get(LsDataComponents.CONTAINED_ITEMS);
        var slot = getNextPotionSlot(stack);
        if (containedItems != null && slot >= 0) {
            var items = NonNullList.withSize(containedItems.getSlots(), ItemStack.EMPTY);
            containedItems.copyInto(items);
            ItemStack potionStack = items.get(slot);
            potionStack.consume(1, livingEntity);
            items.set(slot, potionStack);
            stack.set(LsDataComponents.CONTAINED_ITEMS, ItemContainerContents.fromItems(items));
            return potionStack;
        }
        return stack;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (player.isCrouching()) {
            return super.use(level, player, hand);
        }

        ItemStack stack = player.getItemInHand(hand);
        ItemStack potion = getNextPotion(stack);

        if (potion.isEmpty()) {
            return InteractionResult.PASS;
        } else if (potion.getItem() instanceof ThrowablePotionItem) {
            return throwPotion(level, player, potion, stack);
        } else {
            // Drink potion
            return ItemUtils.startUsingInstantly(level, player, hand);
        }
    }

    private AbstractThrownPotion createPotion(Level level, Player player, ItemStack potion) {
        if (potion.getItem() instanceof SplashPotionItem) {
            return new ThrownSplashPotion(level, player, potion);
        } else if (potion.getItem() instanceof LingeringPotionItem) {
            return new ThrownLingeringPotion(level, player, potion);
        } else {
            throw new IllegalStateException("Unknown thrown potion type: " + potion);
        }
    }

    private InteractionResult throwPotion(Level level, Player player, ItemStack potion, ItemStack stack) {
        if (!level.isClientSide()) {
            AbstractThrownPotion thrownPotion = createPotion(level, player, potion);
            thrownPotion.setItem(potion);
            thrownPotion.shootFromRotation(player, player.getXRot(), player.getYRot(), -20.0F, 0.5F, 1.0F);
            level.addFreshEntity(thrownPotion);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        potion.consume(1, player);
        final var containedItems = stack.get(LsDataComponents.CONTAINED_ITEMS);
        if (containedItems != null) {
            final int slot = getNextPotionSlot(stack);
            NonNullList<ItemStack> items = NonNullList.withSize(containedItems.getSlots(), ItemStack.EMPTY);
            containedItems.copyInto(items);
            items.get(slot).consume(1, player);
            stack.set(LsDataComponents.CONTAINED_ITEMS, ItemContainerContents.fromItems(items));

        }

        player.getCooldowns().addCooldown(stack, USE_COOLDOWN_TIME);

        return InteractionResult.SUCCESS;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        var potionStack = getNextPotion(stack);
        Player player = livingEntity instanceof Player ? (Player) livingEntity : null;
        if (player instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, potionStack);
        }

        if (level instanceof ServerLevel serverLevel) {
            PotionContents potioncontents = potionStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
            potioncontents.forEachEffect(mobEffectInstance -> {
                if (mobEffectInstance.getEffect().value().isInstantenous()) {
                    mobEffectInstance.getEffect().value().applyInstantenousEffect(serverLevel, player, player, livingEntity, mobEffectInstance.getAmplifier(), 1.0);
                } else {
                    livingEntity.addEffect(mobEffectInstance);
                }
            }, 1.0f);
        }

        ItemStack consumedPotion = potionStack;
        if (player != null) {
            player.awardStat(Stats.ITEM_USED.get(this));
            consumedPotion = consumeNextPotion(stack, livingEntity);
        }

        if (player != null && !player.hasInfiniteMaterials()) {
            player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE));
            // Add a cooldown so that a potion can't be accidentally thrown if the next is a throwing potion
            player.getCooldowns().addCooldown(stack, USE_COOLDOWN_TIME);
        }

        livingEntity.gameEvent(GameEvent.DRINK);
        return stack;
    }

    @Override
    public int getUseDuration(ItemStack pStack, LivingEntity p_344979_) {
        return 32;
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack pStack) {
        return ItemUseAnimation.DRINK;
    }

    @Override
    public Component getName(ItemStack pStack) {
        // TODO: Include potion name
        return super.getName(pStack);
    }
}
