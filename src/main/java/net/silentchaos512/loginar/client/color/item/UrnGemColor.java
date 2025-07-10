package net.silentchaos512.loginar.client.color.item;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.silentchaos512.lib.util.Color;
import net.silentchaos512.loginar.setup.LsDataComponents;
import org.jetbrains.annotations.Nullable;

public record UrnGemColor(Color defaultColor) implements ItemTintSource {
    public static final MapCodec<UrnGemColor> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Color.CODEC.fieldOf("default").forGetter(UrnGemColor::defaultColor)
            ).apply(instance, UrnGemColor::new)
    );

    @Override
    public int calculate(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity) {
        return stack.getOrDefault(LsDataComponents.URN_GEM_COLOR, this.defaultColor).getColor();
    }

    @Override
    public MapCodec<? extends ItemTintSource> type() {
        return CODEC;
    }
}
