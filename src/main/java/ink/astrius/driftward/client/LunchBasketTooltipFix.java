package ink.astrius.driftward.client;

import net.mehvahdjukaar.supplementaries.reg.ModRegistry;
import net.neoforged.neoforge.common.NeoForge;
import squeek.appleskin.api.event.TooltipOverlayEvent;

public class LunchBasketTooltipFix {
    public static void registerClient() {
        NeoForge.EVENT_BUS.addListener(LunchBasketTooltipFix::onPreTooltipEvent);
    }

    public static void onPreTooltipEvent(TooltipOverlayEvent.Pre event) {
        if (event.itemStack.getItem().equals(ModRegistry.LUNCH_BASKET_ITEM.get())) {
            event.setCanceled(true);
        }
    }
}
