package net.silentchaos512.loginar.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.silentchaos512.loginar.util.TextUtil;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class UpgradeItem extends Item {
    private final boolean unimplemented;

    public UpgradeItem(boolean unimplemented, Properties properties) {
        super(properties);
        this.unimplemented = unimplemented;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flags) {
        if (unimplemented) {
            tooltip.add(TextUtil.misc("not_implemented").withStyle(ChatFormatting.DARK_RED, ChatFormatting.ITALIC));
        }
    }
}
