package ink.astrius.driftward.mixin.potions;

import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.platform.neoforge.EmiAgnosNeoForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EmiAgnosNeoForge.class)
public class EmiRemovePotions {
    @Inject(method = "addBrewingRecipesAgnos", at = @At("HEAD"), cancellable = true)
    public void skipRegisteringPotions(EmiRegistry registry, CallbackInfo ci) {
        ci.cancel();
    }
}
