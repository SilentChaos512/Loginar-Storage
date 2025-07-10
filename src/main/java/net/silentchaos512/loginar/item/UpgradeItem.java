package net.silentchaos512.loginar.item;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.silentchaos512.lib.util.NameUtils;
import net.silentchaos512.loginar.util.TextUtil;

import java.util.function.Consumer;

public class UpgradeItem extends Item {
    public UpgradeItem(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        ResourceLocation name = NameUtils.fromItem(this);
        tooltipAdder.accept(TextUtil.translate("item", name.getPath() + ".desc"));
    }
}
