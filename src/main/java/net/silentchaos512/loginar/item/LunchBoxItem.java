package net.silentchaos512.loginar.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;
import net.silentchaos512.loginar.item.container.ContainerItem;
import net.silentchaos512.loginar.item.container.ContainerItemMenu;
import net.silentchaos512.loginar.setup.LsMenuTypes;
import org.jetbrains.annotations.Nullable;

public class LunchBoxItem extends ContainerItem {
    public LunchBoxItem(Properties properties) {
        super("lunch_box", properties);
    }

    @Override
    protected MenuType<? extends ContainerItemMenu> getMenuType() {
        return LsMenuTypes.LUNCH_BOX.get();
    }

    @Override
    public int getInventorySize(ItemStack stack) {
        return 9;
    }

    @Override
    public boolean canStore(ItemStack stack) {
        return stack.isEdible();
    }

    @Override
    public boolean canPickupItems() {
        return false;
    }

    private int getFoodSlot(ItemStack stack, @Nullable LivingEntity entity) {
        int foodLevel = entity instanceof Player ? ((Player) entity).getFoodData().getFoodLevel() : 0;
        int neededNutrition = 20 - foodLevel;
        int currentBestNutrition = 0;
        int slot = -1;

        IItemHandler inventory = getInventory(stack);
        for (int i = 0; i < inventory.getSlots(); ++i) {
            ItemStack food = inventory.getStackInSlot(i);
            FoodProperties foodProperties = food.getFoodProperties(entity);

            if (foodProperties != null) {
                return i;

                // FIXME: select best food (but entity is often null...)
                /*int nutrition = foodProperties.getNutrition();
                boolean isBetter = false;
                if (slot < 0) {
                    isBetter = true;
                } else if (currentBestNutrition < neededNutrition && nutrition > currentBestNutrition) {
                    isBetter = true;
                }

                if (isBetter) {
                    slot = i;
                    currentBestNutrition = nutrition;
                }*/
            }
        }

        return slot;
    }

    @Override
    public @Nullable FoodProperties getFoodProperties(ItemStack stack, @Nullable LivingEntity entity) {
        IItemHandler inventory = getInventory(stack);
        int slot = getFoodSlot(stack, entity);
        if (slot < 0) {
            return super.getFoodProperties(stack, entity);
        }

        ItemStack food = inventory.getStackInSlot(slot);
        if (!food.isEmpty() && food.isEdible()) {
            return food.getFoodProperties(entity);
        }
        return super.getFoodProperties(stack, entity);
    }

    @Override
    public boolean isEdible() {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        FoodProperties foodProperties = getFoodProperties(stack, player);

        if (player.isCrouching() || foodProperties == null) {
            // Open inventory menu
            return super.use(level, player, hand);
        }

        // Eat contained food
        if (player.canEat(foodProperties.canAlwaysEat())) {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(stack);
        } else {
            return InteractionResultHolder.fail(stack);
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack stackFinished = super.finishUsingItem(stack, level, entity);
        stackFinished.setCount(1);
        if (entity instanceof Player && !((Player) entity).getAbilities().instabuild) {
            int foodSlot = getFoodSlot(stack, entity);
            IItemHandler inventory = getInventory(stack);
            inventory.getStackInSlot(foodSlot).shrink(1);
            saveInventory(stack, inventory, (Player) entity);
        }
        return stackFinished;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        if (getFoodSlot(stack, null) > -1) {
            return UseAnim.EAT;
        }
        return super.getUseAnimation(stack);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        FoodProperties foodProperties = getFoodProperties(stack, null);
        if (foodProperties != null) {
            return foodProperties.isFastFood() ? 16 : 32;
        }
        return 0;
    }
}
