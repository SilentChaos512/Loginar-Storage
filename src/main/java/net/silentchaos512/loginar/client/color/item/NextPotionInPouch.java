package net.silentchaos512.loginar.client.color.item;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.silentchaos512.loginar.item.PotionPouchItem;
import org.jetbrains.annotations.Nullable;

public record NextPotionInPouch(int defaultColor) implements ItemTintSource {
    public static final MapCodec<NextPotionInPouch> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    ExtraCodecs.RGB_COLOR_CODEC.fieldOf("default").forGetter(NextPotionInPouch::defaultColor)
            ).apply(instance, NextPotionInPouch::new)
    );

    @Override
    public int calculate(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity) {
        if (stack.getItem() instanceof PotionPouchItem potionPouchItem) {
            ItemStack potionStack = potionPouchItem.getNextPotion(stack);
            return potionStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).getColor();
        }
        return -1;
    }

    @Override
    public MapCodec<? extends ItemTintSource> type() {
        return null;
    }
}
