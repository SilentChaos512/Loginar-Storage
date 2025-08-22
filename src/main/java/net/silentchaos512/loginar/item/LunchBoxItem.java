package net.silentchaos512.loginar.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.ComponentItemHandler;
import net.neoforged.neoforge.items.IItemHandler;
import net.silentchaos512.loginar.item.container.ContainerItem;
import net.silentchaos512.loginar.item.container.ContainerItemMenu;
import net.silentchaos512.loginar.setup.LsDataComponents;
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
        return stack.get(DataComponents.FOOD) != null;
    }

    @Override
    public boolean canPickupItems() {
        return false;
    }

    private void setFoodSlot(ItemStack stack, IItemHandler inventory, int slot) {
        stack.set(LsDataComponents.USE_SLOT, slot);

        if (slot < 0 || slot >= inventory.getSlots()) {
            stack.remove(DataComponents.FOOD);
            stack.remove(DataComponents.CONSUMABLE);
            return;
        }

        ItemStack food = inventory.getStackInSlot(slot);
        stack.set(DataComponents.FOOD, food.get(DataComponents.FOOD));
        stack.set(DataComponents.CONSUMABLE, food.get(DataComponents.CONSUMABLE));
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity entity, @Nullable EquipmentSlot slot) {
        if (!(slot == EquipmentSlot.MAINHAND || slot == EquipmentSlot.OFFHAND) || !(entity instanceof Player player)) {
            // Only tick when held by a player
            return;
        }

        selectBestFoodForPlayer(stack, player);
        if (player.tickCount % 20 == 0) {
            displaySelectedFood(stack, player);
        }
    }

    private void displaySelectedFood(ItemStack stack, Player player) {
        int foodSlot = stack.getOrDefault(LsDataComponents.USE_SLOT, -1);
        if (foodSlot >= 0 && foodSlot < getInventorySize(stack)) {
            ItemStack food = getInventory(stack).getStackInSlot(foodSlot);
            player.displayClientMessage(Component.translatable("item.loginar.lunch_box.next_food", food.getDisplayName().getString()), true);
        }
    }

    private void selectBestFoodForPlayer(ItemStack stack, Player player) {
        int foodLevel = player.getFoodData().getFoodLevel();
        int neededNutrition = 20 - foodLevel;
        int currentBestNutritionDifference = Integer.MAX_VALUE;
        int currentFoodSlot = stack.getOrDefault(LsDataComponents.USE_SLOT, 0);
        int bestFoodSlot = -1;

        IItemHandler inventory = getInventory(stack);
        for (int i = 0; i < inventory.getSlots(); ++i) {
            ItemStack food = inventory.getStackInSlot(i);
            FoodProperties foodProperties = food.get(DataComponents.FOOD);

            if (foodProperties != null) {
                int nutrition = foodProperties.nutrition();
                int nutritionDifference = Math.abs(nutrition - neededNutrition);
                boolean isBetter = bestFoodSlot < 0 || nutritionDifference < currentBestNutritionDifference;

                if (isBetter) {
                    bestFoodSlot = i;
                    currentBestNutritionDifference = nutritionDifference;
                }
            }
        }

        if (currentFoodSlot != bestFoodSlot) {
            setFoodSlot(stack, inventory, bestFoodSlot);
        }
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        FoodProperties foodProperties = stack.get(DataComponents.FOOD);

        if (player.isCrouching() || foodProperties == null) {
            // Open inventory menu
            return super.use(level, player, hand);
        }

        // Eat contained food
        if (player.canEat(foodProperties.canAlwaysEat())) {
            player.startUsingItem(hand);
            return InteractionResult.CONSUME;
        } else {
            return InteractionResult.FAIL;
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack stackFinished = super.finishUsingItem(stack, level, entity);
        stackFinished.setCount(1);
        if (entity instanceof Player player && !player.getAbilities().instabuild) {
            int foodSlot = stack.getOrDefault(LsDataComponents.USE_SLOT, -1);
            ComponentItemHandler inventory = getInventory(stack);
            if (foodSlot >= 0 && foodSlot < inventory.getSlots()) {
                inventory.getStackInSlot(foodSlot).shrink(1);
                ItemStack foodStack = inventory.getStackInSlot(foodSlot);
                foodStack.shrink(1);
                inventory.setStackInSlot(foodSlot, foodStack);
                selectBestFoodForPlayer(stack, player);
            }
        }
        return stackFinished;
    }
}
