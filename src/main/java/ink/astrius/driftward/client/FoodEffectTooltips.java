package ink.astrius.driftward.client;

import com.mojang.datafixers.util.Either;
import de.dafuqs.spectrum.helpers.TooltipHelper;
import de.dafuqs.spectrum.registries.SpectrumItemTags;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.ArrayList;

public class FoodEffectTooltips {
    public static void registerClient() {
        NeoForge.EVENT_BUS.addListener(FoodEffectTooltips::onCollectTooltips);
    }

    public static void onCollectTooltips(ItemTooltipEvent event) {
        // Essentially copied from `SpectrumClientEventListeners.onDrawTooltips`, because it's
        // non-trivial to call directly and requires ugly type conversions. This moves food info to
        // between the item name and the ID and other metadata.
        var stack = event.getItemStack();
        if (stack.has(DataComponents.FOOD)) {
            var tooltip = event.getToolTip();
            var lines = new ArrayList<Either<FormattedText, TooltipComponent>>();
            TooltipHelper.addFoodComponentEffectTooltip(Minecraft.getInstance(), stack, lines, event.getContext().tickRate());
            for (var line : lines) {
                tooltip.add((Component) line.left().get());
            }
        }
        if (stack.is(SpectrumItemTags.COMING_SOON_TOOLTIP)) {
            event.getToolTip().add(Component.translatable("spectrum.tooltip.coming_soon").withStyle(ChatFormatting.RED));
        }
    }
}
