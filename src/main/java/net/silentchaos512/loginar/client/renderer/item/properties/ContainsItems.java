package net.silentchaos512.loginar.client.renderer.item.properties;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.silentchaos512.loginar.item.container.ContainerItem;
import org.jetbrains.annotations.Nullable;

public record ContainsItems() implements ConditionalItemModelProperty {
    public static final ContainsItems INSTANCE = new ContainsItems();
    public static final MapCodec<ContainsItems> CODEC = MapCodec.unit(INSTANCE);

    @Override
    public boolean get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed, ItemDisplayContext displayContext) {
        if (stack.getItem() instanceof ContainerItem) {
            return ContainerItem.containsAnyItems(stack);
        }
        return false;
    }

    @Override
    public MapCodec<? extends ConditionalItemModelProperty> type() {
        return CODEC;
    }
}
