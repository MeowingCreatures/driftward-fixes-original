package ink.astrius.driftward.mixin.client.tooltips;

import de.dafuqs.spectrum.registries.client.SpectrumClientEventListeners;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SpectrumClientEventListeners.class)
public class DisableTooltipInjectionMixin {
    @Inject(method = "onDrawTooltips", at = @At("HEAD"), cancellable = true)
    private static void disableTooltipInjection(RenderTooltipEvent.GatherComponents event, CallbackInfo cir) {
        cir.cancel();
    }
}
