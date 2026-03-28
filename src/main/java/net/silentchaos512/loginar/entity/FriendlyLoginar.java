package net.silentchaos512.loginar.entity;

import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathType;
import net.silentchaos512.loginar.entity.ai.goal.LoginarFloatInWaterGoal;
import net.silentchaos512.loginar.entity.ai.goal.LoginarFollowOwnerGoal;
import net.silentchaos512.loginar.setup.LsEntityTypes;
import net.silentchaos512.loginar.setup.LsSounds;
import net.silentchaos512.loginar.setup.LsTags;
import org.jetbrains.annotations.Nullable;

public class FriendlyLoginar extends TamableAnimal implements Loginar {
    public FriendlyLoginar(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setPathfindingMalus(PathType.WATER, -1.0F);
        this.setPathfindingMalus(PathType.LAVA, 8.0F);
        this.setPathfindingMalus(PathType.FIRE_IN_NEIGHBOR, 0.0F);
        this.setPathfindingMalus(PathType.FIRE, 0.0F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new LoginarFloatInWaterGoal(this));
        this.goalSelector.addGoal(1, new TamableAnimalPanicGoal(1.5, DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(6, new LoginarFollowOwnerGoal(this, 1.0, 10.0f, 2.0f));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this).setAlertOthers());
    }

    public static AttributeSupplier createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.ATTACK_DAMAGE, 6.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .build();
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(LsTags.Items.LOGINAR_FOOD);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return LsEntityTypes.FRIENDLY_LOGINAR.get().create(pLevel, EntitySpawnReason.BREEDING);
    }

    @Override
    protected void applyTamingSideEffects() {
        super.applyTamingSideEffects();
        if (this.isTame()) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(40.0);
        } else {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0);
        }
    }

    @Override
    public boolean canStandOnFluid(FluidState fluid) {
        // TODO: Add water walking enchantment for loginar boots
        return fluid.is(FluidTags.LAVA);
    }

    @Override
    public boolean isSensitiveToWater() {
        // TODO: May need to manually handle this by overriding aiStep
        //  Loginars with body armor are protected from rain, but not from being submersed in water
        return true;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return LsSounds.LOGINAR_IDLE.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return LsSounds.LOGINAR_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return LsSounds.LOGINAR_HURT.get();
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount) {
        if (this.isInvulnerableTo(level, damageSource)) {
            return false;
        } else {
            this.setOrderedToSit(false);
            return super.hurtServer(level, damageSource, amount);
        }
    }

    @Override
    public boolean canUseSlot(EquipmentSlot pSlot) {
        return true;
    }

    @Override
    protected void actuallyHurt(ServerLevel level, DamageSource damageSource, float amount) {
        if (!this.canArmorAbsorb(damageSource)) {
            super.actuallyHurt(level, damageSource, amount);
        } else {
            // TODO: Damage armor instead, or reduce damage with armor?
            super.actuallyHurt(level, damageSource, amount); // TODO: Remove this
        }
    }

    private boolean canArmorAbsorb(DamageSource pDamageSource) {
        return this.hasArmor() && !pDamageSource.is(LsTags.DamageTypes.BYPASSES_LOGINAR_ARMOR);
    }

    private boolean hasArmor() {
        return false; // TODO
    }

    @Override
    protected void hurtArmor(DamageSource pDamageSource, float pDamageAmount) {
        this.doHurtEquipment(pDamageSource, pDamageAmount, EquipmentSlot.BODY, EquipmentSlot.FEET);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack heldItem = player.getItemInHand(hand);
        if (!this.level().isClientSide() || this.isBaby() && this.isFood(heldItem)) {
            if (this.isTame()) {
                if (this.isFood(heldItem) && this.getHealth() < this.getMaxHealth()) {
                    this.heal(4.0f);
                    heldItem.consume(1, player);
                    this.gameEvent(GameEvent.EAT);
                    return InteractionResult.SUCCESS;
                } else {
                    if (heldItem.has(DataComponents.DYE)) {
                        DyeColor dyeColor = heldItem.get(DataComponents.DYE);
                        // TODO: Set antenna light color
                        return InteractionResult.SUCCESS;
                    }
                    if (heldItem.is(LsTags.Items.LOGINAR_ARMOR)) {
                        // TODO: Equip armor
                        return InteractionResult.SUCCESS;
                    } else {
                        InteractionResult result = super.mobInteract(player, hand);
                        if (!result.consumesAction() && this.isOwnedBy(player)) {
                            this.setOrderedToSit(!this.isOrderedToSit());
                            this.jumping = false;
                            this.navigation.stop();
                            this.setTarget(null);
                            return InteractionResult.SUCCESS.withoutItem();
                        } else {
                            return result;
                        }
                    }
                }
            } else if (this.isFood(heldItem)) {
                heldItem.consume(1, player);
                this.tryToTame(player);
                return InteractionResult.SUCCESS;
            } else {
                return super.mobInteract(player, hand);
            }
        } else {
            boolean flag = this.isOwnedBy(player) || this.isTame() || this.isFood(heldItem) && !this.isTame();
            return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
        }
    }

    private void tryToTame(Player player) {
        if (this.random.nextInt(3) == 0 && !net.neoforged.neoforge.event.EventHooks.onAnimalTame(this, player)) {
            this.tame(player);
            this.navigation.stop();
            this.setTarget(null);
            this.setOrderedToSit(true);
            this.level().broadcastEntityEvent(this, (byte) 7);
        } else {
            this.level().broadcastEntityEvent(this, (byte) 6);
        }
    }

    @Override
    public boolean wantsToAttack(LivingEntity pTarget, LivingEntity pOwner) {
        if (pTarget instanceof Creeper || pTarget instanceof Ghast || pTarget instanceof ArmorStand) {
            return false;
        } else if (pTarget instanceof Wolf wolf) {
            return !wolf.isTame() || wolf.getOwner() != pOwner;
        } else {
            if (pTarget instanceof Player player && pOwner instanceof Player player1 && !player1.canHarmPlayer(player)) {
                return false;
            }

            if (pTarget instanceof AbstractHorse abstracthorse && abstracthorse.isTamed()) {
                return false;
            }

            if (pTarget instanceof TamableAnimal tamableanimal && tamableanimal.isTame()) {
                return false;
            }

            return true;
        }
    }
}
