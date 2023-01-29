package net.silentchaos512.loginar.item;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.silentchaos512.lib.util.NameUtils;
import net.silentchaos512.loginar.util.TextUtil;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class UpgradeItem extends Item {
    public UpgradeItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flags) {
        ResourceLocation name = NameUtils.fromItem(this);
        tooltip.add(TextUtil.translate("item", name.getPath() + ".desc"));
    }
}
